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
import com.gutotech.tcclogistica.model.Coleta;

import java.util.List;

public class ColetasAdapter extends RecyclerView.Adapter<ColetasAdapter.MyViewHolder> {
    private Context context;
    private List<Coleta> coletasList;

    public ColetasAdapter(Context context, List<Coleta> coletasList) {
        this.context = context;
        this.coletasList = coletasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_coleta, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Coleta coleta = coletasList.get(position);

        holder.numero.setText(coleta.getNumero());
        holder.emissao.setText(coleta.getDataEmissao());
        holder.coletar.setText(coleta.getColetarEm());
        holder.remetente.setText(coleta.getNomeRemetente());
        holder.enderecoRemetente.setText(coleta.getEnderecoRemetente());
        holder.destinatario.setText(coleta.getNomeDestinatario());
        holder.enderecoDestinatario.setText(coleta.getEnderecoDestinatario());
        holder.motorista.setText(coleta.getMotorista().getNome());
        holder.status.setText(coleta.getStatus().toString());

        if (coleta.getStatus() == Coleta.Status.REALIZADA)
            holder.status.setTextColor(Color.GREEN);
        else
            holder.status.setTextColor(Color.RED);
    }

    @Override
    public int getItemCount() {
        return coletasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numero;
        private TextView emissao;
        private TextView coletar;
        private TextView remetente;
        private TextView enderecoRemetente;
        private TextView destinatario;
        private TextView enderecoDestinatario;
        private TextView motorista;
        private TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numero = itemView.findViewById(R.id.numeroTextView);
            emissao = itemView.findViewById(R.id.emissaoTextView);
            coletar = itemView.findViewById(R.id.coletarAteTextView);
            remetente = itemView.findViewById(R.id.remetenteTextView);
            enderecoRemetente = itemView.findViewById(R.id.enderecoRemetenteTextView);
            destinatario = itemView.findViewById(R.id.destinatarioTextView);
            enderecoDestinatario = itemView.findViewById(R.id.enderecoDestinatarioTextView);
            motorista = itemView.findViewById(R.id.motoristaTextView);
            status = itemView.findViewById(R.id.statusTextView);
        }
    }
}
