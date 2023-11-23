package com.example.pdvonhands.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.MainController;
import com.example.pdvonhands.model.MainModel;

public class MainActivity extends AppCompatActivity {

    private EditText edEmail;
    private EditText edSenha;
    private Button btLogar;
    private Spinner spEmpresa;

    private TextView tvEsqueceuSuaSenha;
    MainController mainController = new MainController(new MainModel());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edEmail = findViewById(R.id.edEmail);
        edSenha = findViewById(R.id.edSenha);
        spEmpresa = findViewById(R.id.spEmpresa);
        btLogar = findViewById(R.id.btLogar);
        tvEsqueceuSuaSenha = findViewById(R.id.tvEsqueceuSuaSenha);
        mostrarEmpresas();



        btLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(edEmail.getText().toString().isEmpty()){
                    edEmail.setError("O e-mail é obrigatório");
                    return;
                }

                if(edSenha.getText().toString().isEmpty()){
                    edSenha.setError("A senha é obrigatória");
                    return;
                }
                String emailInformado = edEmail.getText().toString();
                String senhaInformada = edSenha.getText().toString();
                mainController.validarEmail(MainActivity.this, emailInformado, senhaInformada);

            }
        });
        tvEsqueceuSuaSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(RecuperaSenhaActivity.class);
            }
        });
    }

    public void mostrarEmpresas(){
        String[] empresas = {"Selecione sua empresa", "Mercado do vale", "Mercado silveira"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, empresas);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEmpresa.setAdapter(adapter);
    }

    private void abrirActivity (Class<?> activity){
        Intent intent = new Intent(MainActivity.this, activity);
        startActivity(intent);
    }

}
