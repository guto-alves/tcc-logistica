package com.gutotech.tcclogistica.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.helper.DateCustom;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adm.AdmMainActivity;
import com.gutotech.tcclogistica.view.motorista.MotoristaMainActivity;
import com.gutotech.tcclogistica.view.roteirista.RoteiristaMainActivity;

import java.util.LinkedList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout userTextInput, passwordTextInput;

    private List<Funcionario> funcionariosList = new LinkedList<>();

    private DatabaseReference funcionarioReference;
    private ValueEventListener funcionarioListener;

    private ProcessingDialog processingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userTextInput = findViewById(R.id.userTextInput);
        passwordTextInput = findViewById(R.id.passwordTextInput);

        EditText passwordEditText = findViewById(R.id.passwordEditText);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                login();
                return false;
            }
        });

        TextView esqueceuSenhaTextView = findViewById(R.id.esqueceuSenhaTextView);
        esqueceuSenhaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Esqueci a senha");
                builder.setMessage("Entre em contato mais rápido possível com o seu supervisor e solicite uma nova senha.");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", null);
                builder.create();
                builder.show();
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        processingDialog = new ProcessingDialog(this);

        funcionarioReference = ConfigFirebase.getDatabase().child("funcionario");
    }

    private void login() {
        final String user = userTextInput.getEditText().getText().toString().trim();
        final String password = passwordTextInput.getEditText().getText().toString().trim();

        if (isValidField(user, password)) {
            if (signInWith(user, password)) {
                FuncionarioOn.funcionario.setOnline(true);
                FuncionarioOn.funcionario.getLogin().setLastLogin(DateCustom.getDataEHora());
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

                finish();
            }
        }
    }

    private boolean isValidField(String user, String password) {
        boolean valid = true;

        if (user.isEmpty()) {
            userTextInput.setError("Usuário requerido.");
            valid = false;
        } else
            userTextInput.setError(null);

        if (password.isEmpty()) {
            passwordTextInput.setError("Senha requerida.");
            valid = false;
        } else
            passwordTextInput.setError(null);

        return valid;
    }

    private boolean signInWith(String user, String password) {
        for (Funcionario funcionario : funcionariosList) {
            if (funcionario.getLogin().getUser().equals(user)) {
                if (funcionario.getLogin().getPassword().equals(password)) {
                    FuncionarioOn.funcionario = funcionario;
                    return true;
                } else {
                    passwordTextInput.setError("Senha não corresponde.");
                    return false;
                }
            }
        }

        userTextInput.setError("Usuário inválido, por favor tente novamente.");

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
