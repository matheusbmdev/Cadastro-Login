package com.example.estudo;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.estudo.Modelo.EnviarMensagem;
import com.example.estudo.Modelo.Pessoa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CadPerfilFragment extends Fragment {

    private Button btnCadPerfil;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    public EditText nome;
    public EditText sobrenome;
    public String email;
    public String nome_perfil, sobrenome_perfil;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_perfil, container, false);
        auth = FirebaseAuth.getInstance();
        user = Conexao.getFirebaseUser();
        btnCadPerfil = view.findViewById(R.id.btnCadPerfil);

        nome = view.findViewById(R.id.nome_cad_perfil);
        sobrenome = view.findViewById(R.id.sobrenome_cad_perfil);
        email = user.getEmail();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        btnCadPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Pessoa p = new Pessoa(nome.getText().toString().trim(), sobrenome.getText().toString().trim(), email);

                databaseReference.child("Perfil/").child(String.valueOf(new Date().getTime())).setValue(p);

                Toast.makeText(getContext(), "Atualizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                nome.setText("");
                sobrenome.setText("");
            }
        });



        return view;
    }
}
