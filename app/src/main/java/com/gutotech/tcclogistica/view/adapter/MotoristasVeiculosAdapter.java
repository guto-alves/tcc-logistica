package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Funcionario;

import java.util.List;

public class MotoristasVeiculosAdapter extends RecyclerView.Adapter<MotoristasVeiculosAdapter.MyViewHolder> {
    private Context context;
    private List<Funcionario> motoristasVeiculosList;

    public MotoristasVeiculosAdapter(Context context, List<Funcionario> motoristasVeiculosList) {
        this.context = context;
        this.motoristasVeiculosList = motoristasVeiculosList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_motorista_veiculo, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Funcionario motorista = motoristasVeiculosList.get(position);

        holder.motoristaTextView.setText(motorista.getNome().split(" ")[0]);
        holder.veiculoTextView.setText(motorista.getVeiculo().getModelo() + ", " + motorista.getVeiculo().getPlaca());
    }

    @Override
    public int getItemCount() {
        return motoristasVeiculosList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView motoristaTextView;
        private TextView veiculoTextView;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            motoristaTextView = itemView.findViewById(R.id.motoristaTextView);
            veiculoTextView = itemView.findViewById(R.id.veiculoTextView);
        }
    }
}
