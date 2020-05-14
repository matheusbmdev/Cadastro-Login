package com.example.estudo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.estudo.Modelo.EnviarMensagem;
import com.example.estudo.Modelo.Pessoa;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PessoasOnlineFragment extends Fragment {


    public ListView listView;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public List<Pessoa> listPessoas = new ArrayList<Pessoa>();
    public ArrayAdapter<Pessoa> adapter;


    @Nullable
    @Override


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pessoasonline, container, false);
        Query query = null;
        inicializaComponentes(view);

        inicializaFirebase();
        eventoDatabase();


        return view;
    }

    private void eventoDatabase() {
        databaseReference.child("Perfil").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPessoas.clear();
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    listPessoas.add(p);
                }
                adapter = new ArrayAdapter<Pessoa>(getActivity(),android.R.layout.simple_list_item_1, listPessoas);
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

