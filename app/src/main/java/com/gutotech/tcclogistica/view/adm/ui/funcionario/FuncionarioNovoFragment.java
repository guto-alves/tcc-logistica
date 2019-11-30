package com.gutotech.tcclogistica.view.adm.ui.funcionario;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.CNH;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.Login;
import com.gutotech.tcclogistica.model.Veiculo;
import com.gutotech.tcclogistica.view.ProcessingDialog;

import es.dmoral.toasty.Toasty;

public class FuncionarioNovoFragment extends Fragment {
    private EditText nomeEditText, rgEditText, cpfEditText, dataNascimentoEditText,
            enderecoEditText, telefoneEditText, celularEditText, emailEditText;

    private LinearLayout motoristaLinearLayout;
    private EditText cnhEditText, categoriaEditText, nRegistroEditText, dataValidadeEditText, dataEmissaoEditText;

    private Funcionario funcionario = new Funcionario();

    private ProcessingDialog processingDialog;

    public FuncionarioNovoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_adm_funcionario_novo, container, false);

        nomeEditText = root.findViewById(R.id.nomeEditText);
        rgEditText = root.findViewById(R.id.rgEditText);
        cpfEditText = root.findViewById(R.id.cpfEditText);
        dataNascimentoEditText = root.findViewById(R.id.dataNascimentoEditText);
        enderecoEditText = root.findViewById(R.id.enderecoEditText);
        telefoneEditText = root.findViewById(R.id.telefoneEditText);
        celularEditText = root.findViewById(R.id.celularEditText);
        emailEditText = root.findViewById(R.id.emailEditText);
        final Spinner cargoSpinner = root.findViewById(R.id.cargoSpinner);

        motoristaLinearLayout = root.findViewById(R.id.motoristaLinearLayout);
        cnhEditText = root.findViewById(R.id.cnhEditText);
        categoriaEditText = root.findViewById(R.id.categoriaEditText);
        nRegistroEditText = root.findViewById(R.id.nRegistroEditText);
        dataValidadeEditText = root.findViewById(R.id.dataValidadeEditText);
        dataEmissaoEditText = root.findViewById(R.id.dataEmissaoEdiText);

        adicionarMascaras();

        processingDialog = new ProcessingDialog(getActivity());

        funcionario.setCargo(Funcionario.MOTORISTA);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cargos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargoSpinner.setAdapter(adapter);
        cargoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                funcionario.setCargo(cargoSpinner.getSelectedItem().toString());

                if (funcionario.getCargo().equals(Funcionario.MOTORISTA))
                    motoristaLinearLayout.setVisibility(View.VISIBLE);
                else
                    motoristaLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
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

        return root;
    }

    private final View.OnClickListener salvarButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (nomeEditText.getText().toString().isEmpty()) {
                nomeEditText.setError("Campo obrigatório");
                return;
            }

            processingDialog.show();

            funcionario.setNome(nomeEditText.getText().toString());
            funcionario.setRg(rgEditText.getText().toString());
            funcionario.setCpf(cpfEditText.getText().toString());
            funcionario.setEndereco(enderecoEditText.getText().toString());
            funcionario.setCelular(celularEditText.getText().toString());
            funcionario.setTelefone(telefoneEditText.getText().toString());
            funcionario.setEmail(emailEditText.getText().toString());
            funcionario.setDataNascimento(dataNascimentoEditText.getText().toString());

            CNH cnh = new CNH();
            cnh.setCnh(cnhEditText.getText().toString());
            cnh.setCategoria(categoriaEditText.getText().toString());
            cnh.setNumeroRegistro(nRegistroEditText.getText().toString());
            cnh.setDataEmissao(dataEmissaoEditText.getText().toString());
            cnh.setCategoria(categoriaEditText.getText().toString());
            funcionario.setCnh(cnh);

            funcionario.setVeiculo(new Veiculo());

            gerarLogin();
        }
    };

    private void limparCampos() {
        nomeEditText.setText("");
        rgEditText.setText("");
        cpfEditText.setText("");
        dataNascimentoEditText.setText("");
        enderecoEditText.setText("");
        celularEditText.setText("");
        telefoneEditText.setText("");
        emailEditText.setText("");

        cnhEditText.setText("");
        categoriaEditText.setText("");
        nRegistroEditText.setText("");
        dataValidadeEditText.setText("");
        dataEmissaoEditText.setText("");
    }

    private void adicionarMascaras() {
        rgEditText.addTextChangedListener(new MaskTextWatcher(rgEditText, new SimpleMaskFormatter("NN.NNN.NNN-N")));
        cpfEditText.addTextChangedListener(new MaskTextWatcher(cpfEditText, new SimpleMaskFormatter("NNN.NNN.NNN-NN")));
        celularEditText.addTextChangedListener(new MaskTextWatcher(celularEditText, new SimpleMaskFormatter("(NN) NNNNN-NNNN")));
        telefoneEditText.addTextChangedListener(new MaskTextWatcher(telefoneEditText, new SimpleMaskFormatter("(NN) NNNN-NNNN")));
        dataNascimentoEditText.addTextChangedListener(new MaskTextWatcher(dataNascimentoEditText, new SimpleMaskFormatter("NN/NN/NNNN")));

        cnhEditText.addTextChangedListener(new MaskTextWatcher(cnhEditText, new SimpleMaskFormatter("NNNNNNNN")));
        categoriaEditText.addTextChangedListener(new MaskTextWatcher(categoriaEditText, new SimpleMaskFormatter("UU")));
        dataValidadeEditText.addTextChangedListener(new MaskTextWatcher(dataValidadeEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
        dataEmissaoEditText.addTextChangedListener(new MaskTextWatcher(dataEmissaoEditText, new SimpleMaskFormatter("NN/NN/NNNN")));
    }

    private int totalDeFuncionarios;
    private ValueEventListener valueEventListener;

    private void gerarLogin() {
        final DatabaseReference totalFuncionariosReference = ConfigFirebase.getDatabase().child("total_funcionarios");

        valueEventListener = totalFuncionariosReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                totalFuncionariosReference.removeEventListener(valueEventListener);

                totalDeFuncionarios = Integer.parseInt(dataSnapshot.getValue().toString());

                totalFuncionariosReference.setValue(totalDeFuncionarios + 1);

                String user = funcionario.getNome().split(" ")[0] + totalDeFuncionarios;
                funcionario.setLogin(new Login(user, user));

                funcionario.salvar();

                limparCampos();
                processingDialog.dismiss();
                Toasty.success(getActivity(), "Funcionário cadastrado com sucesso", Toasty.LENGTH_SHORT, true).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
