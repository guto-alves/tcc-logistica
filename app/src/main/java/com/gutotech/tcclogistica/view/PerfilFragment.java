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
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.storage.UploadTask;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.Storage;
import com.gutotech.tcclogistica.helper.Actions;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;

import java.io.IOException;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {
    private TextView nomeTextView, cargoTextView, rgTextView, cpfTextView, dataNascimentoTextView,
            enderecoTextView, telefoneTextView, celularTextView, emailTextView, ultimoLoginTextView;

    private TextView categoriaTextView, nRegistroTextView, validadeTextView, primeiraHabilitacaoTextView, localTextView, dataEmissaoTextView;

    private CircleImageView profileImageView;

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

        categoriaTextView = root.findViewById(R.id.categoriaTextView);
        nRegistroTextView = root.findViewById(R.id.nRegistroTextView);
        validadeTextView = root.findViewById(R.id.validadeTextView);
        primeiraHabilitacaoTextView = root.findViewById(R.id.primeiraHabilitacaoTextView);
        localTextView = root.findViewById(R.id.localTextView);
        dataEmissaoTextView = root.findViewById(R.id.dataEmissaoTextView);

        setInformacaoes();

        if (FuncionarioOn.funcionario.getCargo().equals(Funcionario.MOTORISTA)) {
            GridLayout motorista = root.findViewById(R.id.motoristaGridLayout);
            motorista.setVisibility(View.VISIBLE);
        }

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FuncionarioOn.funcionario.temFoto())
                    Actions.startImageViewer(getContext(), FuncionarioOn.funcionario.getImage());
            }
        });

        FloatingActionButton cameraImageButton = root.findViewById(R.id.cameraButton);
        cameraImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.openCamera(PerfilFragment.this, 1);
            }
        });

        FloatingActionButton galleryImageButton = root.findViewById(R.id.galleryButton);
        galleryImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.openGallery(PerfilFragment.this, 2);
            }
        });

        FloatingActionButton deleteImageButton = root.findViewById(R.id.deleteButton);
        deleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FuncionarioOn.funcionario.getImage().isEmpty())
                    Storage.deleteProfile(deleteSuccessListener);
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
        if (FuncionarioOn.funcionario.temFoto())
            Storage.downloadProfile(getActivity(), profileImageView, FuncionarioOn.funcionario.getImage());

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

        categoriaTextView.setText(FuncionarioOn.funcionario.getCnh().getCategoria());
        nRegistroTextView.setText(FuncionarioOn.funcionario.getCnh().getNumeroRegistro());
        validadeTextView.setText(FuncionarioOn.funcionario.getCnh().getValidade());
        primeiraHabilitacaoTextView.setText(FuncionarioOn.funcionario.getCnh().getPrimeiraHabilitacao());
        localTextView.setText(FuncionarioOn.funcionario.getCnh().getLocal());
        dataEmissaoTextView.setText(FuncionarioOn.funcionario.getCnh().getDataEmissao());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
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

            if (bitmap != null) {
                if (FuncionarioOn.funcionario.temFoto())
                    Storage.deleteProfile(null);

                FuncionarioOn.funcionario.setImage(FuncionarioOn.funcionario.getLogin().getUser() + UUID.randomUUID().toString() + ".jpg");
                profileImageView.setImageBitmap(bitmap);
                Storage.uploadProfile(profileImageView, uploadFailureListener, uploadSuccessListener);
            }
        }
    }

    private final OnFailureListener uploadFailureListener = new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(getActivity(), "Erro ao fazer upload da imagem", Toast.LENGTH_SHORT).show();
        }
    };

    private final OnSuccessListener<UploadTask.TaskSnapshot> uploadSuccessListener = new OnSuccessListener<UploadTask.TaskSnapshot>() {
        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            FuncionarioOn.funcionario.salvar();

            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);

            ImageView profileImageView = headerView.findViewById(R.id.profileImageView);
            Storage.downloadProfile(getActivity(), profileImageView, FuncionarioOn.funcionario.getImage());

            Toasty.success(getActivity(), "Sucesso ao fazer upload da imagem", Toasty.LENGTH_SHORT, true).show();
        }
    };

    private final OnSuccessListener<Void> deleteSuccessListener = new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void aVoid) {
            profileImageView.setImageResource(R.drawable.perfil_sem_foto);

            NavigationView navigationView = getActivity().findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            ImageView profileImageView2 = headerView.findViewById(R.id.profileImageView);
            profileImageView2.setImageResource(R.drawable.perfil_sem_foto);

            FuncionarioOn.funcionario.setImage("");
            FuncionarioOn.funcionario.salvar();

            Toasty.success(getActivity(), "Sucesso ao remover foto de perfil", Toasty.LENGTH_SHORT, true).show();
        }
    };

}
