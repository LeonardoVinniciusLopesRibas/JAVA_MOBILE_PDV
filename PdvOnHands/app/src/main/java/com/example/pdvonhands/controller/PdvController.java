package com.example.pdvonhands.controller;
import android.content.Context;
import com.example.pdvonhands.dao.ClienteDAO;
import com.example.pdvonhands.dao.PdvDAO;
import com.example.pdvonhands.dao.ProdutoDAO;
import com.example.pdvonhands.dao.Produto_VendaDAO;
import com.example.pdvonhands.model.ClienteModel;
import com.example.pdvonhands.model.PdvModel;
import com.example.pdvonhands.model.ProdutoModel;
import com.example.pdvonhands.model.Produto_VendaModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class PdvController {
    private ProdutoDAO produtoDAO;
    private ClienteDAO clienteDAO;
    private List<ProdutoModel> carrinho;
    private ClienteModel clienteSelecionado;
    private PdvDAO pdvDAO;
    private Produto_VendaDAO produtoVendaDAO;
    public PdvController(Context context) {
        this.produtoDAO = ProdutoDAO.getInstancia(context);
        this.clienteDAO = ClienteDAO.getInstancia(context);
        this.pdvDAO = PdvDAO.getInstancia(context);
        this.produtoVendaDAO = Produto_VendaDAO.getInstancia(context);
        this.carrinho = new ArrayList<>();
    }
    public List<ProdutoModel> obterListaProdutos() {
        return produtoDAO.getAll();
    }
    public List<ClienteModel> obterListaClientes() {

        return clienteDAO.getAll();
    }
    public List<ProdutoModel> adicionarProdutoAoCarrinho(ProdutoModel produto, int quantidade) {
        boolean produtoExistente = false;
        for (ProdutoModel produtoNoCarrinho : carrinho) {
            if (produtoNoCarrinho.getId() == produto.getId()) {
                produtoNoCarrinho.setQuantidade(produtoNoCarrinho.getQuantidade() + quantidade);
                produtoExistente = true;
                break;
            }
        }
        if (!produtoExistente) {
            ProdutoModel produtoNoCarrinho = new ProdutoModel();
            produtoNoCarrinho.setId(produto.getId());
            produtoNoCarrinho.setNome(produto.getNome());
            produtoNoCarrinho.setValorUnitario(produto.getValorUnitario());
            produtoNoCarrinho.setQuantidade(quantidade);
            carrinho.add(produtoNoCarrinho);
        }
        return carrinho;
    }
    public List<ProdutoModel> obterCarrinho() {
        return carrinho;
    }
    public void selecionarCliente(ClienteModel cliente) {
        this.clienteSelecionado = cliente;
    }
    public ClienteModel obterClienteSelecionado() {
        return clienteSelecionado;
    }
    public boolean deletarClientePorCPF(String cpf) {
        ClienteModel clienteParaDeletar = clienteDAO.getClienteByCPF(cpf);
        if (clienteParaDeletar != null) {
            long resultado = clienteDAO.delete(clienteParaDeletar);
            return resultado != -1;
        }
        return false;
    }
    public List<PdvModel> obterVenda() {
        return pdvDAO.getAll();
    }
    public PdvModel obterVendaPorId(int id) {
        return pdvDAO.getById(id);
    }

    public long salvarVenda(PdvModel pdvModel, List<ProdutoModel> produtos) {
        // Save sale in the VENDA table
        long vendaId = pdvDAO.insert(pdvModel);

        // Associate products with the sale in the PRODUTO_VENDA table
        for (ProdutoModel produto : produtos) {
            Produto_VendaModel produtoVenda = new Produto_VendaModel();
            produtoVenda.setProduto(Collections.singletonList(produto));
            produtoVenda.setVenda(Collections.singletonList(pdvModel));
            produtoVenda.setQuantidade_total(produto.getQuantidade()); // Adjust this based on your product model
            produtoVenda.setValor_total(produto.getValorUnitario() * produto.getQuantidade()); // Adjust this based on your product model

            produtoVendaDAO.insert(produtoVenda);
        }

        return vendaId;
    }

    public boolean finalizarVenda() {
        if (carrinho.isEmpty()) {
            return false;
        }

        carrinho.clear();

        return true;
    }

}