package com.gutotech.tcclogistica.view.roteirista.ui.nota;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.helper.Actions;
import com.gutotech.tcclogistica.model.CalculoImposto;
import com.gutotech.tcclogistica.model.Destinatario;
import com.gutotech.tcclogistica.model.Endereco;
import com.gutotech.tcclogistica.model.Nota;
import com.gutotech.tcclogistica.model.Transportador;

import es.dmoral.toasty.Toasty;

public class NotaDialog extends Dialog {
    private Nota nota;

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

    public NotaDialog(@NonNull final Context context, final Nota nota) {
        super(context);
        setContentView(R.layout.dialog_nota);
        setCancelable(false);

        this.nota = nota;

        numeroNotaEditText = findViewById(R.id.numeroNotaEditText);
        dataEmissaoEditText = findViewById(R.id.dataHoraEmissaoEditText);
        dataRecebimentoEditText = findViewById(R.id.dataHoraRecebimentoEditText);
        naturezaOperacaoEditText = findViewById(R.id.naturezaOperacaoEditText);
        ieEditText = findViewById(R.id.ieEditText);
        cnpjEditText = findViewById(R.id.cnpjEditText);

        nomeDestinatarioEditText = findViewById(R.id.nomeDestinatarioEditText);
        cnpjDestinatarioEditText = findViewById(R.id.cnpjDestinatarioEditText);
        enderecoDestinatarioEditText = findViewById(R.id.enderecoDestinatarioEditText);
        bairroDestinatarioEditText = findViewById(R.id.bairroDestinatarioEditText);
        cepDestinatarioEditText = findViewById(R.id.cepDestinatarioEditText);
        municipioDestinatarioEditText = findViewById(R.id.municipioDestinatarioEditText);

        valorFreteEditText = findViewById(R.id.valorFreteEditText);
        valorTotalProdutosEditText = findViewById(R.id.valorTotalProdutosEditText);
        valorTotalNotaEditText = findViewById(R.id.valorTotalNotaEditText);

        nomeTransportadorEditText = findViewById(R.id.nomeTransportadorEditText);
        fretePorContaEditText = findViewById(R.id.fretePorContaEditText);
        codANTTEditText = findViewById(R.id.codANTTEditText);
        placaVeiculoEditText = findViewById(R.id.placaVeiculoEditText);
        cnpjTransportadorEditText = findViewById(R.id.cnpjTransportadorEditText);
        enderecoTransportadorEditText = findViewById(R.id.enderecoTransportadorEditText);
        municipioTransportadorEditText = findViewById(R.id.municipioTransportadorEditText);
        ufTransportadorEditText = findViewById(R.id.ufTransportadorEditText);
        ieTransportadorEditText = findViewById(R.id.ieTransportadorEditText);

        adicionarMascaras();

        ImageButton openMapImageButton = findViewById(R.id.openMapImageButton);
        openMapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.openMap(context, NotaDialog.this.nota.getDestinatario().getEndereco().getEndereco());
            }
        });

        ImageButton fecharButton = findViewById(R.id.fecharButton);
        final ImageButton updateImageButton = findViewById(R.id.updateImageButton);
        final ImageButton modeEditImageButton = findViewById(R.id.modoEditImageButton);
        ImageButton excluirButton = findViewById(R.id.excluirButton);

        fecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("EXCLUIR NOTA FISCAL");
                alert.setMessage("Tem certeza que deseja excluir a nota?");
                alert.create();
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nota.excluir();
                        dismiss();
                    }
                });
                alert.setNegativeButton("Não", null);
                alert.show();
            }
        });

        modeEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(true);
                modeEditImageButton.setVisibility(View.GONE);
                updateImageButton.setVisibility(View.VISIBLE);
            }
        });

        updateImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarNota();
                Toasty.success(getContext(), "Nota atualizada", Toasty.LENGTH_SHORT, true).show();

                changeMode(false);
                updateImageButton.setVisibility(View.GONE);
                modeEditImageButton.setVisibility(View.VISIBLE);
            }
        });

        setInformations();
        changeMode(false);
    }

    private void setInformations() {
        numeroNotaEditText.setText(nota.getNumero());
        dataEmissaoEditText.setText(nota.getDataEmissao());
        dataRecebimentoEditText.setText(nota.getDataRecebimento());
        naturezaOperacaoEditText.setText(nota.getNaturezaDaOperacao());
        ieEditText.setText(nota.getInscricaoEstadual());
        cnpjEditText.setText(nota.getCnpj());

        nomeDestinatarioEditText.setText(nota.getDestinatario().getNome());
        cnpjDestinatarioEditText.setText(nota.getDestinatario().getCnpj());
        enderecoDestinatarioEditText.setText(nota.getDestinatario().getEndereco().getEndereco());
        bairroDestinatarioEditText.setText(nota.getDestinatario().getEndereco().getBairro());
        cepDestinatarioEditText.setText(nota.getDestinatario().getEndereco().getCep());
        municipioDestinatarioEditText.setText(nota.getDestinatario().getEndereco().getMunicipio());

        valorFreteEditText.setText(nota.getCalculoImposto().getValorFrete());
        valorTotalProdutosEditText.setText(nota.getCalculoImposto().getValorTotalProdutos());
        valorTotalNotaEditText.setText(nota.getCalculoImposto().getValorTotalNota());

        nomeTransportadorEditText.setText(nota.getTransportador().getNome());
        fretePorContaEditText.setText(nota.getTransportador().getFretePorConta());
        codANTTEditText.setText(nota.getTransportador().getCodANTT());
        placaVeiculoEditText.setText(nota.getTransportador().getPlacaDoVeiculo());
        cnpjTransportadorEditText.setText(nota.getTransportador().getCnpj());
        enderecoTransportadorEditText.setText(nota.getTransportador().getEndereco());
        municipioTransportadorEditText.setText(nota.getTransportador().getMunicipio());
        ufTransportadorEditText.setText(nota.getTransportador().getUf());
        ieTransportadorEditText.setText(nota.getTransportador().getInscricaoEstadual());
    }

    private void atualizarNota() {
        if (isValidFields(numeroNotaEditText.getText().toString())) {
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
        }
    }

    private boolean isValidFields(String numeroNota) {
        boolean valid = true;

        if (numeroNota.isEmpty()) {
            numeroNotaEditText.setError("Este campo é obrigatório");
            valid = false;
        }

        return valid;
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

    private void changeMode(boolean mode) {
        numeroNotaEditText.setEnabled(mode);
        dataEmissaoEditText.setEnabled(mode);
        dataRecebimentoEditText.setEnabled(mode);
        naturezaOperacaoEditText.setEnabled(mode);
        ieEditText.setEnabled(mode);
        cnpjEditText.setEnabled(mode);

        nomeDestinatarioEditText.setEnabled(mode);
        cnpjDestinatarioEditText.setEnabled(mode);
        enderecoDestinatarioEditText.setEnabled(mode);
        bairroDestinatarioEditText.setEnabled(mode);
        cepDestinatarioEditText.setEnabled(mode);
        municipioDestinatarioEditText.setEnabled(mode);

        valorFreteEditText.setEnabled(mode);
        valorTotalProdutosEditText.setEnabled(mode);
        valorTotalNotaEditText.setEnabled(mode);

        nomeTransportadorEditText.setEnabled(mode);
        fretePorContaEditText.setEnabled(mode);
        codANTTEditText.setEnabled(mode);
        placaVeiculoEditText.setEnabled(mode);
        cnpjTransportadorEditText.setEnabled(mode);
        enderecoTransportadorEditText.setEnabled(mode);
        municipioTransportadorEditText.setEnabled(mode);
        ufTransportadorEditText.setEnabled(mode);
        ieTransportadorEditText.setEnabled(mode);
    }
}
