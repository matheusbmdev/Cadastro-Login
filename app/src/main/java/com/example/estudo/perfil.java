package com.example.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class perfil extends MainActivity{

    private Button btnout;
    private TextView info;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telaprincipal);

        inicializar();

    }

    private void onClick() {
        btnout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(perfil.this, MainActivity.class);
                startActivity(i);
                Conexao.logOut();
                finish();
            }
        });
    }

    private void inicializar() {

        info = (TextView) findViewById(R.id.nomeProfile);
    }

    @Override
    protected void onStart() {
        super.onStart();

        auth = Conexao.getFirebaseAuth();
        user = Conexao.getFirebaseUser();

        verificaUser();
    }

    private void verificaUser() {
        if(user == null){
            finish();
        }else{
            info.setText("E-mail : "+user.getEmail());
        }
    }
}
