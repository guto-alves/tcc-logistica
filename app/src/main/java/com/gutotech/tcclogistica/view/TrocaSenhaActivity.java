package com.gutotech.tcclogistica.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.tapadoo.alerter.Alerter;

public class TrocaSenhaActivity extends AppCompatActivity {
    private TextInputLayout senhaAtualTextInput, senhaNovaTextInput, senhaNovaTextInput2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_troca_senha);

        senhaAtualTextInput = findViewById(R.id.senhaAtualTextInput);
        senhaNovaTextInput = findViewById(R.id.senhaNovaTextInput);
        senhaNovaTextInput2 = findViewById(R.id.senhaNovaTextInput2);

        Button confirmarButton = findViewById(R.id.confirmarButton);
        confirmarButton.setOnClickListener(confirmarButtonListener);

        Toolbar toolbar = findViewById(R.id.toolbarPrincipal);
        toolbar.setTitle("Troca de Senha");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private final View.OnClickListener confirmarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String password = senhaAtualTextInput.getEditText().getText().toString().trim();
            String newPassword = senhaNovaTextInput.getEditText().getText().toString().trim();
            String confirmNewPassword = senhaNovaTextInput2.getEditText().getText().toString().trim();

            if (isValidField(password, newPassword, confirmNewPassword)) {
                if (validPasswords(password, newPassword, confirmNewPassword)) {
                    FuncionarioOn.funcionario.getLogin().setPassword(newPassword);
                    FuncionarioOn.funcionario.salvar();

                    Alerter.create(TrocaSenhaActivity.this)
                            .setTitle("Senha alterada com sucesso")
                            .setBackgroundColor(R.color.colorGreen)
                            .setIcon(R.drawable.ic_done_24dp)
                            .show();

                    clearFields();
                }
            }
        }
    };

    private boolean validPasswords(String password, String newPassword, String confirmNewPassword) {
        if (password.equals(FuncionarioOn.funcionario.getLogin().getPassword())) {
            if (newPassword.equals(confirmNewPassword))
                return true;
            else
                displayErrorMessage("Senhas não conferem!");
        } else
            displayErrorMessage("Senha atual inválida!");

        return false;
    }

    private void displayErrorMessage(String message) {
        Alerter.create(TrocaSenhaActivity.this)
                .setTitle("Erro")
                .setText(message)
                .setBackgroundColor(android.R.color.holo_red_light)
                .setIcon(R.drawable.ic_info_24dp)
                .show();
    }

    private boolean isValidField(String password, String newPassword, String confirmNewPassword) {
        boolean valid = true;

        if (password.isEmpty()) {
            senhaAtualTextInput.setError("É obrigatório informar a senha atual.");
            valid = false;
        } else
            senhaAtualTextInput.setError(null);

        if (newPassword.isEmpty()) {
            senhaNovaTextInput.setError("É obrigatório informar a nova senha.");
            valid = false;
        } else
            senhaNovaTextInput.setError(null);

        if (confirmNewPassword.isEmpty()) {
            senhaNovaTextInput2.setError("É obrigatório repetir a nova senha.");
            valid = false;
        } else
            senhaNovaTextInput2.setError(null);

        return valid;
    }

    private void clearFields() {
        senhaAtualTextInput.getEditText().setText("");
        senhaNovaTextInput.getEditText().setText("");
        senhaNovaTextInput2.getEditText().setText("");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }
}
