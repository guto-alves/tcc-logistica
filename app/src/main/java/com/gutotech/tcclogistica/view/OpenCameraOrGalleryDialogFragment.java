package com.gutotech.tcclogistica.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.Actions;

import java.io.IOException;

public class OpenCameraOrGalleryDialogFragment extends BottomSheetDialogFragment {
    private Listener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_open_camera_or_gallery_dialog, container, false);

        LinearLayout cameraLinearLayout = view.findViewById(R.id.cameraLinearLayout);
        cameraLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.openCamera(OpenCameraOrGalleryDialogFragment.this, 1);
            }
        });

        LinearLayout galleryLinearLayout = view.findViewById(R.id.galleryLinearLayout);
        galleryLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.openGallery(OpenCameraOrGalleryDialogFragment.this, 2);
            }
        });

        return view;
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    public interface Listener {
        void onBitmapResult(Bitmap bitmap);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            Bitmap bitmap = null;

            try {
                if (requestCode == 1)
                    bitmap = (Bitmap) data.getExtras().get("data");
                else if (requestCode == 2) {
                    Uri uri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            mListener.onBitmapResult(bitmap);
            dismiss();
        }
    }
}
