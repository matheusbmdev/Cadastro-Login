package com.example.cadastro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Cadastro extends AppCompatActivity {
    private EditText logUsuario;
    private EditText logEmail;
    private EditText logSenha;
    private Button btnCadastro;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logUsuario = (EditText) findViewById(R.id.logUsuario);
        logEmail = (EditText) findViewById(R.id.logEmail);
        logSenha = (EditText) findViewById(R.id.logSenha);
        btnCadastro = (Button) findViewById(R.id.btnCadastro);


        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usuario = logUsuario.getText().toString();
                String email = logEmail.getText().toString();
                String senha = logSenha.getText().toString();

                if(!usuario.equals("") || !email.equals("") || !senha.equals("")){
                    if(usuario.equals("admin") && senha.equals("123")){
                        Toast.makeText( Cadastro.this, "OK", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText( Cadastro.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
