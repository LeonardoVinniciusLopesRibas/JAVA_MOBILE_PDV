package com.example.pdvonhands.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.PdvController;

public class RetaguardaActivity extends AppCompatActivity {

    private ImageButton imgPdv;
    private ImageButton imgCliente;
    private ImageButton imgNfc;
    private ImageButton imgSair;
    private PdvController pontoDeVendaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retaguarda);

        imgPdv = findViewById(R.id.imgPdv);
        imgCliente = findViewById(R.id.imgCliente);
        imgNfc = findViewById(R.id.imgNfc);
        imgSair = findViewById(R.id.imgSair);
        pontoDeVendaController = new PdvController(this);

        imgPdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(PdvActivity.class);
            }
        });

        imgCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(ListaCadastroClienteActivity.class);
            }
        });
        imgNfc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(NotaFiscalActivity.class);
            }
        });
        imgSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void abrirActivity (Class<?> activity){
        Intent intent = new Intent(RetaguardaActivity.this, activity);
        startActivity(intent);
    }
}