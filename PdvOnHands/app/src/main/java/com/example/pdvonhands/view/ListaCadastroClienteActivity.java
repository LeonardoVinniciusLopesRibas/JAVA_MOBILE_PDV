package com.example.pdvonhands.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pdvonhands.R;
import com.example.pdvonhands.controller.ClienteController;
import com.example.pdvonhands.controller.PdvController;
import com.example.pdvonhands.controller.ProdutoController;
import com.example.pdvonhands.model.ClienteModel;
import com.example.pdvonhands.model.ProdutoModel;

import java.util.List;

public class ListaCadastroClienteActivity extends AppCompatActivity {
    private ImageButton imgRetornar;
    private ImageButton imgAdd;
    private ClienteController clienteController;
    private TextView tvClientes;
    private EditText edDeletarCliente;
    private Button btDeletarCliente;
    private PdvController pdvController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_cliente);
        edDeletarCliente = findViewById(R.id.edDeletarCliente);
        btDeletarCliente = findViewById(R.id.btDeletarCliente);
        imgRetornar = findViewById(R.id.imgReturn);
        imgAdd = findViewById(R.id.imgAdd);
        tvClientes = findViewById(R.id.tvClientes);
        pdvController = new PdvController(this);
        clienteController = new ClienteController(this);
        List<ClienteModel> listaClientes = clienteController.selectAllClients();
        mostrarCliente(clienteController.selectAllClients());
        imgRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(RetaguardaActivity.class);
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirActivity(FormsCadastraClienteActivity.class);
            }
        });
        btDeletarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletarCliente();
            }
        });
    }
    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(ListaCadastroClienteActivity.this, activity);
        startActivity(intent);
    }
    private void deletarCliente() {

        String cpfParaDeletar = edDeletarCliente.getText().toString().trim();

        if (!cpfParaDeletar.isEmpty()) {
            boolean clienteDeletado = pdvController.deletarClientePorCPF(cpfParaDeletar);

            if (clienteDeletado) {
                mostrarCliente(clienteController.selectAllClients());
                exibirToast("Cliente deletado com sucesso");
            } else {
                edDeletarCliente.setError("Cliente não encontrado pelo CPF");
                exibirToast("Cliente não encontrado");
            }
        } else {
            edDeletarCliente.setError("Informe o CPF do cliente");
        }
    }
    private void exibirToast(String mensagem) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
    }
    private void mostrarCliente(List<ClienteModel> listaClientes) {
        StringBuilder stringBuilder = new StringBuilder();

        for (ClienteModel cliente : listaClientes) {
            String formatarCliente = String.format("Nome: %s - Cpf: %s\n", cliente.getNome(), cliente.getCpfcnpj());
            stringBuilder.append(formatarCliente);
        }
        tvClientes.setText(stringBuilder.toString());
    }


}
