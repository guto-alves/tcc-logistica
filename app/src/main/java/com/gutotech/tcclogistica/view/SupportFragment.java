package com.gutotech.tcclogistica.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.Actions;
import com.gutotech.tcclogistica.model.FuncionarioOn;

import java.util.Locale;

public class SupportFragment extends Fragment {
    private final String EMAIL_SUPORTE = "gutotech.tcclogistica@gmail.com";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_suporte, container, false);

        TextView textView = root.findViewById(R.id.textView);
        textView.setText(
                String.format(Locale.getDefault(),
                        "Olá %s! Você tem dificuldades com o aplicativo ou sugestão para melhorias? " +
                                "Pressione o botão abaixo para ir ao seu cliente de email preferido e nos informe.",
                        FuncionarioOn.funcionario.getNome().split(" ")[0]));

        Button sendButton = root.findViewById(R.id.sendButton);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.sendEmail(getActivity(), EMAIL_SUPORTE);
            }
        });

        return root;
    }


}