package com.gutotech.tcclogistica.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;


public class AdapterEntregasLista extends RecyclerView.Adapter<AdapterEntregasLista.MyViewHolder> {


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_entrega_realizada_roteirista, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.notaFiscal.setText("");
        holder.cliente.setText("");
        holder.motorista.setText("");
        holder.endereco.setText("");
        holder.saida.setText("");
        holder.chegada.setText("");
        holder.status.setText("");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView notaFiscal;
        TextView cliente;
        TextView motorista;
        TextView endereco;
        TextView saida;
        TextView chegada;
        TextView status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notaFiscal = itemView.findViewById(R.id.notaFiscalTextView);
            cliente = itemView.findViewById(R.id.clienteTextView);
            motorista = itemView.findViewById(R.id.motoristaTextView);
            endereco = itemView.findViewById(R.id.enderecoTextView);
            saida = itemView.findViewById(R.id.saidaTextView);
            chegada = itemView.findViewById(R.id.chegadaTextView);
            status = itemView.findViewById(R.id.statusTextView);

        }
    }

}
