package com.gutotech.tcclogistica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gutotech.tcclogistica.adm.AdmMainActivity;

public class LoginActivity extends AppCompatActivity {
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_carregando);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button entrarButton = findViewById(R.id.entrarButton);
        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                dialog.dismiss();

                startActivity(new Intent(LoginActivity.this, AdmMainActivity.class));
                finish();
            }
        });
    }
}
