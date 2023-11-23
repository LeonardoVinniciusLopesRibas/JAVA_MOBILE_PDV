package com.example.pdvonhands.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.PdvController;
import com.example.pdvonhands.dao.PdvDAO;
import com.example.pdvonhands.model.PdvModel;

import java.util.ArrayList;
import java.util.List;

public class NotaFiscalActivity extends AppCompatActivity {
    private TextView tvNotaFiscal;
    private ImageButton imgReturn;
    private ImageButton imgAdd;
    private PdvController pontoDeVendaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota_fiscal);
        tvNotaFiscal = findViewById(R.id.tvNotaFiscal);
        imgReturn = findViewById(R.id.imgReturn);
        imgAdd = findViewById(R.id.imgAdd);

        PdvDAO pdvDAO = PdvDAO.getInstancia(this);
        ArrayList<PdvModel> vendas = pdvDAO.getAll();
        pontoDeVendaController = new PdvController(this);
        atualizarListaNota();



        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(PdvActivity.class);
            }
        });

        imgReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(RetaguardaActivity.class);
            }
        });
    }

    private void atualizarListaNota() {
        List<PdvModel> listaNotaAtualizada = pontoDeVendaController.obterVenda();

        try {
            if (listaNotaAtualizada != null && !listaNotaAtualizada.isEmpty()) {
                StringBuilder stringBuilder = new StringBuilder();
                for (PdvModel pdv : listaNotaAtualizada) {
                    stringBuilder.append("Id: ").append(pdv.getId()).append("\n");
                    stringBuilder.append("Produto: ").append(pdv.getProdutos()).append("\n");
                    stringBuilder.append("Cliente: ").append(pdv.getCliente()).append("\n");
                }

                tvNotaFiscal.setText(stringBuilder.toString());
            } else {
                tvNotaFiscal.setText("Nenhuma nota fiscal encontrada.");
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.e("Erro", "Exceção: "+ e.getMessage());
        }
    }



    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(NotaFiscalActivity.this, activity);
        startActivity(intent);
    }


}