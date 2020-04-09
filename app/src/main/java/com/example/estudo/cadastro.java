package com.example.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.estudo.Modelo.Pessoa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class cadastro extends MainActivity {

    private EditText cadSenha;
    private EditText cadEmail;
    private EditText nome;
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
        nome = findViewById(R.id.cadNome);
        btnCadastro = (Button) findViewById(R.id.btncadCadastro);
        btnVoltar = (Button) findViewById(R.id.btncadVoltar);

    }

    private void criarUser(String email, String senha) {
        auth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser usu = FirebaseAuth.getInstance().getCurrentUser();
                    UserProfileChangeRequest.Builder profileUpdate = new UserProfileChangeRequest.Builder()
                            .setDisplayName(nome.getText().toString().trim());

                    assert usu != null;
                    usu.updateProfile(profileUpdate.build());

                    Pessoa p = new Pessoa();
                    p.setNome(nome.getText().toString().trim());
                    p.setEmail(cadEmail.getText().toString().trim());
                    p.setId(UUID.randomUUID().toString());


                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference myref =database.getReference("Perfil");
                    myref.child(p.getId()).setValue(p);


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
