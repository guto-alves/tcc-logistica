package com.gutotech.tcclogistica.config;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.StorageReference;
import com.gutotech.tcclogistica.model.FuncionarioOn;

public class Storage {

    public static void downloadImage(Context context, StorageReference storageReference, ImageView imageView) {
        Glide.with(context)
                .load(storageReference)
                .into(imageView);
    }

    public static void downloadProfile(Context context, ImageView imageView) {
        StorageReference profileReference = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child(FuncionarioOn.funcionario.getLogin().getUser() + ".jpg");
        downloadImage(context, profileReference, imageView);
    }
}
