package com.ufrpe.bsi.soresenha.infra.persistencia;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSAO_BANCO = 14;
    public static final String NOME_BANCO = "bdAppMpoo";
    public static final String TABELA_USUARIO = "tb_usuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";

    public static final String TABELA_FESTA = "td_festa";
    public static final String COLUNA_IDFESTA = "id_festa";
    public static final String COLUNA_NOMEFESTA = "nome_festa";
    public static final String COLUNA_DESCRICAOFESTA = "descricao_festa";
    public static final String COLUNA_CRIADORFESTA = "criador_festa";



    private static final String[] TABELAS = {
            TABELA_USUARIO,
            TABELA_FESTA
    };

    public DBHelper(Context context) {
        super(context, DBHelper.NOME_BANCO , null, DBHelper.VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        criarTabelaUsuario(db);
        criarTabelaFesta(db);
    }

    private void criarTabelaFesta(SQLiteDatabase db) {
        String QUERY_COLUNAFESTA = "CREATE TABLE " + DBHelper.TABELA_FESTA + "("
                + DBHelper.COLUNA_IDFESTA + " INTEGER PRIMARY KEY, " + DBHelper.COLUNA_NOMEFESTA
                + " TEXT, " + DBHelper.COLUNA_CRIADORFESTA
                + " TEXT, " + DBHelper.COLUNA_DESCRICAOFESTA + " TEXT)";
        db.execSQL(QUERY_COLUNAFESTA);
    }

    private void criarTabelaUsuario(SQLiteDatabase db) {
        String QUERY_COLUNAUSUARIO = "CREATE TABLE " + DBHelper.TABELA_USUARIO + "("
                + DBHelper.COLUNA_ID + " INTEGER PRIMARY KEY, " + DBHelper.COLUNA_NOME
                + " TEXT, " + DBHelper.COLUNA_EMAIL + " TEXT," + DBHelper.COLUNA_SENHA
                + " TEXT)";
        db.execSQL(QUERY_COLUNAUSUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTables(db);

    }

    private void dropTables(SQLiteDatabase db) {
        StringBuilder dropTables = new StringBuilder();
        for (String tabela : TABELAS) {
            dropTables.append(" DROP TABLE IF EXISTS ");
            dropTables.append(tabela);
            dropTables.append("; ");
        }
        db.execSQL(dropTables.toString());
    }


}
