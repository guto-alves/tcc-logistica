package com.gutotech.tcclogistica.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;

import es.dmoral.toasty.Toasty;

public class SplashActivity extends AppCompatActivity {
    private ProcessingDialog processingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        processingDialog = new ProcessingDialog(this);
        processingDialog.show();

        checkForMaintenace();
    }

    private void checkForMaintenace() {
        DatabaseReference maintenanceReference = ConfigFirebase.getDatabase().child("manutencao");

        maintenanceReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean maintenance = (boolean) dataSnapshot.getValue();

                processingDialog.dismiss();

                if (maintenance) {
                    Toasty.warning(getApplicationContext(), getResources().getString(R.string.estamos_em_manutencao), Toasty.LENGTH_LONG, true).show();
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
