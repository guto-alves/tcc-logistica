package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Entrega;
import com.gutotech.tcclogistica.model.Status;

import java.util.List;

public class EntregasAdapter extends RecyclerView.Adapter<EntregasAdapter.MyViewHolder> {
    private Context context;
    private List<Entrega> entregasList;

    public EntregasAdapter(Context context, List<Entrega> entregasList) {
        this.entregasList = entregasList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_entrega, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Entrega entrega = entregasList.get(position);

        holder.numeroNotaTextView.setText(String.valueOf(entrega.getNota().getNumero()));
        holder.nomeMotorista.setText(entrega.getMotorista().getNome());
        holder.destinatarioTextView.setText(entrega.getNota().getDestinatario().getNome());
        holder.enderecoTextView.setText(entrega.getNota().getDestinatario().getEndereco().getEndereco());
        holder.dataTextView.setText(entrega.getData());
        holder.horarioEntregarTextView.setText(entrega.getHora());
        holder.statusTextView.setText(entrega.getStatus() == Status.NAO_REALIZADA ? "NÃO REALIZADA" : entrega.getStatus().toString());

        if (entrega.getStatus() == Status.PENDENTE)
            holder.statusTextView.setTextColor(Color.YELLOW);
        else if (entrega.getStatus() == Status.REALIZADA)
            holder.statusTextView.setTextColor(Color.GREEN);
        else
            holder.statusTextView.setTextColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        return entregasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numeroNotaTextView;
        private TextView nomeMotorista;
        private TextView dataTextView;
        private TextView enderecoTextView;
        private TextView statusTextView;
        private TextView destinatarioTextView;
        private TextView horarioEntregarTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeroNotaTextView = itemView.findViewById(R.id.numeroNotaTextView);
            nomeMotorista = itemView.findViewById(R.id.nomeMotoristaTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            dataTextView = itemView.findViewById(R.id.dataEntregarTextView);
            enderecoTextView = itemView.findViewById(R.id.enderecoTextView);
            destinatarioTextView = itemView.findViewById(R.id.destinatarioTextView);
            horarioEntregarTextView = itemView.findViewById(R.id.horarioEntregarTextView);
        }
    }
}
