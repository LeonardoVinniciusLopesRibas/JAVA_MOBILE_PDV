package com.example.pdvonhands.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pdvonhands.R;
import com.example.pdvonhands.adapter.ClienteAdapter;
import com.example.pdvonhands.adapter.ProdutoAdapter;
import com.example.pdvonhands.controller.PdvController;
import com.example.pdvonhands.model.ClienteModel;
import com.example.pdvonhands.model.ProdutoModel;
import java.util.ArrayList;
import java.util.List;
public class PdvActivity extends AppCompatActivity {
    private Spinner spProdutos;
    private EditText edValorUnitario;
    private EditText edQtdProduto;
    private Button btAddProduto;
    private TextView tvListaProdutos;
    private PdvController pdvController;
    private List<ProdutoModel> carrinhoProdutos = new ArrayList<>();
    //----------------------------------------------------------------------------------------------
    List<ProdutoModel> listaProdutos;
    //----------------------------------------------------------------------------------------------
    private Spinner spAddCliente;
    private TextView tvClienteSelecionado;
    private Button btFinalizarNotaFiscal;
    private AutoCompleteTextView autoCompleteProdutos;
    private Button btSelecionarCliente;
    private LinearLayout llClientes;
    private int posicaoProdutoSelecionado = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ponto_de_venda);
        edValorUnitario = findViewById(R.id.edValorUnitario);
        edQtdProduto = findViewById(R.id.edQtdProdutos);
        btAddProduto = findViewById(R.id.btAddProduto);
        autoCompleteProdutos = findViewById(R.id.autoCompleteProdutos);
        //------------------------------------------------------------------------------------------
        tvListaProdutos = findViewById(R.id.tvListaProdutos);
        //------------------------------------------------------------------------------------------
        btSelecionarCliente = findViewById(R.id.btSelecionarCliente);
        tvClienteSelecionado = findViewById(R.id.tvClienteSelecionado);
        btFinalizarNotaFiscal = findViewById(R.id.btFinalizarNotaFiscal);
        pdvController = new PdvController(this);
        //----------------------------------------------------------------------------------------------
        listaProdutos = pdvController.obterListaProdutos();
        //----------------------------------------------------------------------------------------------
        List<ClienteModel> listaClientes = pdvController.obterListaClientes();
        autoCompleteProdutos = findViewById(R.id.autoCompleteProdutos);
        ProdutoAdapter produtoAdapter = new ProdutoAdapter(this, listaProdutos);
        autoCompleteProdutos.setAdapter(produtoAdapter);
        autoCompleteProdutos.setThreshold(1);


        autoCompleteProdutos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ProdutoModel produtoSelecionado = (ProdutoModel) parent.getItemAtPosition(position);
                posicaoProdutoSelecionado = position;

                // Preencher os campos com os dados do produto
                edValorUnitario.setText(String.valueOf(produtoSelecionado.getValorUnitario()));
                edQtdProduto.setText("1");
            }
        });

        btAddProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edQtdProduto.getText().toString().isEmpty()) {
                    edQtdProduto.setError("Verifique os campos quantidade está vazio!");
                } else if(edValorUnitario.getText().toString().isEmpty()) {
                    edValorUnitario.getText().toString().isEmpty();
                }else {
                    adicionarProdutoAoCarrinho();
                }
            }
        });



        btSelecionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a nova atividade de seleção de cliente com startActivityForResult
                Intent intent = new Intent(PdvActivity.this, SelecionarClienteActivity.class);
                startActivityForResult(intent, 1); // 1 é o código de requisição, pode ser qualquer número
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null) {
                ClienteModel clienteSelecionado = data.getParcelableExtra("clienteSelecionado");
                pdvController.selecionarCliente(clienteSelecionado);
                adicionarClienteAVenda();
            }
        }
    }



    private void abrirActivity(Class<?> activity) {
        Intent intent = new Intent(PdvActivity.this, activity);
        startActivity(intent);
    }


    private void adicionarProdutoAoCarrinho() {
        if (posicaoProdutoSelecionado != -1) {
            ProdutoModel produtoSelecionado = listaProdutos.get(posicaoProdutoSelecionado);
            int quantidade = Integer.parseInt(edQtdProduto.getText().toString());
            pdvController.adicionarProdutoAoCarrinho(produtoSelecionado, quantidade);
            atualizarListaProdutosNoTextView(pdvController.obterCarrinho());
        }
    }

    //----------------------------------------------------------------------------------------------
    private void atualizarListaProdutosNoTextView(List<ProdutoModel> carrinho) {
        StringBuilder stringBuilder = new StringBuilder();
        for (ProdutoModel produto : carrinho) {
            double vlTotal = (produto.getValorUnitario() * produto.getQuantidade());
            String formatarProduto = String.format("Nome: %s - Valor Unitário: R$ %.2f - Valor Total: R$ %.2f\n", produto.getNome(), produto.getValorUnitario(), vlTotal);
            stringBuilder.append(formatarProduto);
        }
        tvListaProdutos.setText(stringBuilder.toString());
    }
    //----------------------------------------------------------------------------------------------
    private void adicionarClienteAVenda() {
        ClienteModel clienteSelecionado = pdvController.obterClienteSelecionado();
        if (clienteSelecionado != null) {
            tvClienteSelecionado.setText("Cliente Selecionado: " + clienteSelecionado.getNome() + " Cnpj: " +clienteSelecionado.getCpfcnpj());
        } else {
            tvClienteSelecionado.setText("Nenhum cliente selecionado");
        }
    }
}