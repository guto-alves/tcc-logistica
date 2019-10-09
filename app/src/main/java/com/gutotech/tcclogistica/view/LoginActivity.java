package com.gutotech.tcclogistica.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adm.AdmMainActivity;
import com.gutotech.tcclogistica.view.motorista.MotoristaMainActivity;
import com.gutotech.tcclogistica.view.roteirista.RoteiristaMainActivity;

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private final int ADM = 1;
    private final int ROTEIRISTA = 2;
    private final int MOTORISTA = 3;

    private EditText userEditText, passwordEditText;

    private Dialog processingDialog;

    private DatabaseReference funcionarioReference;
    private Query funcionarioQuery;
    private ValueEventListener funcionarioValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEditText = findViewById(R.id.userEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        processingDialog = new Dialog(this);
        processingDialog.setContentView(R.layout.dialog_carregando);
        //processingDialog.setCancelable(false);
        processingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        funcionarioReference = ConfigFirebase.getDatabase().child("funcionario");
    }

    public void entrar(View view) {
        processingDialog.show();

        final String user = userEditText.getText().toString();
        final String password = passwordEditText.getText().toString();

        if (isValidField(user, password)) {
            funcionarioQuery = funcionarioReference.orderByKey().equalTo(user);

            funcionarioValueEventListener = funcionarioQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Funcionario funcionario = dataSnapshot.getValue(Funcionario.class);

                    funcionarioQuery.removeEventListener(funcionarioValueEventListener);

                    if (funcionario != null) {
                        FuncionarioOn.funcionario = funcionario;

                        if (funcionario.getLogin().getPassword().equals(password)) {
                            switch (funcionario.getCargo()) {
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

                            finish();
                        } else
                            Toasty.error(LoginActivity.this, "Senha inválida", Toast.LENGTH_SHORT, true).show();
                    } else
                        Toasty.error(LoginActivity.this, "Usuário inválido", Toast.LENGTH_SHORT, true).show();

                    processingDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        processingDialog.dismiss();
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
