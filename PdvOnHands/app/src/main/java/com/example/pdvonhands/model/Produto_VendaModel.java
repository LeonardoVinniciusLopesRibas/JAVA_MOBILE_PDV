package com.example.pdvonhands.model;

import java.util.List;

public class Produto_VendaModel {

    private int id;
    private List<ProdutoModel> produto;
    private List<PdvModel> venda;
    private double quantidade_total;
    private double valor_total;

    public Produto_VendaModel() {
    }

    public Produto_VendaModel(int id, List<ProdutoModel> produto, List<PdvModel> venda, double quantidade_total, double valor_total) {
        this.id = id;
        this.produto = produto;
        this.venda = venda;
        this.quantidade_total = quantidade_total;
        this.valor_total = valor_total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ProdutoModel> getProduto() {
        return produto;
    }

    public void setProduto(List<ProdutoModel> produto) {
        this.produto = produto;
    }

    public List<PdvModel> getVenda() {
        return venda;
    }

    public void setVenda(List<PdvModel> venda) {
        this.venda = venda;
    }

    public double getQuantidade_total() {
        return quantidade_total;
    }

    public void setQuantidade_total(double quantidade_total) {
        this.quantidade_total = quantidade_total;
    }

    public double getValor_total() {
        return valor_total;
    }

    public void setValor_total(double valor_total) {
        this.valor_total = valor_total;
    }
}