package com.gutotech.tcclogistica.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfigFirebase {
    private static DatabaseReference database;
    private static StorageReference storage;
    private static FirebaseVisionTextRecognizer textRecognizer;

    public static DatabaseReference getDatabase() {
        if (database == null)
            database = FirebaseDatabase.getInstance().getReference();
        return database;
    }

    public static StorageReference getStorage() {
        if (storage == null)
            storage = FirebaseStorage.getInstance().getReference();
        return storage;
    }

    public static FirebaseVisionTextRecognizer getTextRecognizer() {
        if (textRecognizer == null)
            textRecognizer = FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        return textRecognizer;
    }
}
