package com.gutotech.tcclogistica.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.config.ConfigFirebase;

import es.dmoral.toasty.Toasty;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        isManutencao();
    }

    private void isManutencao() {
        DatabaseReference manutencaoReference = ConfigFirebase.getDatabase().child("manutencao");

        manutencaoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean manutencao = (boolean) dataSnapshot.getValue();

                if (manutencao) {
                    Toasty.warning(getApplicationContext(), "Estamos em manutençao!", Toasty.LENGTH_SHORT, true).show();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
