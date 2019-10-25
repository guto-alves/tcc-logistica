package com.gutotech.tcclogistica.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.FuncionarioOn;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class FuncionarioPerfilFragment extends Fragment {
    private ImageView profileImageView;
    private Bitmap bitmap;
    private final int CAMERA_CODE = 1;
    private final int GALLERY_CODE = 2;

    public FuncionarioPerfilFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_funcionario_perfil, container, false);

        profileImageView = root.findViewById(R.id.profileImageView);
        ImageButton cameraImageButton = root.findViewById(R.id.cameraImageButton);
        ImageButton galleryImageButton = root.findViewById(R.id.galleryImageButton);

        cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_CODE);
            }
        });

        galleryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Selecione uma imagem"), GALLERY_CODE);
//                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
//                    startActivityForResult(intent, GALLERY_CODE);
//                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GALLERY_CODE);
            }
        });

        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            bitmap = null;

            try {
                if (requestCode == CAMERA_CODE) {
                    bitmap = (Bitmap) data.getExtras().get("data");
                } else if (requestCode == GALLERY_CODE) {
                    Uri uri = data.getData();
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                }

                if (bitmap != null) {
                    profileImageView.setImageBitmap(bitmap);
                    uploadImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {
        StorageReference imagemRef = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child("fasdfasdf.jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dadosImagem = baos.toByteArray();

        UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toasty.success(getActivity(), "Sucesso ao fazer upload da imagem", Toasty.LENGTH_SHORT, true).show();

                taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener
                        (new OnSuccessListener<Uri>() {
                             @Override
                             public void onSuccess(Uri uri) {
                                 FuncionarioOn.funcionario.setProfileImage(true);
                                 FuncionarioOn.funcionario.salvar();
                             }
                         }
                        );
            }
        });
    }

}