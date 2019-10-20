package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;

import java.util.List;

public class FuncionariosAdapter extends RecyclerView.Adapter<FuncionariosAdapter.MyViewHolder> {
    private List<Funcionario> funcionariosList;
    private Context context;

    public FuncionariosAdapter(Context context, List<Funcionario> funcionariosList) {
        this.context = context;
        this.funcionariosList = funcionariosList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_funcionario_cadastrado, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Funcionario funcionario = funcionariosList.get(position);

        holder.nomeTextView.setText(funcionario.getNome());
        holder.cargoTextView.setText(funcionario.getCargo());
        holder.idTextView.setText(String.valueOf(position + 1));

        if (funcionario.isOnline())
            holder.onlineImageView.setImageResource(R.drawable.funcionario_on);
        else
            holder.onlineImageView.setImageResource(R.drawable.funcionario_off);
    }

    @Override
    public int getItemCount() {
        return funcionariosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nomeTextView;
        private TextView cargoTextView;
        private TextView idTextView;
        private ImageView onlineImageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeTextView = itemView.findViewById(R.id.nomeTextView);
            cargoTextView = itemView.findViewById(R.id.cpfTextView);
            idTextView = itemView.findViewById(R.id.idTextView);
            onlineImageView = itemView.findViewById(R.id.onlineImageView);
        }
    }
}
