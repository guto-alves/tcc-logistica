package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Coleta;
import com.gutotech.tcclogistica.model.FuncionarioOn;
import com.gutotech.tcclogistica.model.Status;

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

        if (coleta.getMotorista().getNome().equals(FuncionarioOn.funcionario.getNome()))
            holder.motorista.setVisibility(View.GONE);
        else
            holder.motorista.setVisibility(View.VISIBLE);

        holder.motorista.setText(coleta.getMotorista().getNome());
        holder.numero.setText(coleta.getNumero());
        holder.remetente.setText(coleta.getNomeRemetente());
        holder.enderecoRemetente.setText(coleta.getEnderecoRemetente());
        holder.destinatario.setText(coleta.getNomeDestinatario());
        holder.enderecoDestinatario.setText(coleta.getEnderecoDestinatario());
        holder.data.setText(coleta.getData());
        holder.hora.setText(coleta.getHora());

        holder.status.setText(coleta.getStatus() == Status.NAO_REALIZADA ? "NÃO REALIZADA" : coleta.getStatus().toString());

        if (coleta.getStatus() == Status.PENDENTE)
            holder.status.setTextColor(Color.YELLOW);
        else if (coleta.getStatus() == Status.REALIZADA)
            holder.status.setTextColor(Color.GREEN);
        else
            holder.status.setTextColor(Color.RED);

        if (coleta.getStatus() == Status.PENDENTE)
            holder.resultadoViagemLinear.setVisibility(View.GONE);
        else {
            holder.dataEntregueTextView.setText("Data: " + coleta.getResultadoViagem().getData());
            holder.chegadaTextView.setText("Chegada: " + coleta.getResultadoViagem().getHorarioChegada());
            holder.saidaTextView.setText("Saída: " + coleta.getResultadoViagem().getHorarioSaida());

            if (coleta.getStatus() == Status.REALIZADA)
                holder.motivoTextView.setVisibility(View.GONE);
            else {
                holder.motivoTextView.setText("Motivo: " + coleta.getResultadoViagem().getAconteceu());
                holder.motivoTextView.setVisibility(View.VISIBLE);
            }

            holder.resultadoViagemLinear.setVisibility(View.VISIBLE);
        }
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
        private TextView status;

        private LinearLayout resultadoViagemLinear;
        private TextView dataEntregueTextView;
        private TextView chegadaTextView;
        private TextView saidaTextView;
        private TextView motivoTextView;


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
            status = itemView.findViewById(R.id.statusTextView);

            resultadoViagemLinear = itemView.findViewById(R.id.resultadoViagemLinear);
            dataEntregueTextView = itemView.findViewById(R.id.dataEntregueTextView);
            chegadaTextView = itemView.findViewById(R.id.chegadaTextView);
            saidaTextView = itemView.findViewById(R.id.saidaTextView);
            motivoTextView = itemView.findViewById(R.id.motivoTextView);
        }
    }
}
