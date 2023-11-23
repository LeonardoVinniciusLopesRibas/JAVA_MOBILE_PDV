    package com.example.pdvonhands.dao;

    import android.content.ContentValues;
    import android.content.Context;
    import android.database.Cursor;
    import android.database.SQLException;
    import android.database.sqlite.SQLiteDatabase;
    import android.database.sqlite.SQLiteOpenHelper;
    import android.util.Log;

    import com.example.pdvonhands.controller.ClienteController;
    import com.example.pdvonhands.controller.ProdutoController;
    import com.example.pdvonhands.helper.SQLiteDataHelper;
    import com.example.pdvonhands.model.ClienteModel;
    import com.example.pdvonhands.model.PdvModel;
    import com.example.pdvonhands.model.ProdutoModel;
    import java.util.ArrayList;
    import java.util.List;

    public class PdvDAO implements IGenericDAO<PdvModel>{
        private ProdutoController prodController;
        private ClienteController clienteController;
        private SQLiteOpenHelper openHelper;
        private SQLiteDatabase bancoDados;
        private String nomeTabela = "VENDA";
        private String[]colunas = {"ID_VENDA", "ID_PRODUTO", "ID_CLIENTE"};
        private Context context;
        private static PdvDAO instancia;
        public static PdvDAO getInstancia(Context context){
            if (instancia == null){
                return instancia = new PdvDAO(context);
            }else{
                return instancia;
            }
        }
        public PdvDAO(Context context){
            this.context = context;
            openHelper = new SQLiteDataHelper(this.context, "PDVHANDS", null, 1);
            bancoDados = openHelper.getWritableDatabase();
            this.prodController = new ProdutoController(context);
            this.clienteController = new ClienteController(context);
        }

        @Override
        public long insert(PdvModel obj) {
            try{
                ContentValues valores = new ContentValues();
                valores.put(colunas[1], obj.getCliente().get(0).getId());
                valores.put(colunas[2], obj.getProdutos().get(0).getId());

                return bancoDados.insert(nomeTabela, null, valores);
            }catch (SQLException ex){
                Log.e("Erro", "PdvDAO.insert: "+ ex.getMessage());
            }
            return 0;
        }

        @Override
        public long update(PdvModel obj) {
            try{
                ContentValues valores = new ContentValues();
                valores.put(colunas[1], obj.getProdutos().get(0).getId());
                valores.put(colunas[2], obj.getCliente().get(0).getId());

                String[] identificador = {String.valueOf(obj.getId())};
                return bancoDados.update(nomeTabela, valores, colunas[0] + " = ?", identificador);
            } catch (SQLException ex){
                Log.e("Erro","PdvDAO.update(): "+ex.getMessage());
            }
            return 0;
        }

        @Override
        public long delete(PdvModel obj) {
            try{
                String[] identificador = {String.valueOf(obj.getId())};
                long resultado = bancoDados.delete(nomeTabela, colunas[0]+ " = ?", identificador);

                Log.d("PdvDAO", "Pdv deletado com resultado " + resultado);
                return resultado;
            }catch (SQLException ex){
                Log.e("Erro", "PdvDAO.delete(): "+ex.getMessage());
            }
            return 0;
        }

        @Override
        public ArrayList<PdvModel> getAll() {
            ArrayList<PdvModel> lista = new ArrayList<>();
             try{
                Cursor cursor = bancoDados.query(nomeTabela, colunas, null, null, null, null, colunas[0]);
                if (cursor.moveToFirst()){
                    do{

                        PdvModel pdvModel = new PdvModel();
                        pdvModel.setId(cursor.getInt(0));


                        pdvModel.setId(cursor.getInt(1));
                        ProdutoModel produto = prodController.obterProdutoById(pdvModel.getId());
                        List<ProdutoModel> produtoModelList = new ArrayList<>();
                        if (produto != null) {
                            produtoModelList.add(produto);
                        }
                        pdvModel.setProdutos(produtoModelList);



                        ClienteModel clienteModel = clienteController.selectClienteById(cursor.getInt(2));
                        List<ClienteModel> clienteModelList = new ArrayList<>();
                        if (clienteModel != null){
                            clienteModelList.add(clienteModel);
                        }
                        pdvModel.setCliente(clienteModelList);




                        lista.add(pdvModel);
                    }while (cursor.moveToNext());
                }
            }catch (SQLException ex) {
                 Log.e("Erro", "ProdutoDAO.getAll(): " + ex.getMessage());
             }
            return lista;
        }

        @Override
        public PdvModel getById(int id) {

            try {
                String[] identificador = {String.valueOf(id)};
                Cursor cursor = bancoDados.query(nomeTabela, colunas, colunas[0] + " = ?", identificador, null, null, null);

                if (cursor.moveToFirst()) {
                    PdvModel pdvModel = new PdvModel();
                    pdvModel.setId(cursor.getInt(0));
                    pdvModel.setId(cursor.getInt(1));

                    ProdutoModel produto = prodController.obterProdutoById(pdvModel.getId());
                    List<ProdutoModel> produtoModelList = new ArrayList<>();
                    if (produto != null) {
                        produtoModelList.add(produto);
                    }
                    pdvModel.setProdutos(produtoModelList);

                    ClienteModel clienteModel = clienteController.selectClienteById(cursor.getInt(2));
                    List<ClienteModel> clienteModelList = new ArrayList<>();
                    if (clienteModel != null){
                        clienteModelList.add(clienteModel);
                    }
                    pdvModel.setCliente(clienteModelList);

                    return pdvModel;
                }
            } catch (SQLException ex) {
                Log.e("Erro", "PdvDAO.getById(): " + ex.getMessage());
            }
            return null;
        }
    }

