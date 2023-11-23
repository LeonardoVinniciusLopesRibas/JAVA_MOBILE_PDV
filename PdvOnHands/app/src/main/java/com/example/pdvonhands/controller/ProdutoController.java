package com.example.pdvonhands.controller;

import android.content.Context;

import com.example.pdvonhands.dao.ProdutoDAO;
import com.example.pdvonhands.model.ProdutoModel;

import java.util.List;


public class ProdutoController {

    private ProdutoDAO produtoDAO;

    public ProdutoController (Context context) {
        this.produtoDAO = ProdutoDAO.getInstancia(context);
    }

    public boolean insertProduto(String nome, double valorUnitario){
        if (nome.isEmpty()){
            return false;
        }
        if (valorUnitario <= 0 ){
            return false;
        }
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(nome);
        produtoModel.setValorUnitario(valorUnitario);

        long resultado = produtoDAO.insert(produtoModel);
        return resultado != -1;
    }

    public boolean updateProduto(String nome, double valorUnitario){
        if (nome.isEmpty() || valorUnitario <0 ){
            return false;
        }
        ProdutoModel produtoModel = new ProdutoModel();
        produtoModel.setNome(nome);
        produtoModel.setValorUnitario(valorUnitario);

        long resultado = produtoDAO.update(produtoModel);

        return resultado != -1;

    }
//teste
    public boolean deletarProduto(int id){
        if (id<=0){
            return false;
        }

        ProdutoModel produto = produtoDAO.getById(id);

        if (produto != null){
            long resultado = produtoDAO.delete(produto);
            return resultado != -1;
        }
        return false;
    }

    public List<ProdutoModel> selectAllProdutos(){
        return produtoDAO.getAll();
    }

    public ProdutoModel obterProdutoById(int id){
        return produtoDAO.getById(id);
    }


}
