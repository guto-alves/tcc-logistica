package com.gutotech.tcclogistica.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;

import java.util.List;

public class FuncionarioCadastradoAdapter extends RecyclerView.Adapter<FuncionarioCadastradoAdapter.MyViewHolder> {
    private List<Funcionario> funcionarios;

    public FuncionarioCadastradoAdapter(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View funcionarioLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_funcionario_cadastrado, parent, false);

        return new MyViewHolder(funcionarioLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Funcionario funcionario = funcionarios.get(position);

        holder.nome.setText(funcionario.getNome());
        holder.rg.setText(funcionario.getRg());
        holder.cpf.setText(funcionario.getCpf());
        holder.endereco.setText(funcionario.getEndereco());
        holder.celular.setText(funcionario.getCelular());
        holder.email.setText(funcionario.getEmail());
        holder.cargo.setText(funcionario.getCargo());

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView nome;
        private TextView rg;
        private TextView cpf;
        private TextView endereco;
        private TextView celular;
        private TextView email;
        private TextView cargo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.nomeTextView);
            rg = itemView.findViewById(R.id.rgTextView);
            cpf = itemView.findViewById(R.id.cpfTextView);
            endereco = itemView.findViewById(R.id.enderecoTextView);
            celular = itemView.findViewById(R.id.celularTextView);
            email = itemView.findViewById(R.id.emailTextView);
            cargo = itemView.findViewById(R.id.cargoTextView);

        }
    }
}
