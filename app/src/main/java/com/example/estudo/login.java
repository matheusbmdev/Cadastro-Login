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

public class login extends MainActivity {
    private EditText editSenha;
    private TextView resetSenha;
    private EditText editUser;
    private Button btnVoltar;
    private Button btnEntrar;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telalogin);
        inicializaComponentes();
        eventoClicks();
    }

    private void eventoClicks() {
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(login.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editUser.getText().toString().trim();
                String senha = editSenha.getText().toString().trim();
                Login(email, senha);
            }
        });


    }

    private void Login(String email, String senha) {
        auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                 if(task.isSuccessful()){
                     Intent i = new Intent(login.this, NavHeaderActivity.class);
                     startActivity(i);
                 }else{
                     alert("E-mail ou senha incorretos!");
                 }
            }
        });
    }

    private void alert(String str) {
        Toast.makeText(login.this, str, Toast.LENGTH_SHORT).show();
    }

    private void inicializaComponentes() {
        editSenha = (EditText) findViewById(R.id.logSenha);
        resetSenha = (TextView) findViewById(R.id.logSenha);
        editUser = (EditText) findViewById(R.id.logUsuario);
        btnEntrar = (Button) findViewById(R.id.btnlogEntrar);
        btnVoltar = (Button) findViewById(R.id.btnlogVoltar);
    }


    @Override
    protected void onStart() {
        super.onStart();
        auth = Conexao.getFirebaseAuth();
    }
}