package com.gutotech.tcclogistica.helper;

import android.graphics.Bitmap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class TextRecognizer {
    private Listener listener;

    public TextRecognizer(Listener listener) {
        this.listener = listener;
    }

    public void detect(Bitmap bitmap) {
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

        FirebaseVisionTextRecognizer textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();

        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                processText(firebaseVisionText);
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

        listener.onTextResult(lines.toString());
    }

    public interface Listener {
        void onTextResult(String text);
    }
}
