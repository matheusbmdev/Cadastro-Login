package com.example.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class cadastro extends MainActivity {

    private EditText cadSenha;
    private EditText cadEmail;
    private Button btnCadastro;
    private Button btnVoltar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telacadastro);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = cadEmail.getText().toString().trim();
                String senha = cadSenha.getText().toString().trim();

                criarUser(email, senha);
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(cadastro.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void inicializaComponentes() {
        cadSenha = (EditText) findViewById(R.id.cadSenha);
        cadEmail = (EditText) findViewById(R.id.cadEmail);
        btnCadastro = (Button) findViewById(R.id.btncadCadastro);
        btnVoltar = (Button) findViewById(R.id.btncadVoltar);
    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    alert("Cadastrado com sucesso!");
                    Intent i = new Intent(cadastro.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    alert("ERRO!");
                }
            }
        });

    }

    private void alert(String msg) {
        Toast.makeText(cadastro.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}
