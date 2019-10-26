package com.gutotech.tcclogistica.config;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;

public class Storage {

    private static void downloadImage(Context context, StorageReference storageReference, ImageView imageView) {
        Glide.with(context)
                .load(storageReference)
                .into(imageView);
    }

    public static void downloadProfile(Context context, ImageView imageView, String user) {
        StorageReference profileReference = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child(user + ".jpg");
        downloadImage(context, profileReference, imageView);
    }
}
