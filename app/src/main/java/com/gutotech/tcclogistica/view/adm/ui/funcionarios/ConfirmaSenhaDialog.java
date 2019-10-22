package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;


public class ConfirmaSenhaDialog {
    private Funcionario funcionario;
    private Dialog confirmDialog;

    private boolean allow;

    public ConfirmaSenhaDialog(Context context, Funcionario funcionario) {
        this.funcionario = funcionario;
        confirmDialog = new Dialog(context);
        confirmDialog.setContentView(R.layout.dialog_confirm_password);
        confirmDialog.setContentView(R.layout.dialog_confirm_password);
        confirmDialog.setCancelable(false);
    }

    public boolean confirm() {
        confirmDialog.show();

        final EditText passwordEditText = confirmDialog.findViewById(R.id.passwordEditText);
        Button okButton = confirmDialog.findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                if (password.equals(FuncionarioOn.funcionario.getLogin().getPassword())) {
                    allow = true;
                } else {
                    allow = false;
                }
            }
        });

        Button cancelButton = confirmDialog.findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog.dismiss();
            }
        });

        return allow;
    }

}
