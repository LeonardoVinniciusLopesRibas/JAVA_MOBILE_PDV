package com.example.pdvonhands.model;
import java.util.List;
public class PdvModel {
    private int id;
    private List<ProdutoModel> produtos;
    private List<ClienteModel> cliente;

    public PdvModel() {
    }

    public PdvModel(int id, List<ProdutoModel> produtos, List<ClienteModel> cliente) {
        this.id = id;
        this.produtos = produtos;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ProdutoModel> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<ProdutoModel> produtos) {
        this.produtos = produtos;
    }

    public List<ClienteModel> getCliente() {
        return cliente;
    }

    public void setCliente(List<ClienteModel> cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "PdvModel{" +
                "id=" + id +
                ", produtos=" + produtos +
                ", cliente=" + cliente +
                '}';
    }
}