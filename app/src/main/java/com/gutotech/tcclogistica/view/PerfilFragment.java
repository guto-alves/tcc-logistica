package com.gutotech.tcclogistica.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.config.Storage;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.adm.AdmMainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {
    private TextView nomeTextView, cargoTextView, rgTextView, cpfTextView, dataNascimentoTextView,
            enderecoTextView, telefoneTextView, celularTextView, emailTextView, ultimoLoginTextView;

    private TextView cnhTextView, categoriaTextView, veiculoTextView, anoTextView, placaTextView;

    private ImageView profileImageView;

    private final int CAMERA_CODE = 1;
    private final int GALLERY_CODE = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_perfil, container, false);

        profileImageView = root.findViewById(R.id.profileImageView);
        nomeTextView = root.findViewById(R.id.nomeTextView);
        cargoTextView = root.findViewById(R.id.cargoTextView);
        rgTextView = root.findViewById(R.id.rgTextView);
        cpfTextView = root.findViewById(R.id.cpfTextView);
        dataNascimentoTextView = root.findViewById(R.id.dataNascimentoTextView);
        enderecoTextView = root.findViewById(R.id.enderecoTextView);
        telefoneTextView = root.findViewById(R.id.telefoneTextView);
        celularTextView = root.findViewById(R.id.celularTextView);
        emailTextView = root.findViewById(R.id.emailTextView);
        ultimoLoginTextView = root.findViewById(R.id.ultimoLoginTextView);

        veiculoTextView = root.findViewById(R.id.veiculoTextView);
        cnhTextView = root.findViewById(R.id.cnhTextView);
        categoriaTextView = root.findViewById(R.id.categoriaTextView);
        anoTextView = root.findViewById(R.id.anoTextView);
        placaTextView = root.findViewById(R.id.placaTextView);

        setInformacaoes();

        if (FuncionarioOn.funcionario.getCargo().equals(Funcionario.MOTORISTA)) {
            GridLayout motorista = root.findViewById(R.id.motoristaGridLayout);
            motorista.setVisibility(View.VISIBLE);
        }

        if (FuncionarioOn.funcionario.isProfileImage())
            Storage.downloadProfile(getActivity(), profileImageView, FuncionarioOn.funcionario.getLogin().getUser());

        FloatingActionButton cameraImageButton = root.findViewById(R.id.cameraButton);
        cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_CODE);
            }
        });

        FloatingActionButton galleryImageButton = root.findViewById(R.id.galleryButton);
        galleryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setType("image/*");
                intent1.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent1, "Selecione uma imagem"), GALLERY_CODE);
            }
        });

        FloatingActionButton deleteImageButton = root.findViewById(R.id.deleteButton);
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference profileReference = ConfigFirebase.getStorage()
                        .child("images")
                        .child("funcionarios")
                        .child(FuncionarioOn.funcionario.getLogin().getUser() + ".jpg");

                profileReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        profileImageView.setImageResource(R.drawable.perfil_sem_foto);

                        NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                        View headerView = navigationView.getHeaderView(0);
                        ImageView profileImageView = headerView.findViewById(R.id.profileImageView);
                        profileImageView.setImageResource(R.drawable.perfil_sem_foto);

                        FuncionarioOn.funcionario.setProfileImage(false);
                        FuncionarioOn.funcionario.salvar();

                        Toasty.success(getActivity(), "Sucesso ao remover foto de perfil", Toasty.LENGTH_SHORT, true).show();
                    }
                });
            }
        });

        TextView alterarSenhaTextView = root.findViewById(R.id.alterarSenhaTextView);
        alterarSenhaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TrocaSenhaActivity.class));
            }
        });

        return root;
    }

    private void setInformacaoes() {
        nomeTextView.setText(FuncionarioOn.funcionario.getNome());
        cargoTextView.setText(FuncionarioOn.funcionario.getCargo());
        rgTextView.setText(FuncionarioOn.funcionario.getRg());
        cpfTextView.setText(FuncionarioOn.funcionario.getCpf());
        dataNascimentoTextView.setText(FuncionarioOn.funcionario.getDataNascimento());
        enderecoTextView.setText(FuncionarioOn.funcionario.getEndereco());
        telefoneTextView.setText(FuncionarioOn.funcionario.getTelefone());
        celularTextView.setText(FuncionarioOn.funcionario.getCelular());
        emailTextView.setText(FuncionarioOn.funcionario.getEmail());
        ultimoLoginTextView.setText(FuncionarioOn.funcionario.getLogin().getLastLogin());

        cnhTextView.setText(FuncionarioOn.funcionario.getCnh());
        veiculoTextView.setText(FuncionarioOn.funcionario.getVeiculo().getNome());
        categoriaTextView.setText(FuncionarioOn.funcionario.getVeiculo().getCategoria());
        anoTextView.setText(FuncionarioOn.funcionario.getVeiculo().getAno());
        placaTextView.setText(FuncionarioOn.funcionario.getVeiculo().getPlaca());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            Uri imagemSelecionada = data.getData();

            profileImageView.setImageURI(imagemSelecionada);

            uploadImage();
        }
    }

    private void uploadImage() {
        profileImageView.setDrawingCacheEnabled(true);
        profileImageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) profileImageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] dataByte = baos.toByteArray();

        StorageReference imagemRef = ConfigFirebase.getStorage()
                .child("images")
                .child("funcionarios")
                .child(FuncionarioOn.funcionario.getLogin().getUser() + ".jpg");

        UploadTask uploadTask = imagemRef.putBytes(dataByte);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(getActivity(), "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
                View headerView = navigationView.getHeaderView(0);
                ImageView profileImageView = headerView.findViewById(R.id.profileImageView);
                profileImageView.setImageResource(R.drawable.perfil_sem_foto);
                Storage.downloadProfile(getActivity(), profileImageView, FuncionarioOn.funcionario.getLogin().getUser());

                FuncionarioOn.funcionario.setProfileImage(true);
                FuncionarioOn.funcionario.salvar();

                Toasty.success(getActivity(), "Sucesso ao fazer upload da imagem", Toasty.LENGTH_SHORT, true).show();
            }
        });
    }

}
