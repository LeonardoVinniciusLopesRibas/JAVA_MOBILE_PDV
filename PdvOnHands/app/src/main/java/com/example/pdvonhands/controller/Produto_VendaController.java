package com.example.pdvonhands.controller;

import android.content.Context;

import com.example.pdvonhands.dao.ProdutoDAO;
import com.example.pdvonhands.dao.Produto_VendaDAO;

public class Produto_VendaController {

    private Produto_VendaDAO produtoVendaDAO;

    public Produto_VendaController (Context context){
        this.produtoVendaDAO = Produto_VendaDAO.getInstancia(context);
    }



}
