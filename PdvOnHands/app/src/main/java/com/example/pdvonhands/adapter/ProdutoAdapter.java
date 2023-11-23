package com.example.pdvonhands.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.pdvonhands.model.ProdutoModel;

import java.util.ArrayList;
import java.util.List;

public class ProdutoAdapter extends ArrayAdapter<ProdutoModel> implements Filterable {

    private List<ProdutoModel> listaProdutosCompleta;
    private List<ProdutoModel> listaProdutosFiltrada;

    public ProdutoAdapter(Context context, List<ProdutoModel> produtos) {
        super(context, 0, produtos);
        this.listaProdutosCompleta = new ArrayList<>(produtos);
        this.listaProdutosFiltrada = new ArrayList<>(produtos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProdutoModel produto = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_dropdown_item_1line, parent, false);
        }

        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(produto.getNome());

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                List<ProdutoModel> suggestions = new ArrayList<>();

                if (constraint == null || constraint.length() == 0) {
                    suggestions.addAll(listaProdutosCompleta);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();

                    for (ProdutoModel produto : listaProdutosCompleta) {
                        if (produto.getNome().toLowerCase().contains(filterPattern)) {
                            suggestions.add(produto);
                        }
                    }
                }

                results.values = suggestions;
                results.count = suggestions.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List) results.values);
                notifyDataSetChanged();
            }

            @Override
            public CharSequence convertResultToString(Object resultValue) {
                return ((ProdutoModel) resultValue).getNome();
            }
        };
    }
}
