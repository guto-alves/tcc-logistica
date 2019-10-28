package com.gutotech.tcclogistica.view.roteirista;

import android.content.Intent;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.config.Storage;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.PerfilFragment;
import com.gutotech.tcclogistica.view.LoginActivity;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class RoteiristaMainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView profileImageView;

    private DatabaseReference funcionarioReference;
    private ValueEventListener funcionarioListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roteirista_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);

        TextView userTextView = headerView.findViewById(R.id.userTextView);
        userTextView.setText(FuncionarioOn.funcionario.getNome().toUpperCase().split(" ")[0]);

        profileImageView = headerView.findViewById(R.id.profileImageView);

        atualizarFoto();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_notas, R.id.nav_entregas, R.id.nav_coletas, R.id.nav_meus_dados, R.id.nav_suporte)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exit) {
            startActivity(new Intent(RoteiristaMainActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void getFuncionario() {
        funcionarioReference = ConfigFirebase.getDatabase()
                .child("funcionario")
                .child(FuncionarioOn.funcionario.getLogin().getUser());

        funcionarioListener = funcionarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FuncionarioOn.funcionario = dataSnapshot.getValue(Funcionario.class);
                atualizarFoto();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void atualizarFoto() {
        if (FuncionarioOn.funcionario.isProfileImage())
            Storage.downloadProfile(getApplicationContext(), profileImageView, FuncionarioOn.funcionario.getLogin().getUser());
        else
            profileImageView.setImageResource(R.drawable.perfil_sem_foto);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getFuncionario();
    }

    @Override
    protected void onStop() {
        super.onStop();

        funcionarioReference.removeEventListener(funcionarioListener);
        FuncionarioOn.funcionario.deslogar();
    }
}
