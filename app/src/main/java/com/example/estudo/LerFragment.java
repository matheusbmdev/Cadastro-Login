package com.example.estudo;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.R.layout.simple_list_item_1;

public class LerFragment extends Fragment {

    private EditText mensagem;
    public ListView listView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public List<EnviarMensagem> listMensagens = new ArrayList<EnviarMensagem>();
    public ArrayAdapter<EnviarMensagem> adapter;


    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ler, container, false);
        Query query = null;
        inicializaComponentes(view);

      inicializaFirebase();
        eventoDatabase();

     /*     listMensagens.clear();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    EnviarMensagem em = objSnapshot.getValue(EnviarMensagem.class);
                    listMensagens.add(em);
                }

                adapter = new ArrayAdapter<EnviarMensagem>(getActivity(), android.R.layout.simple_list_item_1, listMensagens);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/





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
                adapter = new ArrayAdapter<EnviarMensagem>(getActivity(),android.R.layout.simple_list_item_1, listMensagens);
                listView.setAdapter(adapter);
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

    }

    private void inicializaComponentes(View view) {
        listView = (ListView) view.findViewById(R.id.list_Ler);
    }


}

