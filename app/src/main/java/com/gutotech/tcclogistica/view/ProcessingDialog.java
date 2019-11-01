package com.gutotech.tcclogistica.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.annotation.NonNull;

import com.gutotech.tcclogistica.R;

public class ProcessingDialog extends Dialog {

    public ProcessingDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_processing);
        setCancelable(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
