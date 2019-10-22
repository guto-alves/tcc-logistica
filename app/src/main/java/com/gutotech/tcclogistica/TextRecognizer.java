package com.gutotech.tcclogistica;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class TextRecognizer {
    private Bitmap bitmap;

    private Context context;

    public TextRecognizer(Context context) {
        this.context = context;
    }

    public void detect() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_carregando);
        dialog.setCancelable(false);
        dialog.show();

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processText(firebaseVisionText);
                dialog.dismiss();
            }
        });
    }

    private void processText(FirebaseVisionText firebaseVisionText) {
        StringBuilder lines = new StringBuilder();

        for (FirebaseVisionText.TextBlock textBlock : firebaseVisionText.getTextBlocks()) {
            for (FirebaseVisionText.Line line : textBlock.getLines())
                lines.append(line.getText() + "\n");

            lines.append("\n");
        }
    }

    private void detectNota() {

    }


    private void detectColeta() {

    }
}
