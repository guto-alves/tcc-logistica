package com.gutotech.tcclogistica.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;


public class AdapterMotoristaCadastrado extends RecyclerView.Adapter<AdapterMotoristaCadastrado.MyViewHolder> {


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int i) {
       return null;
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        holder.motorista.setText("");
        holder.celular.setText("");
        holder.cnh.setText("");
        holder.veiculo.setText("");
        holder.categoria.setText("");
    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView motorista;
        TextView celular;
        TextView cnh;
        TextView veiculo;
        TextView categoria;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            motorista = itemView.findViewById(R.id.motoristaTextView);
            celular = itemView.findViewById(R.id.celularTextView);
            cnh = itemView.findViewById(R.id.cnhTextView);
            veiculo = itemView.findViewById(R.id.veiculoTextView);
            categoria = itemView.findViewById(R.id.categoriaTextView);

        }
    }

}
