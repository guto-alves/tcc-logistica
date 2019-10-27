package com.gutotech.tcclogistica.view.suporte;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gutotech.tcclogistica.R;

public class SupportFragment extends Fragment {
    private EditText subjectEditText, messageEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_suporte, container, false);

        subjectEditText = root.findViewById(R.id.subjectEditText);
        messageEditText = root.findViewById(R.id.messageEditText);

        Button sendButton = root.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail(subjectEditText.getText().toString(), messageEditText.getText().toString());
                limparCampos();
            }
        });

        return root;
    }

    private void sendEmail(String subject, String message) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"gutotech.tcclogistica@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(intent, "Enviar"));
    }

    private void limparCampos() {
        subjectEditText.setText("");
        messageEditText.setText("");
    }

}