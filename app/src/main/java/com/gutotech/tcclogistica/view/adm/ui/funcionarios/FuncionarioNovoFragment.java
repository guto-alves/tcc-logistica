package com.gutotech.tcclogistica.view.adm.ui.funcionarios;

import android.app.Dialog;
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
import android.widget.GridLayout;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.config.ConfigFirebase;
import com.gutotech.tcclogistica.model.Funcionario;
import com.gutotech.tcclogistica.model.GeradorLogin;
import com.gutotech.tcclogistica.model.Login;
import com.gutotech.tcclogistica.model.Veiculo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class FuncionarioNovoFragment extends Fragment {
    private EditText nomeEditText, rgEditText, cpfEditText, enderecoEditText, celularEditText, emailEditText;

    private EditText cnhEditText, categoriaEditText, veiculoEditText, anoEditText, placaEditText;

    private Funcionario funcionario = new Funcionario();

    private Dialog processingDialog;

    public FuncionarioNovoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_adm_funcionario_novo, container, false);

        nomeEditText = root.findViewById(R.id.nomeEditText);
        rgEditText = root.findViewById(R.id.rgEditText);
        cpfEditText = root.findViewById(R.id.cpfEditText);
        enderecoEditText = root.findViewById(R.id.enderecoEditText);
        celularEditText = root.findViewById(R.id.celularEditText);
        emailEditText = root.findViewById(R.id.emailEditText);
        final Spinner cargoSpinner = root.findViewById(R.id.cargoSpinner);

        veiculoEditText = root.findViewById(R.id.veiculoEditText);
        cnhEditText = root.findViewById(R.id.cnhEditText);
        categoriaEditText = root.findViewById(R.id.categoriaEditText);
        anoEditText = root.findViewById(R.id.anoEditText);
        placaEditText = root.findViewById(R.id.placaEditText);

        processingDialog = new Dialog(getActivity());
        processingDialog.setContentView(R.layout.dialog_carregando);
        processingDialog.setCancelable(false);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.cargos_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cargoSpinner.setAdapter(adapter);
        cargoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                funcionario.setCargo(cargoSpinner.getSelectedItem().toString());

                GridLayout motoristaGridLayout = root.findViewById(R.id.motoristaGridLayout);

                if (funcionario.getCargo().equals(Funcionario.MOTORISTA))
                    motoristaGridLayout.setVisibility(View.VISIBLE);
                else
                    motoristaGridLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button salvarButton = root.findViewById(R.id.salvarButton);
        salvarButton.setOnClickListener(new View.OnClickListener() {
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
                funcionario.setEmail(emailEditText.getText().toString());

                funcionario.setCnh(cnhEditText.getText().toString());
                funcionario.setVeiculo(new Veiculo(veiculoEditText.getText().toString(), categoriaEditText.getText().toString(), anoEditText.getText().toString(), placaEditText.getText().toString()));

                gerarLogin();
            }
        });

        Button limparButton = root.findViewById(R.id.limparButton);
        limparButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        return root;
    }

    private void limparCampos() {
        nomeEditText.setText("");
        rgEditText.setText("");
        cpfEditText.setText("");
        enderecoEditText.setText("");
        celularEditText.setText("");
        emailEditText.setText("");

        veiculoEditText.setText("");
        cnhEditText.setText("");
        categoriaEditText.setText("");
        anoEditText.setText("");
        placaEditText.setText("");
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
