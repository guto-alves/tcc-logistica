package com.gutotech.tcclogistica.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.gutotech.tcclogistica.R;

public class EscolhaSimOuNaoDialog extends Dialog {

    public EscolhaSimOuNaoDialog(@NonNull Context context, final ChoiceListener listener) {
        super(context);
        setContentView(R.layout.dialog_choose_thumbs_up_down);
        setCancelable(false);

        ImageButton simImageButton = findViewById(R.id.simImageButton);
        simImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChoice(R.drawable.ic_thumb_up_24dp);
                dismiss();
            }
        });

        ImageButton naoImageButton = findViewById(R.id.naoImageButton);
        naoImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onChoice(R.drawable.ic_thumb_down_24dp);
                dismiss();
            }
        });
    }

    public interface ChoiceListener {
        void onChoice(int choice);
    }
}
