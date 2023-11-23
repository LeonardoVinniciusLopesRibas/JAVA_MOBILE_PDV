package com.example.pdvonhands.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.PdvController;
import com.example.pdvonhands.model.ClienteModel;
import java.util.List;

public class SelecionarClienteActivity extends AppCompatActivity {

    private PdvController pdvController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_cliente);

        pdvController = new PdvController(this);

        LinearLayout llClientesSelecao = findViewById(R.id.llClientesSelecao);

        List<ClienteModel> listaClientes = pdvController.obterListaClientes();

        for (ClienteModel cliente : listaClientes) {
            Button btnCliente = new Button(this);
            btnCliente.setText(cliente.getNome() + " - " + cliente.getCpfcnpj());

            final ClienteModel finalCliente = cliente; // Make a final copy

            btnCliente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("clienteSelecionado", finalCliente);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            });

            llClientesSelecao.addView(btnCliente);
        }

    }

}
