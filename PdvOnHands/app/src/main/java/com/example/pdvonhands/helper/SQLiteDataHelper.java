package com.example.pdvonhands.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SQLiteDataHelper extends SQLiteOpenHelper {


    public SQLiteDataHelper(@Nullable Context context,
                            @Nullable String name,
                            @Nullable SQLiteDatabase.CursorFactory factory,
                            int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE PRODUTO(ID_PRODUTO INTEGER PRIMARY KEY AUTOINCREMENT, NOME_PRODUTO VARCHAR(100) NOT NULL, VALORUNITARIO NUMERIC NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE CLIENTE(ID_CLIENTE INTEGER PRIMARY KEY AUTOINCREMENT, NOME_CLIENTE VARCHAR(100) NOT NULL, CPFCNPJ VARCHAR(14) NOT NULL, EMAIL VARCHAR(50) NOT NULL)");
        sqLiteDatabase.execSQL("CREATE TABLE VENDA(ID_VENDA INTEGER PRIMARY KEY AUTOINCREMENT, ID_CLIENTE INTEGER NOT NULL  , FOREIGN KEY (ID_CLIENTE) REFERENCES CLIENTE(ID_CLIENTE))");
        sqLiteDatabase.execSQL("CREATE TABLE PRODUTO_VENDA(ID_PRODUTO_VENDA INTEGER PRIMARY KEY AUTOINCREMENT, ID_PRODUTO INTEGER NOT NULL, ID_VENDA INTEGER NOT NULL, QUANTIDADE_TOTAL NUMERIC NOT NULL, VALOR_TOTAL NUMERIC NOT NULL, FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO(ID_PRODUTO), FOREIGN KEY (ID_VENDA) REFERENCES VENDA(ID_VENDA))");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Arroz', 13.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Feijão', 7.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Macarrão', 3.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Leite', 13.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Carne Moída', 7.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Picanha', 3.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Doce', 13.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Barra de chocolate', 7.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Refrigerante Coca-Cola', 3.99)");
        sqLiteDatabase.execSQL("INSERT INTO PRODUTO (NOME_PRODUTO, VALORUNITARIO) VALUES ('Bala fini', 13.99)");
        sqLiteDatabase.execSQL("INSERT INTO CLIENTE(NOME_CLIENTE, CPFCNPJ, EMAIL) VALUES ('Leonardo', '09408168909', 'leonardovinniciuslopesribas@gmail.com')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
