package com.gutotech.tcclogistica.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Coleta;

import java.util.List;

public class ColetaRealizadaAdapter extends RecyclerView.Adapter<ColetaRealizadaAdapter.MyViewHolder> {
    private List<Coleta> coletas;

    public ColetaRealizadaAdapter(List<Coleta> coletas) {
        this.coletas = coletas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View coletaLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_coleta_realizada, parent, false);
        return new MyViewHolder(coletaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Coleta coleta = coletas.get(position);

        holder.emissao.setText(coleta.getDataEmissao());
        holder.coletar.setText("");
        holder.remetente.setText("");
        holder.produto.setText("");
        holder.destinatario.setText("");
        holder.motorista.setText("");
        holder.dataColetada.setText("");
        holder.horaColetada.setText("");

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView emissao;
        private TextView coletar;
        private TextView remetente;
        private TextView produto;
        private TextView destinatario;
        private TextView motorista;
        private TextView dataColetada;
        private TextView horaColetada;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            emissao = itemView.findViewById(R.id.emissaoTextView);
            coletar = itemView.findViewById(R.id.coletarTextView);
            remetente = itemView.findViewById(R.id.remetenteTextView);
            produto = itemView.findViewById(R.id.produtoTextView);
            destinatario = itemView.findViewById(R.id.destinatarioTextView);
            motorista = itemView.findViewById(R.id.motoristaTextView);
            dataColetada = itemView.findViewById(R.id.dataColetadaTextView);
            horaColetada = itemView.findViewById(R.id.horaColetadaTextView);

        }
    }
}
