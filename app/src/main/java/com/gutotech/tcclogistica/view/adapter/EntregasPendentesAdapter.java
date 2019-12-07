package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Entrega;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class EntregasPendentesAdapter extends RecyclerView.Adapter<EntregasPendentesAdapter.MyViewHolder> {
    private Context context;
    private List<Entrega> entregasList;

    public EntregasPendentesAdapter(Context context, List<Entrega> entregasList) {
        this.entregasList = entregasList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_entrega_pendente, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Entrega entrega = entregasList.get(position);

        holder.destinatarioTextView.setText(entrega.getNota().getDestinatario().getNome());
        holder.enderecoTextView.setText(entrega.getNota().getDestinatario().getEndereco().getEndereco());
        holder.numeroNotaTextView.setText(String.valueOf(entrega.getNota().getNumero()));
        holder.dataTextView.setText(entrega.getData());

        holder.finalizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrega.setStatus(Entrega.Status.REALIZADA);
                entrega.salvar();
            }
        });
    }

    @Override
    public int getItemCount() {
        return entregasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numeroNotaTextView;
        private TextView destinatarioTextView;
        private TextView enderecoTextView;
        private TextView dataTextView;
        private Button finalizarButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeroNotaTextView = itemView.findViewById(R.id.numeroNotaTextView);
            destinatarioTextView = itemView.findViewById(R.id.destinatarioTextView);
            enderecoTextView = itemView.findViewById(R.id.enderecoTextView);
            dataTextView = itemView.findViewById(R.id.dataEntregaTextView);
            finalizarButton = itemView.findViewById(R.id.finalizarButton);
        }
    }
}
