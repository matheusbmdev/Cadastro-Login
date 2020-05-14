package com.example.estudo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.estudo.Modelo.Pessoa;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavHeaderActivity extends MainActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String email;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth =FirebaseAuth.getInstance();
        user = Conexao.getFirebaseUser();
        FirebaseUser currentUser = auth.getCurrentUser();

        setContentView(R.layout.activity_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        View headerVier =navigationView.getHeaderView(0);
        TextView info_email =  headerVier.findViewById(R.id.naheader_email);
        assert currentUser != null;
        email = currentUser.getEmail();
        info_email.setText(currentUser.getEmail());

        ////////////////////////////////////////////////////////////////////////
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref =database.getReference("Perfil");
        myref.orderByChild("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                    Pessoa p = objSnapshot.getValue(Pessoa.class);
                    if(p.getEmail().equals(email)){
                        if(p.isAdm() != true){
                            hideItem();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        ///////////////////////////////////////////////////////////////////////
        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
            navigationView.setCheckedItem(R.id.Menu_Perfil);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.Menu_Ler:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LerFragment()).commit();
                break;
            case R.id.Menu_Cadastrar:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CadFragment()).commit();
                break;
            case R.id.Menu_Perfil:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PerfilFragment()).commit();
                break;
            case R.id.Menu_Pessoas:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PessoasOnlineFragment()).commit();
                break;
            case R.id.menu_Sair:
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
                Conexao.logOut();
                finish();

                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private void hideItem() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        Menu nav_Menu = navigationView.getMenu();
        nav_Menu.findItem(R.id.Menu_Pessoas).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

}


