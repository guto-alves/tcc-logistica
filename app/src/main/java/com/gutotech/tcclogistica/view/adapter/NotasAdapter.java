package com.gutotech.tcclogistica.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Nota;

import java.util.List;

public class NotasAdapter extends RecyclerView.Adapter<NotasAdapter.MyViewHolder> {
    private Context context;
    private List<Nota> notasList;

    public NotasAdapter(Context context, List<Nota> notasList) {
        this.context = context;
        this.notasList = notasList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.adapter_nota, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Nota nota = notasList.get(position);

        holder.numeroNotaTextView.setText(String.valueOf(nota.getNumero()));
        holder.dataEmissaoTextView.setText(nota.getDataEmissao());
    }

    @Override
    public int getItemCount() {
        return notasList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView numeroNotaTextView;
        private TextView dataEmissaoTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            numeroNotaTextView = itemView.findViewById(R.id.numeroNotaTextView);
            dataEmissaoTextView = itemView.findViewById(R.id.dataEmissaoTextView);
        }
    }
}
