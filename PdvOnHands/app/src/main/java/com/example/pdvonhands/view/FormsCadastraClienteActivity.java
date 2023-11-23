package com.example.pdvonhands.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.ClienteController;

public class FormsCadastraClienteActivity extends AppCompatActivity {

    private EditText edNomeCliente;
    private EditText edCpfCnpj;
    private EditText edEmail;
    private Button btCadastrarCliente;
    private ClienteController clienteController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forms_cadastra_cliente);

        edNomeCliente = findViewById(R.id.edNomeCliente);
        edCpfCnpj = findViewById(R.id.edCpfCnpj);
        edEmail = findViewById(R.id.edEmail);
        btCadastrarCliente = findViewById(R.id.btCadastrarCliente);


        clienteController = new ClienteController(this);
        btCadastrarCliente.setOnClickListener(v -> cadastrarCliente());


    }

    private void cadastrarCliente() {

        String nome = edNomeCliente.getText().toString();
        String cpfCnpj = edCpfCnpj.getText().toString();
        String email = edEmail.getText().toString();

         if(edNomeCliente.getText().toString().isEmpty()){
            edNomeCliente.setError("O nome do cliente é obrigatório!");
            return;
        }

        if(edCpfCnpj.getText().toString().isEmpty()){
            edCpfCnpj.setError("O CPF do cliente é obrigatório!");
            return;
        }

        if (edCpfCnpj.length() < 11 || edCpfCnpj.length() > 11){
            edCpfCnpj.setError("Excedido tamanho de caracteres, verifique por favor!");
        }

        if (edCpfCnpj.length() > 14 || edCpfCnpj.length() < 14){
            edCpfCnpj.setError("Excedido tamanho de caracteres, verifique por favor!");
        }

        if (edEmail.getText().toString().isEmpty()){
            edEmail.setError("O email do cliente é obrigatório!");
            return;
        }


        if (clienteController != null) {
            boolean sucesso = clienteController.insertCliente(nome, cpfCnpj, email);
            showToast("Cadastrado com sucesso");
        } else {
            showToast("Erro: ClienteController não inicializado corretamente.");
        }

    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}