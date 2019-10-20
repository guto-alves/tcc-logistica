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

import es.dmoral.toasty.Toasty;

public class LoginActivity extends AppCompatActivity {
    private EditText userEditText, passwordEditText;

    private Dialog processingDialog;

    private DatabaseReference funcionarioReference;
    private Query funcionarioQuery;
    private ValueEventListener funcionarioListener;

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

            switch (Integer.parseInt(user)) {
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

            if (isValidField(user, password)) {
                processingDialog.show();

                funcionarioQuery = funcionarioReference.orderByKey().equalTo(user);

                funcionarioListener = funcionarioQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Funcionario funcionario = dataSnapshot.getValue(Funcionario.class);

                        funcionarioQuery.removeEventListener(funcionarioListener);

                        if (funcionario != null) {
                            if (funcionario.getLogin() != null) {
                                if (funcionario.getLogin().getPassword().equals(password)) {
                                    funcionario.setOnline(true);
                                    funcionario.setUltimoLogin(DateCustom.getDataEHora());
                                    FuncionarioOn.funcionario = funcionario;

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

                                } else
                                    Toasty.error(LoginActivity.this, "Senha inválida", Toast.LENGTH_SHORT, true).show();
                            } else
                                Toasty.error(LoginActivity.this, "Vai tomar no cu " + funcionario.getNome(), Toast.LENGTH_SHORT, true).show();

                        } else
                            Toasty.error(LoginActivity.this, "Usuário não encontrado", Toast.LENGTH_SHORT, true).show();

                        processingDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
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

    private void isManutencao() {
        DatabaseReference manutencaoReference = ConfigFirebase.getDatabase().child("manutencao");

        manutencaoReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean manutencao = (boolean) dataSnapshot.getValue();

                if (manutencao) {
                    Toasty.warning(getApplicationContext(), "Estamos em manutençao!", Toasty.LENGTH_SHORT, true).show();
                } else
                    Toasty.success(getApplicationContext(), "Não estamos em manutençao!", Toasty.LENGTH_SHORT, true).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        isManutencao();
    }
}
