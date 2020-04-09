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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CadFragment extends Fragment {

    private Button btnenviarComentario;
    public EditText com;
    private FirebaseAuth auth;
    private FirebaseUser user;
    public String coment;
    private DatabaseReference databaseReference;
    public String email;
    public String uid;
    ////////////////////////////////////////
    public String hr;
    public SimpleDateFormat simpleDateFormat;
    public Calendar calendar;
    public String nome;
    public String id;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cad, container, false);
        auth = FirebaseAuth.getInstance();
        user = Conexao.getFirebaseUser();
        btnenviarComentario = view.findViewById(R.id.frag_cad_button);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        DatabaseReference myref =database.getReference("Perfil");

        //testando coisa nova

        com = view.findViewById(R.id.frag_cad_editText);
        email = user.getEmail();
        coment = com.getText().toString().trim();

        //hora certa
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        hr = simpleDateFormat.format(calendar.getTime());





        databaseReference = FirebaseDatabase.getInstance().getReference();
        btnenviarComentario.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
            // final EnviarMensagem enviarMensagem = new EnviarMensagem(email , com.getText().toString().trim(), hr, nome, id);
             uid = UUID.randomUUID().toString();

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myref =database.getReference("Perfil");
                myref.orderByChild("email").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                            Pessoa p = objSnapshot.getValue(Pessoa.class);
                            if(p.getEmail().equals(email)){
                               EnviarMensagem enviarMensagem = new EnviarMensagem(email, com.getText().toString().trim(),hr, p.getNome(), p.getId());
                                databaseReference.child("Chat").child(String.valueOf(new Date().getTime())).setValue(enviarMensagem);
                                Toast.makeText(getContext(), "Mensagem enviada com sucesso!",
                                        Toast.LENGTH_SHORT).show();
                                com.setText("");
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });


           //  databaseReference.child("Chat").child(String.valueOf(new Date().getTime())).setValue(enviarMensagem);
                //DatabaseReference myRef = database.getReference("Chat/");
                 //myRef.push().setValue(enviarMensagem);








        return view;

    }

    private void cadFB() {


    }
}
