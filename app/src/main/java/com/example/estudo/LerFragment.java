package com.example.estudo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.estudo.Modelo.EnviarMensagem;
import com.example.estudo.Modelo.Pessoa;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

import java.util.EventListener;
import java.util.List;
import java.util.Objects;

import static android.R.layout.simple_list_item_1;

public class LerFragment extends Fragment {

    public EditText mensagem;
    public ListView listView;
    public Button btn;
    public Button btnexcluir;
    public String email;
    public String emailuser;
    private int aux = 0;
    FirebaseUser user;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public List<EnviarMensagem> listMensagens = new ArrayList<EnviarMensagem>();
    public ArrayAdapter<EnviarMensagem> adapter;
    EnviarMensagem msg;


    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ler, container, false);
        Query query = null;
        inicializaComponentes(view);

        inicializaFirebase();
        eventoDatabase();

        btn.setVisibility(view.INVISIBLE);
        btnexcluir.setVisibility(view.INVISIBLE);



        return view;
    }

    private void eventoDatabase() {
        databaseReference.child("Chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMensagens.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    EnviarMensagem em = objSnapshot.getValue(EnviarMensagem.class);
                    listMensagens.add(em);
                }
                if(getActivity() != null){
                    adapter = new ArrayAdapter<EnviarMensagem>((getActivity()),android.R.layout.simple_list_item_1, listMensagens);
                    listView.setAdapter(adapter);
                }


                //pegando a parte que o usuario clica
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        msg = (EnviarMensagem)parent.getItemAtPosition(position);
                        mensagem.setText( msg.getMensagem());
                        email = msg.getEmail();
                        emailuser = user.getEmail();
                        databaseReference.child("Perfil").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                                    if(p.getEmail().equals(emailuser)){
                                        if(p.isAdm() == true) aux = 1;
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        if(aux == 1){
                            if(emailuser.equals(email)) btn.setVisibility(view.VISIBLE);
                            else btn.setVisibility(view.INVISIBLE);
                            btnexcluir.setVisibility(view.VISIBLE);
                        }else if(email.equals(emailuser)){

                                btn.setVisibility(view.VISIBLE);
                                btnexcluir.setVisibility(view.VISIBLE);

                        }else{
                            btn.setVisibility(view.INVISIBLE);
                            btnexcluir.setVisibility(view.INVISIBLE);
                        }
                    }
                });

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EnviarMensagem reenviar = new EnviarMensagem();
                        reenviar.setCad_id(msg.getCad_id());
                        reenviar.setCad_id_msg(msg.getCad_id_msg());
                        reenviar.setCad_nome(msg.getCad_nome());
                        reenviar.setEmail(msg.getEmail());
                        reenviar.setHora_Formatada(msg.getHora_Formatada());
                        reenviar.setMensagem(mensagem.getText().toString().trim());
                        databaseReference.child("Chat").child(msg.getCad_id_msg()).setValue(reenviar);
                        mensagem.setText("");

                    }
                });
                btnexcluir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EnviarMensagem apagar = new EnviarMensagem();
                        apagar.setCad_id_msg(msg.getCad_id_msg());
                        databaseReference.child("Chat").child(msg.getCad_id_msg()).removeValue();
                        mensagem.setText("");

                    }
                });


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    private void inicializaFirebase() {
        FirebaseApp.initializeApp(getActivity());
        firebaseDatabase = FirebaseDatabase.getInstance();
        if(firebaseDatabase == null) firebaseDatabase.setPersistenceEnabled(true);
        databaseReference = firebaseDatabase.getReference();
        DatabaseReference perfilref = firebaseDatabase.getReference("Perfil");
        user = Conexao.getFirebaseUser();

    }

    private void inicializaComponentes(View view) {
        listView = (ListView) view.findViewById(R.id.list_Ler);
        mensagem = (EditText) view.findViewById(R.id.frag_ler_editText);
        btn = (Button) view.findViewById(R.id.frag_ler_button);
        btnexcluir = (Button) view.findViewById(R.id.frag_excluir_button);
    }


}

