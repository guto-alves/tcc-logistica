package com.gutotech.tcclogistica.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Entrega;

import java.util.List;

public class EntregaRealizadaAdapter extends RecyclerView.Adapter<EntregaRealizadaAdapter.MyViewHolder> {
    private List<Entrega> entregas;

    public EntregaRealizadaAdapter(List<Entrega> entregas) {
        this.entregas = entregas;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Entrega entrega = entregas.get(position);

        holder.notaFiscal.setText(entrega.getId());

        holder.motoristaTextView.setText(entrega.getMotorista().getNome());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView notaFiscal;
        private TextView motoristaTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            notaFiscal = itemView.findViewById(R.id.notaFiscalTextView);
            motoristaTextView = itemView.findViewById(R.id.motoristaTextView);

        }
    }
}
