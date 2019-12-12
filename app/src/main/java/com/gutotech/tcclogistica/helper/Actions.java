package com.gutotech.tcclogistica.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.fragment.app.Fragment;

import com.gutotech.tcclogistica.view.ImageViewerActivity;

public class Actions {

    public static void sendEmail(Context context, String email) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        context.startActivity(intent);
    }

    public static void openGallery(Fragment context, int code) {
        Intent intent1 = new Intent();
        intent1.setType("image/*");
        intent1.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(Intent.createChooser(intent1, "Selecione uma imagem"), code);
    }

    public static void openCamera(Fragment context, int code) {
        context.startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), code);
    }

    public static void dial(Context context, String phone) {
        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone)));
    }

    public static void openMap(Context context, String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + address);
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        context.startActivity(mapIntent);
    }

    public static void startImageViewer(Context context, String image) {
        Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra("image", image);
        context.startActivity(intent);
    }

}
