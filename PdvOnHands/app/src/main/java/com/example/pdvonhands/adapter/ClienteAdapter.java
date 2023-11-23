package com.example.pdvonhands.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.example.pdvonhands.model.ClienteModel;

import java.util.ArrayList;
import java.util.List;

public class ClienteAdapter extends ArrayAdapter<ClienteModel> implements Filterable {

    private List<ClienteModel> clienteListFull;

    public ClienteAdapter(Context context, List<ClienteModel> clienteList){
        super(context, android.R.layout.simple_dropdown_item_1line, clienteList);
        clienteListFull = new ArrayList<>(clienteList);
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return super.getFilter();
    }

    private Filter clienteFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            List<ClienteModel> suggestions = new ArrayList<>();
            if (constraint == null || constraint.length() == 0){
                suggestions.addAll(clienteListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for(ClienteModel cliente : clienteListFull){
                    if (cliente.getNome().toLowerCase().contains(filterPattern) || cliente.getCpfcnpj().contains(filterPattern)){
                        suggestions.add(cliente);
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
            return ((ClienteModel) resultValue).getNome() + " - " + ((ClienteModel) resultValue).getCpfcnpj();
        }
    };
}
