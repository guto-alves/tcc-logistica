package com.gutotech.tcclogistica.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;

public class PasswordConfirmationDialog extends Dialog {
    private EditText passwordEditText;

    private Listener mListener;

    public PasswordConfirmationDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_confirm_password);
        setCancelable(false);

        passwordEditText = findViewById(R.id.passwordEditText);

        Button okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString();
                mListener.onPasswordConfirmed(password);
                dismiss();
            }
        });

        Button cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setListener(Listener listener) {
        mListener = listener;
    }

    @Override
    public void onDetachedFromWindow() {
        mListener = null;
        super.onDetachedFromWindow();
    }

    public interface Listener {
        void onPasswordConfirmed(String password);
    }

}
