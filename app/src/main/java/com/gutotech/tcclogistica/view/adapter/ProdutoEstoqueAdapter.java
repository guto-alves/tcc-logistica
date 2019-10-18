package com.gutotech.tcclogistica.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gutotech.tcclogistica.R;
import com.gutotech.tcclogistica.model.Produto;

import java.util.List;

public class ProdutoEstoqueAdapter extends RecyclerView.Adapter<ProdutoEstoqueAdapter.MyViewHolder> {
    private List<Produto> produtos;

    public ProdutoEstoqueAdapter(List<Produto> produtos) {
        this.produtos = produtos;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View produtoLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_produto_estoque, parent, false);

        return new MyViewHolder(produtoLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Produto produto = produtos.get(position);

        holder.codigo.setText("");
        holder.nome.setText("");
        holder.peso.setText("");
        holder.quantidade.setText("");
        holder.precoUnitario.setText("");
        holder.valorTotal.setText("");
        holder.descricao.setText("");

    }

    @Override
    public int getItemCount() {
        return 2;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView codigo;
        private TextView nome;
        private TextView peso;
        private TextView quantidade;
        private TextView precoUnitario;
        private TextView valorTotal;
        private TextView descricao;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            codigo = itemView.findViewById(R.id.codigoTextView);
            nome = itemView.findViewById(R.id.nomeTextView);
            peso = itemView.findViewById(R.id.pesoTextView);
            quantidade = itemView.findViewById(R.id.quantidadeTextView);
            precoUnitario = itemView.findViewById(R.id.precoUnitarioTextView);
            valorTotal = itemView.findViewById(R.id.valorTotalTextView);
            descricao = itemView.findViewById(R.id.descricaoTextView);

        }
    }
}
