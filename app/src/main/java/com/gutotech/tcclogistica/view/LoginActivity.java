package com.gutotech.tcclogistica.view;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.view.adm.AdmMainActivity;
import com.gutotech.tcclogistica.view.motorista.MotoristaMainActivity;
import com.gutotech.tcclogistica.view.roteirista.RoteiristaMainActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText userEditText, passwordEditText;
    private Dialog processingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = findViewById(R.id.userEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        processingDialog = new Dialog(this);
        processingDialog.setContentView(R.layout.dialog_carregando);
        processingDialog.setCancelable(false);
        processingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button entrarButton = findViewById(R.id.loginButton);
        entrarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processingDialog.show();

                String user = userEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidField(user, password)) {
                    switch (getTipoFuncionario(user)) {
                        case 1:
                            startActivity(new Intent(LoginActivity.this, AdmMainActivity.class));
                            break;
                        case 2:
                            startActivity(new Intent(LoginActivity.this, RoteiristaMainActivity.class));
                            break;
                        case 3:
                            startActivity(new Intent(LoginActivity.this, MotoristaMainActivity.class));
                            break;
                    }

                    finish();
                }

                processingDialog.dismiss();
            }
        });
    }

    private boolean isValidField(String user, String password) {
        boolean valid = true;

        if (user.isEmpty()) {
            userEditText.setError("É necessário um usuário válido.");
            valid = false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Senha requerida.");
            valid = false;
        }

        return valid;
    }

    private int getTipoFuncionario(String user) {
        return Integer.parseInt(user.split("-")[1]);
    }
}
