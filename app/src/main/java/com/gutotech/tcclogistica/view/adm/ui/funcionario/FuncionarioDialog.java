package com.gutotech.tcclogistica.view.adm.ui.funcionario;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.Storage;
import com.gutotech.tcclogistica.helper.Actions;
import com.gutotech.tcclogistica.model.CNH;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.view.PasswordConfirmationDialog;
import com.gutotech.tcclogistica.view.adapter.LoginFuncionarioExpandableAdapter;

import es.dmoral.toasty.Toasty;

public class FuncionarioDialog extends Dialog {
    private Funcionario funcionario;

    private ImageView perfilImageView;
    private TextView nomeTextView;
    private TextView cargoTextView;
    private EditText rgEditText;
    private EditText cpfEditText;
    private EditText dataNascimentoEditText;
    private EditText enderecoEditText;
    private EditText celularEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private TextView ultimoLoginTextView;
    private ExpandableListView loginExpandableListView;

    private EditText categoriaEditText, nRegistroEditText, validadeEditText, primeiraHabilitacaoEditText, localEditText, dataEmissaoEditText;

    private boolean passwordConfirmed;

    public FuncionarioDialog(@NonNull Context context, final Funcionario funcionario) {
        super(context);
        setContentView(R.layout.dialog_funcionario);

        this.funcionario = funcionario;

        perfilImageView = findViewById(R.id.profileImageView);
        nomeTextView = findViewById(R.id.nomeTextView);
        cargoTextView = findViewById(R.id.cargoTextView);
        rgEditText = findViewById(R.id.rgEditText);
        cpfEditText = findViewById(R.id.cpfEditText);
        dataNascimentoEditText = findViewById(R.id.dataNascimentoEditText);
        enderecoEditText = findViewById(R.id.enderecoEditText);
        celularEditText = findViewById(R.id.celularEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        ultimoLoginTextView = findViewById(R.id.ultimoLoginTextView);
        loginExpandableListView = findViewById(R.id.loginFuncionarioExpandable);
        categoriaEditText = findViewById(R.id.categoriaEditText);
        nRegistroEditText = findViewById(R.id.nRegistroEditText);
        validadeEditText = findViewById(R.id.validadeEditText);
        primeiraHabilitacaoEditText = findViewById(R.id.primeiraHabilitacaoEditText);
        localEditText = findViewById(R.id.localEditText);
        dataEmissaoEditText = findViewById(R.id.dataEmissaoEdiText);

        addMasks();
        setInformations();

        ImageButton phoneCallImageButton = findViewById(R.id.dialCelularImageButton);
        phoneCallImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.dial(getContext(), funcionario.getCelular());
            }
        });

        ImageButton telefoneCallImageButton = findViewById(R.id.dialTelefoneImageButton);
        telefoneCallImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.dial(getContext(), funcionario.getTelefone());
            }
        });

        ImageButton sendEmailmageButton = findViewById(R.id.sendEmailmageButton);
        sendEmailmageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Actions.sendEmail(getContext(), funcionario.getEmail());
            }
        });

        LoginFuncionarioExpandableAdapter loginAdapter = new LoginFuncionarioExpandableAdapter(getContext(), funcionario);
        loginExpandableListView.setAdapter(loginAdapter);
        loginExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (!passwordConfirmed) {
                    loginExpandableListView.collapseGroup(0);

                    PasswordConfirmationDialog passwordConfirmationDialog = new PasswordConfirmationDialog(getContext());
                    passwordConfirmationDialog.setListener(passwordConfirmationlistener);
                    passwordConfirmationDialog.show();
                }
            }
        });
        loginExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                passwordConfirmed = false;
            }
        });

        ImageButton fecharButton = findViewById(R.id.fecharButton);
        fecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ImageButton excluirButton = findViewById(R.id.excluirButton);
        if (FuncionarioOn.funcionario.getLogin().getUser().equals(funcionario.getLogin().getUser()))
            excluirButton.setVisibility(View.GONE);

        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("EXCLUIR FUNCIONÁRIO");
                alert.setMessage("Tem certeza que deseja excluir o " + funcionario.getCargo() + " " + funcionario.getNome().toUpperCase() + "?");
                alert.create();
                alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        funcionario.excluir();
                        dismiss();
                    }
                });
                alert.setNegativeButton("Não", null);
                alert.show();
            }
        });

        final ImageButton updateImageButton = findViewById(R.id.updateImageButton);
        final ImageButton modeEditImageButton = findViewById(R.id.modoEditImageButton);

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
                updateEmployee();
                Toasty.success(getContext(), "Funcionário atualizado", Toasty.LENGTH_SHORT, true).show();

                changeMode(false);
                updateImageButton.setVisibility(View.GONE);
                modeEditImageButton.setVisibility(View.VISIBLE);
            }
        });
    }

    private void setInformations() {
        if (!funcionario.getImage().isEmpty())
            Storage.downloadProfile(getContext(), perfilImageView, funcionario.getImage());

        nomeTextView.setText(funcionario.getNome());
        cargoTextView.setText(funcionario.getCargo());
        rgEditText.setText(funcionario.getRg());
        cpfEditText.setText(funcionario.getCpf());
        dataNascimentoEditText.setText(funcionario.getDataNascimento());
        enderecoEditText.setText(funcionario.getEndereco());
        celularEditText.setText(funcionario.getCelular());
        telefoneEditText.setText(funcionario.getTelefone());
        emailEditText.setText(funcionario.getEmail());
        ultimoLoginTextView.setText(funcionario.getLogin().getLastLogin());

        if (funcionario.getCargo().equals(Funcionario.MOTORISTA)) {
            categoriaEditText.setText(funcionario.getCnh().getCategoria());
            nRegistroEditText.setText(funcionario.getCnh().getNumeroRegistro());
            validadeEditText.setText(funcionario.getCnh().getValidade());
            primeiraHabilitacaoEditText.setText(funcionario.getCnh().getPrimeiraHabilitacao());
            localEditText.setText(funcionario.getCnh().getLocal());
            dataEmissaoEditText.setText(funcionario.getCnh().getDataEmissao());

            LinearLayout motoristaTextViews = findViewById(R.id.motoristaLinearLayout);
            motoristaTextViews.setVisibility(View.VISIBLE);
        }

        changeMode(false);
    }

    private final PasswordConfirmationDialog.Listener passwordConfirmationlistener = new PasswordConfirmationDialog.Listener() {
        @Override
        public void onPasswordConfirmed(String password) {
            if (password.equals(FuncionarioOn.funcionario.getLogin().getPassword())) {
                passwordConfirmed = true;
                loginExpandableListView.expandGroup(0);
            } else {
                passwordConfirmed = false;
                Toasty.error(getContext(), "Acesso negado, senha inválida!", Toasty.LENGTH_SHORT, true).show();
            }
        }
    };

    private void updateEmployee() {
        funcionario.setRg(rgEditText.getText().toString());
        funcionario.setCpf(cpfEditText.getText().toString());
        funcionario.setEndereco(enderecoEditText.getText().toString());
        funcionario.setCelular(celularEditText.getText().toString());
        funcionario.setTelefone(telefoneEditText.getText().toString());
        funcionario.setEmail(emailEditText.getText().toString());
        funcionario.setDataNascimento(dataNascimentoEditText.getText().toString());
        CNH cnh = new CNH();
        cnh.setCategoria(categoriaEditText.getText().toString());
        cnh.setNumeroRegistro(nRegistroEditText.getText().toString());
        cnh.setValidade(validadeEditText.getText().toString());
        cnh.setPrimeiraHabilitacao(primeiraHabilitacaoEditText.getText().toString());
        cnh.setLocal(localEditText.getText().toString());
        cnh.setDataEmissao(dataEmissaoEditText.getText().toString());
        funcionario.setCnh(cnh);
        funcionario.salvar();
    }

    private void addMasks() {
        rgEditText.addTextChangedListener(new MaskTextWatcher(rgEditText, new SimpleMaskFormatter("NN.NNN.NNN-N")));
        cpfEditText.addTextChangedListener(new MaskTextWatcher(cpfEditText, new SimpleMaskFormatter("NNN.NNN.NNN-NN")));
        celularEditText.addTextChangedListener(new MaskTextWatcher(celularEditText, new SimpleMaskFormatter("(NN) NNNNN-NNNN")));
        telefoneEditText.addTextChangedListener(new MaskTextWatcher(telefoneEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));
        dataNascimentoEditText.addTextChangedListener(new MaskTextWatcher(dataNascimentoEditText, new SimpleMaskFormatter("NN/NN/NNNN")));

        categoriaEditText.addTextChangedListener(new MaskTextWatcher(categoriaEditText, new SimpleMaskFormatter("UU")));
        nRegistroEditText.addTextChangedListener(new MaskTextWatcher(nRegistroEditText, new SimpleMaskFormatter("NNNNNNNNNNN")));
        validadeEditText.addTextChangedListener(new MaskTextWatcher(validadeEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        primeiraHabilitacaoEditText.addTextChangedListener(new MaskTextWatcher(primeiraHabilitacaoEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        dataEmissaoEditText.addTextChangedListener(new MaskTextWatcher(dataEmissaoEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
    }

    private void changeMode(boolean mode) {
        rgEditText.setEnabled(mode);
        cpfEditText.setEnabled(mode);
        dataNascimentoEditText.setEnabled(mode);
        enderecoEditText.setEnabled(mode);
        celularEditText.setEnabled(mode);
        telefoneEditText.setEnabled(mode);
        emailEditText.setEnabled(mode);

        categoriaEditText.setEnabled(mode);
        nRegistroEditText.setEnabled(mode);
        validadeEditText.setEnabled(mode);
        primeiraHabilitacaoEditText.setEnabled(mode);
        localEditText.setEnabled(mode);
        dataEmissaoEditText.setEnabled(mode);
    }

}
