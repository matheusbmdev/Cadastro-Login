package com.example.estudo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilFragment extends Fragment {


    private TextView info;
    private Button btnatt;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        user = Conexao.getFirebaseUser();
        info = view.findViewById(R.id.nomeProfile);
        info.setText("E-mail : "+user.getEmail());

        btnatt = view.findViewById(R.id.att_perfil);

        btnatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Instancia o fragmentManager que é o responsável pela troca de Fragment
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//Instancia o fragment que você vai colocar na tela
                Fragment fragment = new CadPerfilFragment();
//Faz a transação, substituindo no frame da sua MainActivity que contem os fragmentos, o antigo pelo novo fragmento que você instanciou.
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        return view;



    }




    private void inicializar(View view) {

    }


    private void verificaUser() {
        if(user == null){
            alert("E-mail ou senha incorretos!");
        }else{
            info.setText("E-mail : "+user.getEmail());
        }
    }

    private void alert(String str) {
        Toast teste = Toast.makeText(getContext(), str, Toast.LENGTH_LONG);
        teste.show();
    }
}
