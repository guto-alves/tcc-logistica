package com.gutotech.tcclogistica.config;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static DatabaseReference database;

    public static DatabaseReference getDatabase() {
        if (database == null)
            database = FirebaseDatabase.getInstance().getReference();
        return database;
    }

}
