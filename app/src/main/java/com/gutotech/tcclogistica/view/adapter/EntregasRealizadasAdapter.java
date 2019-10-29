package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Entrega;

import java.util.List;

public class EntregasRealizadasAdapter extends RecyclerView.Adapter<EntregasRealizadasAdapter.MyViewHolder> {
    private Context context;
    private List<Entrega> entregasList;

    public EntregasRealizadasAdapter(Context context, List<Entrega> entregasList) {
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
        holder.statusTextView.setText(entrega.getStatus().toString());
    }

    @Override
    public int getItemCount() {
        return entregasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numeroNotaTextView;
        private TextView nomeMotorista;
        private TextView statusTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeroNotaTextView = itemView.findViewById(R.id.numeroNotaTextView);
            nomeMotorista = itemView.findViewById(R.id.nomeMotoristaTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
        }
    }
}
