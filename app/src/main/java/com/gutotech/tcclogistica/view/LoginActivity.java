package com.gutotech.tcclogistica.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adm.AdmMainActivity;
import com.gutotech.tcclogistica.view.motorista.MotoristaMainActivity;
import com.gutotech.tcclogistica.view.roteirista.RoteiristaMainActivity;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText userEditText, passwordEditText;

    private Dialog processingDialog;

    private DatabaseReference funcionarioReference;
    private ValueEventListener funcionarioListener;

    private List<Funcionario> funcionariosList = new ArrayList<>();

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

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(loginButtonListener);

        funcionarioReference = ConfigFirebase.getDatabase().child("funcionario");
    }

    private final View.OnClickListener loginButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final String user = userEditText.getText().toString().trim();
            final String password = passwordEditText.getText().toString();

            if (isValidField(user, password)) {
                if (signInWith(user, password)) {
                    FuncionarioOn.funcionario.setOnline(true);
                    FuncionarioOn.funcionario.setUltimoLogin(DateCustom.getDataEHora());
                    FuncionarioOn.funcionario.salvar();

                    switch (FuncionarioOn.funcionario.getCargo()) {
                        case Funcionario.ADM:
                            startActivity(new Intent(LoginActivity.this, AdmMainActivity.class));
                            break;
                        case Funcionario.ROTEIRISTA:
                            startActivity(new Intent(LoginActivity.this, RoteiristaMainActivity.class));
                            break;
                        case Funcionario.MOTORISTA:
                            startActivity(new Intent(LoginActivity.this, MotoristaMainActivity.class));
                            break;
                    }
                } else
                    Toasty.error(LoginActivity.this, "Usuário ou Senha inválidos", Toast.LENGTH_SHORT, true).show();
            }
        }
    };

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

    private boolean signInWith(String user, String password) {
        for (Funcionario funcionario : funcionariosList) {
            if (funcionario.getLogin().getUser().equals(user)) {
                if (funcionario.getLogin().getPassword().equals(password)) {
                    FuncionarioOn.funcionario = funcionario;
                    return true;
                }
            }
        }
        return false;
    }

    private void carregarFuncionarios() {
        processingDialog.show();

        funcionarioListener = funcionarioReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                funcionariosList.clear();

                for (DataSnapshot data : dataSnapshot.getChildren())
                    funcionariosList.add(data.getValue(Funcionario.class));

                processingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarFuncionarios();
    }

    @Override
    protected void onStop() {
        super.onStop();
        funcionarioReference.removeEventListener(funcionarioListener);
    }
}
