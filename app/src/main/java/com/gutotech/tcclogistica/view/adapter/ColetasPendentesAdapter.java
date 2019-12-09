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
import com.gutotech.tcclogistica.model.Coleta;

import java.util.List;

public class ColetasPendentesAdapter extends RecyclerView.Adapter<ColetasPendentesAdapter.MyViewHolder> {
    private Context context;
    private List<Coleta> coletasList;

    public ColetasPendentesAdapter(Context context, List<Coleta> coletasList) {
        this.context = context;
        this.coletasList = coletasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_coleta_pendente, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Coleta coleta = coletasList.get(position);

        holder.motorista.setText(coleta.getMotorista().getNome());
        holder.numero.setText(coleta.getNumero());
        holder.remetente.setText(coleta.getNomeRemetente());
        holder.enderecoRemetente.setText(coleta.getEnderecoRemetente());
        holder.destinatario.setText(coleta.getNomeDestinatario());
        holder.enderecoDestinatario.setText(coleta.getEnderecoDestinatario());
        holder.data.setText(coleta.getData());
        holder.hora.setText(coleta.getHora());

        holder.finalizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return coletasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numero;
        private TextView data;
        private TextView hora;
        private TextView remetente;
        private TextView enderecoRemetente;
        private TextView destinatario;
        private TextView enderecoDestinatario;
        private TextView motorista;
        private Button finalizarButton;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numero = itemView.findViewById(R.id.numeroTextView);
            data = itemView.findViewById(R.id.dataTextView);
            hora = itemView.findViewById(R.id.horaTextView);
            remetente = itemView.findViewById(R.id.remetenteTextView);
            enderecoRemetente = itemView.findViewById(R.id.enderecoRemetenteTextView);
            destinatario = itemView.findViewById(R.id.destinatarioTextView);
            enderecoDestinatario = itemView.findViewById(R.id.enderecoDestinatarioTextView);
            motorista = itemView.findViewById(R.id.motoristaTextView);
            finalizarButton = itemView.findViewById(R.id.finalizarButton);
        }
    }
}
