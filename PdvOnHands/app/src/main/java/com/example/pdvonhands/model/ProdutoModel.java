package com.example.pdvonhands.model;

public class ProdutoModel {

    private int id;
    private String nome;
    private double valorUnitario;
    private double quantidade;

    public ProdutoModel() {
    }

    public ProdutoModel(int id, String nome, double valorUnitario, double quantidade) {
        this.id = id;
        this.nome = nome;
        this.valorUnitario = valorUnitario;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(double quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "ProdutoModel{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", quantidade=" + quantidade +
                '}';
    }
}
