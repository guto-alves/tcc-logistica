package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.model.Veiculo;
import com.gutotech.tcclogistica.view.adapter.LoginFuncionarioExpandableAdapter;

import es.dmoral.toasty.Toasty;

public class FuncionarioDialog {
    private Context context;
    private Funcionario funcionario;

    private boolean confirmaSenha = true;

    private Dialog fDialog;

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

    private EditText veiculoEditText;
    private EditText cnhEditText;
    private EditText categoriaEditText;
    private EditText anoEditText;
    private EditText placaEditText;

    public FuncionarioDialog(Context context, Funcionario funcionario) {
        this.context = context;
        this.funcionario = funcionario;

        fDialog = new Dialog(context);
        fDialog.setContentView(R.layout.dialog_funcionario);

        perfilImageView = fDialog.findViewById(R.id.profileImageView);
        nomeTextView = fDialog.findViewById(R.id.nomeTextView);
        cargoTextView = fDialog.findViewById(R.id.cargoTextView);
        rgEditText = fDialog.findViewById(R.id.rgEditText);
        cpfEditText = fDialog.findViewById(R.id.cpfEditText);
        dataNascimentoEditText = fDialog.findViewById(R.id.dataNascimentoEditText);
        enderecoEditText = fDialog.findViewById(R.id.enderecoEditText);
        celularEditText = fDialog.findViewById(R.id.celularEditText);
        telefoneEditText = fDialog.findViewById(R.id.telefoneEditText);
        emailEditText = fDialog.findViewById(R.id.emailEditText);
        ultimoLoginTextView = fDialog.findViewById(R.id.ultimoLoginTextView);
        loginExpandableListView = fDialog.findViewById(R.id.loginFuncionarioExpandable);
        veiculoEditText = fDialog.findViewById(R.id.veiculoEditText);
        cnhEditText = fDialog.findViewById(R.id.cnhEditText);
        categoriaEditText = fDialog.findViewById(R.id.categoriaEditText);
        anoEditText = fDialog.findViewById(R.id.anoEditText);
        placaEditText = fDialog.findViewById(R.id.placaEditText);

        addMasks();
    }

    public void show() {
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
            cnhEditText.setText(funcionario.getCnh());
            categoriaEditText.setText(funcionario.getVeiculo().getCategoria());
            veiculoEditText.setText(funcionario.getVeiculo().getNome());
            placaEditText.setText(funcionario.getVeiculo().getPlaca());
            anoEditText.setText(funcionario.getVeiculo().getAno());

            LinearLayout motoristaTextViews = fDialog.findViewById(R.id.motoristaLinearLayout);
            motoristaTextViews.setVisibility(View.VISIBLE);
        }

        ImageButton celularCallImageButton = fDialog.findViewById(R.id.callImageButton);
        celularCallImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + funcionario.getCelular())));
            }
        });

        ImageButton telefoneCallImageButton = fDialog.findViewById(R.id.callTelmageButton);
        telefoneCallImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + funcionario.getTelefone())));
            }
        });

        ImageButton sendEmailmageButton = fDialog.findViewById(R.id.sendEmailmageButton);
        sendEmailmageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{funcionario.getEmail()});
                context.startActivity(intent);
            }
        });

        LoginFuncionarioExpandableAdapter loginAdapter = new LoginFuncionarioExpandableAdapter(context, funcionario);
        loginExpandableListView.setAdapter(loginAdapter);
        loginExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (confirmaSenha) {
                    loginExpandableListView.collapseGroup(0);

                    final Dialog confirmDialog = new Dialog(context);
                    confirmDialog.setContentView(R.layout.dialog_confirm_password);
                    confirmDialog.setCancelable(false);

                    final EditText passwordEditText = confirmDialog.findViewById(R.id.passwordEditText);

                    Button okButton = confirmDialog.findViewById(R.id.okButton);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String password = passwordEditText.getText().toString();
                            if (password.equals(FuncionarioOn.funcionario.getLogin().getPassword())) {
                                confirmaSenha = false;
                                confirmDialog.dismiss();
                                loginExpandableListView.expandGroup(0);
                            } else {
                                confirmaSenha = true;
                                confirmDialog.dismiss();
                                Toasty.error(context, "Acesso negado, senha inválida!", Toasty.LENGTH_SHORT, true).show();
                            }
                        }
                    });

                    Button cancelButton = confirmDialog.findViewById(R.id.cancelButton);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmDialog.dismiss();
                        }
                    });

                    confirmDialog.show();
                }
            }
        });
        loginExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                confirmaSenha = true;
            }
        });

        Button fecharButton = fDialog.findViewById(R.id.fecharButton);
        fecharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fDialog.dismiss();
            }
        });

        Button excluirButton = fDialog.findViewById(R.id.excluirButton);
        excluirButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!FuncionarioOn.funcionario.getLogin().getUser().equals(funcionario.getLogin().getUser())) {
                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                    alert.setTitle("EXCLUIR FUNCIONÁRIO");
                    alert.setMessage("Tem certeza que deseja excluir o " + funcionario.getCargo() + " " + funcionario.getNome().toUpperCase() + "?");
                    alert.create();
                    alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            funcionario.excluir();
                            fDialog.dismiss();
                        }
                    });
                    alert.setNegativeButton("Não", null);
                    alert.show();
                }
            }
        });

        final ImageButton doneEditImageButton = fDialog.findViewById(R.id.doneEditImageButton);
        final ImageButton modeEditImageButton = fDialog.findViewById(R.id.modoEditImageButton);

        modeEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeMode(true);

                modeEditImageButton.setVisibility(View.GONE);
                doneEditImageButton.setVisibility(View.VISIBLE);
            }
        });

        doneEditImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcionario.setRg(rgEditText.getText().toString());
                funcionario.setCpf(cpfEditText.getText().toString());
                funcionario.setEndereco(enderecoEditText.getText().toString());
                funcionario.setCelular(celularEditText.getText().toString());
                funcionario.setTelefone(telefoneEditText.getText().toString());
                funcionario.setEmail(emailEditText.getText().toString());
                funcionario.setDataNascimento(dataNascimentoEditText.getText().toString());

                funcionario.setCnh(cnhEditText.getText().toString());
                funcionario.setVeiculo(new Veiculo(veiculoEditText.getText().toString(), categoriaEditText.getText().toString(), anoEditText.getText().toString(), placaEditText.getText().toString()));

                funcionario.salvar();

                Toasty.success(context, "Funcionário atualizado!", Toasty.LENGTH_SHORT, true).show();

                changeMode(false);

                doneEditImageButton.setVisibility(View.GONE);
                modeEditImageButton.setVisibility(View.VISIBLE);
            }
        });

        changeMode(false);

        fDialog.show();
    }

    private void addMasks() {
        rgEditText.addTextChangedListener(new MaskTextWatcher(rgEditText, new SimpleMaskFormatter("NN.NNN.NNN-N")));
        cpfEditText.addTextChangedListener(new MaskTextWatcher(cpfEditText, new SimpleMaskFormatter("NNN.NNN.NNN-NN")));
        celularEditText.addTextChangedListener(new MaskTextWatcher(celularEditText, new SimpleMaskFormatter("(NN) NNNNN-NNNN")));
        telefoneEditText.addTextChangedListener(new MaskTextWatcher(telefoneEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));
        dataNascimentoEditText.addTextChangedListener(new MaskTextWatcher(dataNascimentoEditText, new SimpleMaskFormatter("NN/NN/NNNN")));

        anoEditText.addTextChangedListener(new MaskTextWatcher(anoEditText, new SimpleMaskFormatter("NNNN")));
        cnhEditText.addTextChangedListener(new MaskTextWatcher(cnhEditText, new SimpleMaskFormatter("NNNNNNNN")));
        categoriaEditText.addTextChangedListener(new MaskTextWatcher(categoriaEditText, new SimpleMaskFormatter("UU")));
        placaEditText.addTextChangedListener(new MaskTextWatcher(placaEditText, new SimpleMaskFormatter("LLL-NNNN")));
    }

    private void changeMode(boolean mode) {
        rgEditText.setEnabled(mode);
        cpfEditText.setEnabled(mode);
        dataNascimentoEditText.setEnabled(mode);
        enderecoEditText.setEnabled(mode);
        celularEditText.setEnabled(mode);
        telefoneEditText.setEnabled(mode);
        emailEditText.setEnabled(mode);
        veiculoEditText.setEnabled(mode);
        cnhEditText.setEnabled(mode);
        categoriaEditText.setEnabled(mode);
        anoEditText.setEnabled(mode);
        placaEditText.setEnabled(mode);
    }

}
