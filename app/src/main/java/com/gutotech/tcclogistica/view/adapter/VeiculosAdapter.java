package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Veiculo;

import java.util.List;

public class VeiculosAdapter extends RecyclerView.Adapter<VeiculosAdapter.MyViewHolder> {
    private Context context;
    private List<Veiculo> veiculos;

    public VeiculosAdapter(Context context, List<Veiculo> veiculos) {
        this.context = context;
        this.veiculos = veiculos;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_veiculo_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Veiculo veiculo = veiculos.get(position);

        holder.modeloEMarcaTextView.setText(String.format("%s - %s", veiculo.getModelo(), veiculo.getMarca()));
        holder.placaTextView.setText(veiculo.getPlaca());
        holder.corTextView.setText(veiculo.getCor());
        holder.anoTextView.setText(veiculo.getAno());
    }

    @Override
    public int getItemCount() {
        return veiculos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView modeloEMarcaTextView;
        private TextView placaTextView;
        private TextView corTextView;
        private TextView anoTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            modeloEMarcaTextView = itemView.findViewById(R.id.modeloEMarcaTextView);
            placaTextView = itemView.findViewById(R.id.placaTextView);
            corTextView = itemView.findViewById(R.id.corTextView);
            anoTextView = itemView.findViewById(R.id.anoTextView);
        }
    }
}
