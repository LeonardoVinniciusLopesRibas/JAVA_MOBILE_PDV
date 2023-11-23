package com.example.pdvonhands.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pdvonhands.helper.SQLiteDataHelper;
import com.example.pdvonhands.model.ProdutoModel;

import java.util.ArrayList;

public class ProdutoDAO implements IGenericDAO<ProdutoModel> {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bancoDados;
    private String nomeTabela = "PRODUTO";
    private String[]colunas = {"ID_PRODUTO", "NOME_PRODUTO", "VALORUNITARIO"};
    private Context context;
    private static ProdutoDAO instancia;
    public static ProdutoDAO getInstancia(Context context){
        if (instancia == null){
            return instancia = new ProdutoDAO(context);
        }else{
            return instancia;
        }
    }

    public ProdutoDAO(Context context){
        this.context = context;

        openHelper = new SQLiteDataHelper(this.context, "PDVHANDS", null, 1);

        bancoDados = openHelper.getWritableDatabase();

    }


    @Override
    public long insert(ProdutoModel obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[1], obj.getNome());
            valores.put(colunas[2], obj.getValorUnitario());

            return bancoDados.insert(nomeTabela, null, valores);

        }catch (SQLException ex){
            Log.e("Erro", "ProdutoDAO.insert(): "+ex.getMessage());
        }
        return 0;
    }

    @Override
    public long update(ProdutoModel obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[2], obj.getNome());
            String[] identificador = {String.valueOf(obj.getNome()),
                    String.valueOf(obj.getValorUnitario())};
            return bancoDados.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
        }catch (SQLException ex){
            Log.e("Erro", "ProdutoDAO.update(): "+ ex.getMessage());
        }
        return 0;
    }

    @Override
    public long delete(ProdutoModel obj) {
        try {
            String[] identificador = {String.valueOf(obj.getId())};
            long resultado = bancoDados.delete(nomeTabela, colunas[0] + " = ?", identificador);

            Log.d("ProdutoDAO", "Cliente deletado com resultado: " + resultado);

            return resultado;
        } catch (SQLException ex) {
            Log.e("ERRO", "ProdutoDAO.delete(): " + ex.getMessage());
        }
        return 0;
    }

    @Override
    public ArrayList<ProdutoModel> getAll() {

        ArrayList<ProdutoModel> lista = new ArrayList<>();
        try{
            Cursor cursor = bancoDados.query(nomeTabela, colunas, null, null, null, null, colunas[0]);

            if(cursor.moveToFirst()){
                do {
                    ProdutoModel pModel = new ProdutoModel();
                    pModel.setId(cursor.getInt(0));
                    pModel.setNome(cursor.getString(1));
                    pModel.setValorUnitario(cursor.getDouble(2));

                    lista.add(pModel);

                }while (cursor.moveToNext());
            }
        }catch (SQLException ex){
            Log.e("Erro", "AlunoDao.getAll(): "+ ex.getMessage());
        }
        return lista;
    }

    @Override
    public ProdutoModel getById(int id) {

        try{
            String[]identificador = {String.valueOf(id)};
            Cursor cursor = bancoDados.query(nomeTabela, colunas, colunas[0]+ " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()){
                ProdutoModel produtoModel = new ProdutoModel();
                produtoModel.setId(cursor.getInt(0));
                produtoModel.setNome(cursor.getString(1));
                produtoModel.setValorUnitario(cursor.getDouble(2));

                return produtoModel;
            }
        }catch (SQLException ex){
            Log.e("Erro", "ProdutoDAO.getById(): "+ex.getMessage());
        }

        return null;
    }
}
