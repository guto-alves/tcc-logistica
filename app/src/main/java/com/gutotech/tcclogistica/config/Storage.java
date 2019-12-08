package com.gutotech.tcclogistica.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gutotech.tcclogistica.model.FuncionarioOn;

import java.io.ByteArrayOutputStream;

public class Storage {

    private static void downloadImage(Context context, StorageReference storageReference, ImageView imageView) {
        Glide.with(context)
                .using(new FirebaseImageLoader())
                .load(storageReference)
                .into(imageView);
    }

    public static void downloadProfile(Context context, ImageView imageView, String image) {
        StorageReference profileReference = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child(image);
        downloadImage(context, profileReference, imageView);
    }

    public static void deleteProfile(OnSuccessListener<Void> listener) {
        StorageReference profileReference = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child(FuncionarioOn.funcionario.getImage());

        profileReference.delete().addOnSuccessListener(listener);
    }

    public static void uploadProfile(ImageView imageView, OnFailureListener failureListener, OnSuccessListener<UploadTask.TaskSnapshot> successListener) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] dataByte = baos.toByteArray();

        StorageReference imagemRef = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child(FuncionarioOn.funcionario.getImage());

        UploadTask uploadTask = imagemRef.putBytes(dataByte);
        uploadTask.addOnFailureListener(failureListener).addOnSuccessListener(successListener);
    }

}
