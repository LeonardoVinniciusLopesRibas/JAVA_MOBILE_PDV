package com.example.pdvonhands.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import com.example.pdvonhands.controller.ClienteController;
import com.example.pdvonhands.controller.PdvController;
import com.example.pdvonhands.controller.ProdutoController;
import com.example.pdvonhands.helper.SQLiteDataHelper;
import com.example.pdvonhands.model.PdvModel;
import com.example.pdvonhands.model.ProdutoModel;
import com.example.pdvonhands.model.Produto_VendaModel;

import java.util.ArrayList;
import java.util.List;

public class Produto_VendaDAO implements IGenericDAO<Produto_VendaModel>{

    private ProdutoController produtoController;
    private ClienteController clienteController;
    private PdvController pdvController;
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bancoDados;
    private String nomeTabela = "PRODUTO_VENDA";
    private String[]colunas = {"ID_PRODUTO_VENDA", "ID_PRODUTO", "ID_VENDA", "QUANTIDADE_TOTAL", "VALOR_TOTAL"};
    private Context context;
    private static Produto_VendaDAO instancia;

    public static Produto_VendaDAO getInstancia(Context context){
        if(instancia == null){
            return instancia = new Produto_VendaDAO(context);

        }else{
            return instancia;
        }
    }
    private Produto_VendaDAO(Context context){
        this.context = context;
        openHelper = new SQLiteDataHelper(this.context, "PDVHANDS", null, 1);
        bancoDados = openHelper.getWritableDatabase();
        this.produtoController = new ProdutoController(context);
        this.clienteController = new ClienteController(context);
    }

    @Override
    public long insert(Produto_VendaModel obj) {
        try {
            ContentValues valores = new ContentValues();
            valores.put(colunas[1], obj.getProduto().get(0).getId());
            valores.put(colunas[2], obj.getVenda().get(0).getId());
            valores.put(colunas[3], obj.getQuantidade_total());
            valores.put(colunas[4], obj.getValor_total());

            return bancoDados.insert(nomeTabela, null, valores);
        } catch (SQLException ex) {
            Log.e("Erro", "Produto_VendaDAO.insert: " + ex.getMessage());
        }
        return 0;
    }

    @Override
    public long update(Produto_VendaModel obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[1], obj.getProduto().get(0).getId());
            valores.put(colunas[2], obj.getVenda().get(0).getId());
            valores.put(colunas[3], obj.getQuantidade_total());
            valores.put(colunas[4], obj.getValor_total());

            String[] identificador = {String.valueOf(obj.getId())};
            return bancoDados.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
        }catch (SQLException ex){
            Log.e("Erro", "Produto_VendaDAO.update(): "+ ex.getMessage());
        }
        return 0;
    }

    @Override
    public long delete(Produto_VendaModel obj) {
        try{
            String[] identificador = {String.valueOf(obj.getId())};
            long resultado = bancoDados.delete(nomeTabela, colunas[0]+ " = ?", identificador);
            Log.d("Produto_VendaDAO", "Pdv deletado com resultado " + resultado);
            return resultado;
        }catch (SQLException ex){
            Log.e("Erro", "Produto_VendaDAO.delete(): "+ ex.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<Produto_VendaModel> getAll() {
        ArrayList<Produto_VendaModel> lista = new ArrayList<>();
        try {
            Cursor cursor = bancoDados.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
            if (cursor.moveToFirst()) {
                do {
                    Produto_VendaModel produtoVendaModel = new Produto_VendaModel();
                    produtoVendaModel.setId(cursor.getInt(0));

                    produtoVendaModel.setId(cursor.getInt(1));
                    ProdutoModel produtoModel = produtoController.obterProdutoById(produtoVendaModel.getId());
                    List<ProdutoModel> produtoModelList = new ArrayList<>();
                    if (produtoModel != null) {
                        produtoModelList.add(produtoModel);
                    }
                    produtoVendaModel.setProduto(produtoModelList);

                    produtoVendaModel.setId(cursor.getInt(2));
                    PdvModel pdvModel = pdvController.obterVendaPorId(produtoVendaModel.getId());
                    List<PdvModel> pdvModelList = new ArrayList<>();
                    if (produtoModel != null){
                        pdvModelList.add(pdvModel);
                    }
                    produtoVendaModel.setVenda(pdvModelList);

                    produtoVendaModel.setValor_total(cursor.getDouble(3));
                    produtoVendaModel.setQuantidade_total(cursor.getDouble(4));


                    lista.add(produtoVendaModel);

                } while (cursor.moveToNext());
            }
        } catch (SQLException ex) {
            Log.e("Erro", "Produto_VendaDAO.getAll(): " + ex.getMessage());
        }
        return lista;
    }


    @Override
    public Produto_VendaModel getById(int id) {
        try{
            String[] identificador = {String.valueOf(id)};
            Cursor cursor = bancoDados.query(nomeTabela, colunas, colunas[0] + " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()){
                Produto_VendaModel produtoVendaModel = new Produto_VendaModel();
                produtoVendaModel.setId(cursor.getInt(0));
                produtoVendaModel.setId(cursor.getInt(1));

                ProdutoModel produto = produtoController.obterProdutoById(produtoVendaModel.getId());
                List<ProdutoModel> produtoModelList = new ArrayList<>();
                if (produto != null) {
                    produtoModelList.add(produto);
                }
                produtoVendaModel.setProduto(produtoModelList);

                produtoVendaModel.setId(cursor.getInt(2));
                PdvModel pdvModel = pdvController.obterVendaPorId(produtoVendaModel.getId());
                List<PdvModel> pdvModelList = new ArrayList<>();
                if(produto != null) {
                    pdvModelList.add(pdvModel);
                }
                produtoVendaModel.setVenda(pdvModelList);

                produtoVendaModel.setValor_total(cursor.getDouble(3));
                produtoVendaModel.setQuantidade_total(cursor.getDouble(4));

                return produtoVendaModel;
            }
        }catch (SQLException ex){
            Log.e("Erro", "Produto_Venda.getById(): "+ ex.getMessage());
        }
        return null;
    }
}
