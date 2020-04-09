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

import com.example.estudo.Modelo.Pessoa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PerfilFragment extends Fragment {


    private TextView info;
    private Button btnatt;
    public  String email;
    private Pessoa pessoa;
    public TextView name;


    private FirebaseAuth auth;
    private FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        user = Conexao.getFirebaseUser();
        info = view.findViewById(R.id.emailProfile);
        email = user.getEmail();
        info.setText("E-mail : "+email);
        name = view.findViewById(R.id.nomeProfile);

        btnatt = view.findViewById(R.id.att_perfil);






        btnatt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = new CadPerfilFragment();
                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });

        return view;



    }

    @Override
    public void onResume() {
        super.onResume();

        atualizaperfil();
    }

    private void atualizaperfil() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref =database.getReference("Perfil");
        myref.orderByChild("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    if(p.getEmail().equals(email)){
                        name.setText("Nome : " + p.getNome());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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
