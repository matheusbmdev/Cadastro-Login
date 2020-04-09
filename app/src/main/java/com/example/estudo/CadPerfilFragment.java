package com.example.estudo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.estudo.Modelo.Pessoa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

import okhttp3.internal.Util;

public class CadPerfilFragment extends Fragment {

    private Button btnCadPerfil;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    public EditText nome;
    public String newid;
    public String email;
    public String nome_perfil;
   // private Pessoa pessoa;
    public  TextView name;
    public EditText newnome;
    public TextView teste;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad_perfil, container, false);

        btnCadPerfil = view.findViewById(R.id.btnCadPerfil);
       // name = view.findViewById(R.id.nome_cad_perfil);
        newnome = view.findViewById(R.id.nome_cad_perfil);
        //teste = view.findViewById(R.id.teste);

        //atualizaperfil();
        Pessoa pessoa = new Pessoa();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref =database.getReference("Perfil");
        ///////////////////////////////////////////////////////////////////////
        databaseReference = database.getReference();

        //////////////////////////////////////////////////////////////////
        user = Conexao.getFirebaseUser();
        email = user.getEmail();
        myref.orderByChild("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    if(p.getEmail().equals(email)){
                        newnome.setText(p.getNome());
                        newid = p.getId();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });





        btnCadPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref =database.getReference("Perfil");
                myref.orderByChild("email").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                            Pessoa p = objSnapshot.getValue(Pessoa.class);
                            if(p.getEmail().equals(email)){
                               // newnome.setText(p.getNome());
                                newid = p.getId();
                                Pessoa pessoa = new Pessoa();
                                pessoa.setId(newid);
                                pessoa.setNome(newnome.getText().toString().trim());
                                pessoa.setEmail(email);
                                databaseReference.child("Perfil").child(newid).setValue(pessoa);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

 /*
    btnCadPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Pessoa p = new Pessoa(nome.getText().toString().trim(), sobrenome.getText().toString().trim(), email);
                FirebaseUser usu =FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest.Builder profileUpdate = new UserProfileChangeRequest.Builder()
                        .setDisplayName(nome.getText().toString().trim());

                assert usu != null;
                usu.updateProfile(profileUpdate.build());

                Pessoa p = new Pessoa();
                p.setNome(nome.getText().toString().trim());
                p.setEmail(email);
               // p.setSobrenome(sobrenome.getText().toString().trim());

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref =database.getReference("Perfil");
                myref.push().setValue(p);



              //  databaseReference.child("Perfil/").child(String.valueOf(new Date().getTime())).setValue(p);





                Toast.makeText(getContext(), "Atualizado com sucesso!",
                        Toast.LENGTH_SHORT).show();
                nome.setText("");
                sobrenome.setText("");
            }
        });

*/

        return view;
    }

    private void atualizaperfil() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref =database.getReference("Perfil");
        user = Conexao.getFirebaseUser();
        email = user.getEmail();
        myref.orderByChild("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    if(p.getEmail().equals(email)){
                        newnome.setText(p.getNome());
                        nome_perfil = p.getNome();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }

}
