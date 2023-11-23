package com.example.pdvonhands.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pdvonhands.helper.SQLiteDataHelper;
import com.example.pdvonhands.model.ClienteModel;

import java.util.ArrayList;

public class ClienteDAO implements IGenericDAO<ClienteModel>{

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase bancoDados;
    private String nomeTabela = "CLIENTE";
    private String[]colunas = {"ID_CLIENTE", "NOME_CLIENTE", "CPFCNPJ", "EMAIL"};
    private Context context;
    private static ClienteDAO instancia;

    public static ClienteDAO getInstancia(Context context){
        if(instancia == null){
            return instancia = new ClienteDAO(context);

        }else{
            return instancia;
        }
    }

    private ClienteDAO(Context context){
        this.context = context;
        openHelper = new SQLiteDataHelper(this.context, "PDVHANDS", null, 1);
        bancoDados = openHelper.getWritableDatabase();
    }

    @Override
    public long insert(ClienteModel obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[1], obj.getNome());
            valores.put(colunas[2], obj.getCpfcnpj());
            valores.put(colunas[3], obj.getEmail());

            return bancoDados.insert(nomeTabela, null, valores);

        }catch (SQLException ex){
            Log.e("Erro", "ClienteDAO.insert(): "+ex.getMessage());
        }
        return 0;
    }

    @Override
    public long update(ClienteModel obj) {
        try{
            ContentValues valores = new ContentValues();
            valores.put(colunas[2], obj.getCpfcnpj());
            String[] identificador = {String.valueOf(obj.getNome()),
                                    String.valueOf(obj.getEmail())};
            return bancoDados.update(nomeTabela, valores, colunas[2] + " = ?", identificador);
        }catch (SQLException ex){
            Log.e("Erro", "ClienteDAO.update(): "+ ex.getMessage());
        }
        return 0;
    }

    @Override
    public long delete(ClienteModel obj) {
        try {
            String[] identificador = {String.valueOf(obj.getCpfcnpj())};
            long resultado = bancoDados.delete(nomeTabela, colunas[2] + " = ?", identificador);

            Log.d("ClienteDAO", "Cliente deletado com resultado: " + resultado);

            return resultado;
        } catch (SQLException ex) {
            Log.e("ERRO", "ClienteDAO.delete(): " + ex.getMessage());
        }
        return 0;
    }


    @Override
    public ArrayList<ClienteModel> getAll() {

        ArrayList<ClienteModel> lista = new ArrayList<>();
        try{
            Cursor cursor = bancoDados.query(nomeTabela, colunas, null, null, null, null, colunas[0]);

            if(cursor.moveToFirst()){
                do {
                    ClienteModel cModel = new ClienteModel();
                    cModel.setId(cursor.getInt(0));
                    cModel.setNome(cursor.getString(1));
                    cModel.setCpfcnpj(cursor.getString(2));
                    cModel.setEmail(cursor.getString(3));

                    lista.add(cModel);

                }while (cursor.moveToNext());
            }
        }catch (SQLException ex){
            Log.e("Erro", "ClienteDAO.getAll(): "+ ex.getMessage());
        }
        return lista;
    }

    @Override
    public ClienteModel getById(int id) {
        try{
            String[]identificador = {String.valueOf(id)};
            Cursor cursor = bancoDados.query(nomeTabela, colunas, colunas[0]+ " = ?", identificador, null, null, null);
            if (cursor.moveToFirst()){
                ClienteModel clienteModel = new ClienteModel();
                clienteModel.setId(cursor.getInt(0));
                clienteModel.setNome(cursor.getString(1));
                clienteModel.setCpfcnpj(cursor.getString(2));
                clienteModel.setEmail(cursor.getString(3));

                return clienteModel;
            }
        }catch (SQLException ex){
            Log.e("Erro", "ClienteDAO.getById(): "+ex.getMessage());
        }

        return null;
    }

    public ClienteModel getClienteByCPF(String cpfcnpj) {
        try {
            String selection = colunas[2] + "=?";
            String[] selectionArgs = {cpfcnpj};

            Cursor cursor = bancoDados.query(nomeTabela, colunas, selection, selectionArgs, null, null, null);

            if (cursor.moveToFirst()) {
                ClienteModel cliente = new ClienteModel();
                cliente.setId(cursor.getInt(0));
                cliente.setNome(cursor.getString(1));
                cliente.setCpfcnpj(cursor.getString(2));
                cliente.setEmail(cursor.getString(3));
                return cliente;
            }
        } catch (SQLException ex) {
            Log.e("Erro", "ClienteDAO.getClienteByCPF(): " + ex.getMessage());
        }
        return null;
    }


}
