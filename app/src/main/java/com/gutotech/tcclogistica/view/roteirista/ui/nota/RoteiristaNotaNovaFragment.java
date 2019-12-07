package com.gutotech.tcclogistica.view.roteirista.ui.nota;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.TextRecognizer;
import com.gutotech.tcclogistica.model.CalculoImposto;
import com.gutotech.tcclogistica.model.Destinatario;
import com.gutotech.tcclogistica.model.Endereco;
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.model.Transportador;
import com.gutotech.tcclogistica.view.OpenCameraOrGalleryDialogFragment;
import com.gutotech.tcclogistica.view.ProcessingDialog;

import java.io.IOException;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class RoteiristaNotaNovaFragment extends Fragment {
    private Nota nota = new Nota();

    private EditText numeroNotaEditText;
    private EditText dataEmissaoEditText;
    private EditText dataRecebimentoEditText;
    private EditText naturezaOperacaoEditText;
    private EditText ieEditText;
    private EditText cnpjEditText;

    // DESTINATARIO/REMETENTE
    private EditText nomeDestinatarioEditText, cnpjDestinatarioEditText, enderecoDestinatarioEditText, bairroDestinatarioEditText, cepDestinatarioEditText,
            municipioDestinatarioEditText;

    // CÁLCULO DO IMPOSTO
    private EditText valorFreteEditText, valorTotalProdutosEditText, valorTotalNotaEditText;

    // TRANSPORTADOR/VOLUMES TRANSPORTADOS
    private EditText nomeTransportadorEditText, fretePorContaEditText, codANTTEditText, placaVeiculoEditText, cnpjTransportadorEditText,
            enderecoTransportadorEditText, municipioTransportadorEditText, ufTransportadorEditText, ieTransportadorEditText;

    private TextRecognizer textRecognizer;

    private ProcessingDialog processingDialog;

    public RoteiristaNotaNovaFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_roteirista_nota_nova, container, false);

        numeroNotaEditText = root.findViewById(R.id.numeroNotaEditText);
        dataEmissaoEditText = root.findViewById(R.id.dataHoraEmissaoEditText);
        dataRecebimentoEditText = root.findViewById(R.id.dataHoraRecebimentoEditText);
        naturezaOperacaoEditText = root.findViewById(R.id.naturezaOperacaoEditText);
        ieEditText = root.findViewById(R.id.ieEditText);
        cnpjEditText = root.findViewById(R.id.cnpjEditText);

        nomeDestinatarioEditText = root.findViewById(R.id.nomeDestinatarioEditText);
        cnpjDestinatarioEditText = root.findViewById(R.id.cnpjDestinatarioEditText);
        enderecoDestinatarioEditText = root.findViewById(R.id.enderecoDestinatarioEditText);
        bairroDestinatarioEditText = root.findViewById(R.id.bairroDestinatarioEditText);
        cepDestinatarioEditText = root.findViewById(R.id.cepDestinatarioEditText);
        municipioDestinatarioEditText = root.findViewById(R.id.municipioDestinatarioEditText);

        valorFreteEditText = root.findViewById(R.id.valorFreteEditText);
        valorTotalProdutosEditText = root.findViewById(R.id.valorTotalProdutosEditText);
        valorTotalNotaEditText = root.findViewById(R.id.valorTotalNotaEditText);

        nomeTransportadorEditText = root.findViewById(R.id.nomeTransportadorEditText);
        fretePorContaEditText = root.findViewById(R.id.fretePorContaEditText);
        codANTTEditText = root.findViewById(R.id.codANTTEditText);
        placaVeiculoEditText = root.findViewById(R.id.placaVeiculoEditText);
        cnpjTransportadorEditText = root.findViewById(R.id.cnpjTransportadorEditText);
        enderecoTransportadorEditText = root.findViewById(R.id.enderecoTransportadorEditText);
        municipioTransportadorEditText = root.findViewById(R.id.municipioTransportadorEditText);
        ufTransportadorEditText = root.findViewById(R.id.ufTransportadorEditText);
        ieTransportadorEditText = root.findViewById(R.id.ieTransportadorEditText);

        adicionarMascaras();

        ImageButton textRecognizerImageButton = root.findViewById(R.id.textRecognizerImageButton);
        textRecognizerImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCameraOrGalleryDialogFragment openCameraOrGalleryDialogFragment = new OpenCameraOrGalleryDialogFragment();
                openCameraOrGalleryDialogFragment.setListener(openCameraOrGalleryListener);
                openCameraOrGalleryDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });

        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(salvarButtonListener);

        Button limparButton = root.findViewById(R.id.limparButton);
        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        textRecognizer = new TextRecognizer(textRecognizerListener);

        processingDialog = new ProcessingDialog(getActivity());

        return root;
    }

    private final OpenCameraOrGalleryDialogFragment.Listener openCameraOrGalleryListener = new OpenCameraOrGalleryDialogFragment.Listener() {

        @Override
        public void onBitmapResult(Bitmap bitmap) {
            processingDialog.show();
            textRecognizer.detect(bitmap);
        }
    };

    private final TextRecognizer.Listener textRecognizerListener = new TextRecognizer.Listener() {
        @Override
        public void onTextResult(String text) {
            processingDialog.dismiss();
        }
    };

    private final View.OnClickListener salvarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (isValidFields(numeroNotaEditText.getText().toString())) {
                nota.setId(UUID.randomUUID().toString());
                nota.setNumero(numeroNotaEditText.getText().toString());
                nota.setDataEmissao(dataEmissaoEditText.getText().toString());
                nota.setDataRecebimento(dataRecebimentoEditText.getText().toString());
                nota.setNaturezaDaOperacao(naturezaOperacaoEditText.getText().toString());
                nota.setInscricaoEstadual(ieEditText.getText().toString());
                nota.setCnpj(cnpjEditText.getText().toString());

                Destinatario destinatario = new Destinatario();
                destinatario.setNome(nomeDestinatarioEditText.getText().toString());
                destinatario.setCnpj(cnpjDestinatarioEditText.getText().toString());
                destinatario.setEndereco(new Endereco(enderecoDestinatarioEditText.getText().toString(),
                        cepDestinatarioEditText.getText().toString(),
                        bairroDestinatarioEditText.getText().toString(),
                        municipioDestinatarioEditText.getText().toString(), ""));
                nota.setDestinatario(destinatario);

                CalculoImposto calculoImposto = new CalculoImposto(valorTotalProdutosEditText.getText().toString(), valorFreteEditText.
                        getText().toString(),
                        valorTotalNotaEditText.getText().toString());
                nota.setCalculoImposto(calculoImposto);

                Transportador transportador = new Transportador();
                transportador.setNome(nomeTransportadorEditText.getText().toString());
                transportador.setFretePorConta(fretePorContaEditText.getText().toString());
                transportador.setCodANTT(codANTTEditText.getText().toString());
                transportador.setPlacaDoVeiculo(placaVeiculoEditText.getText().toString());
                transportador.setCnpj(cnpjTransportadorEditText.getText().toString());
                transportador.setInscricaoEstadual(ieTransportadorEditText.getText().toString());

                // endereço transportador
                transportador.setEndereco(enderecoTransportadorEditText.getText().toString());
                transportador.setMunicipio(municipioTransportadorEditText.getText().toString());
                transportador.setUf(ufTransportadorEditText.getText().toString());
                nota.setTransportador(transportador);

                nota.setEstoque(true);
                nota.salvar();

                limparCampos();
                Toasty.success(getActivity(), "Nota cadastrada com sucesso!", Toasty.LENGTH_SHORT, true).show();
            }
        }
    };

    private void limparCampos() {
        numeroNotaEditText.setText("");
        dataEmissaoEditText.setText("");
        dataRecebimentoEditText.setText("");
        naturezaOperacaoEditText.setText("");
        ieEditText.setText("");
        cnpjEditText.setText("");

        nomeDestinatarioEditText.setText("");
        cnpjDestinatarioEditText.setText("");
        enderecoDestinatarioEditText.setText("");
        bairroDestinatarioEditText.setText("");
        cepDestinatarioEditText.setText("");
        municipioDestinatarioEditText.setText("");

        valorFreteEditText.setText("");
        valorTotalProdutosEditText.setText("");
        valorTotalNotaEditText.setText("");

        nomeTransportadorEditText.setText("");
        fretePorContaEditText.setText("");
        codANTTEditText.setText("");
        placaVeiculoEditText.setText("");
        cnpjTransportadorEditText.setText("");
        enderecoTransportadorEditText.setText("");
        municipioTransportadorEditText.setText("");
        ufTransportadorEditText.setText("");
        ieTransportadorEditText.setText("");
    }

    private void adicionarMascaras() {
        dataEmissaoEditText.addTextChangedListener(new MaskTextWatcher(dataEmissaoEditText, new SimpleMaskFormatter("NN/NN/NNNN NN:NN:NN")));
        dataRecebimentoEditText.addTextChangedListener(new MaskTextWatcher(dataRecebimentoEditText, new SimpleMaskFormatter("NN/NN/NNNN NN:NN:NN")));
        ieEditText.addTextChangedListener(new MaskTextWatcher(ieEditText, new SimpleMaskFormatter("NNN.NNN.NNN.NNN")));
        cnpjEditText.addTextChangedListener(new MaskTextWatcher(cnpjEditText, new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN")));

        cnpjDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(cnpjDestinatarioEditText, new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN")));
        cepDestinatarioEditText.addTextChangedListener(new MaskTextWatcher(cepDestinatarioEditText, new SimpleMaskFormatter("NN.NNN-NNN")));

        placaVeiculoEditText.addTextChangedListener(new MaskTextWatcher(placaVeiculoEditText, new SimpleMaskFormatter("LLL-NNNN")));
        cnpjTransportadorEditText.addTextChangedListener(new MaskTextWatcher(cnpjTransportadorEditText, new SimpleMaskFormatter("NN.NNN.NNN/NNNN-NN")));
        ufTransportadorEditText.addTextChangedListener(new MaskTextWatcher(ufTransportadorEditText, new SimpleMaskFormatter("UU")));
        ieTransportadorEditText.addTextChangedListener(new MaskTextWatcher(ieTransportadorEditText, new SimpleMaskFormatter("NNN.NNN.NNN.NNN")));
    }

    private boolean isValidFields(String numeroNota) {
        boolean valid = true;

        if (numeroNota.isEmpty()) {
            numeroNotaEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        return valid;
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

            processingDialog.show();
            textRecognizer.detect(bitmap);
        }
    }

}
