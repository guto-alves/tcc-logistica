package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
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
        View coletaLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_coleta_realizada, parent, false);
        return new MyViewHolder(coletaLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Coleta coleta = coletasList.get(position);

        holder.emissao.setText(coleta.getDataEmissao());
        holder.coletar.setText(coleta.getColetarEm());
        holder.remetente.setText(coleta.getNomeRemetente());
        holder.destinatario.setText(coleta.getNomeDestinatario());
        holder.motorista.setText(coleta.getMotorista().getNome());
    }

    @Override
    public int getItemCount() {
        return coletasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView emissao;
        private TextView coletar;
        private TextView remetente;
        private TextView destinatario;
        private TextView motorista;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            emissao = itemView.findViewById(R.id.emissaoTextView);
            coletar = itemView.findViewById(R.id.coletarTextView);
            remetente = itemView.findViewById(R.id.remetenteTextView);
            destinatario = itemView.findViewById(R.id.destinatarioTextView);
            motorista = itemView.findViewById(R.id.motoristaTextView);
        }
    }
}
