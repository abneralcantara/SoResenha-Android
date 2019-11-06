package com.ufrpe.bsi.soresenha.infra.persistencia;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSAO_BANCO = 29;
    public static final String NOME_BANCO = "SORESENHA_BD";
    public static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");

    public static final String TABELA_USUARIO = "TB_USUARIO";
    public static final String COLUNA_ID = "ID";
    public static final String COLUNA_NOME = "NOME";
    public static final String COLUNA_EMAIL = "EMAIL";
    public static final String COLUNA_SENHA = "SENHA";
    public static final String COLUNA_TIPO = "TIPO";

    public static final String TABELA_FESTA = "TB_FESTA";
    public static final String COLUNA_IDFESTA = "ID_FESTA";
    public static final String COLUNA_NOMEFESTA = "NOME_FESTA";
    public static final String COLUNA_DESCRICAOFESTA = "DESC_FESTA";
    public static final String COLUNA_PRECOFESTA = "PRECO_FESTA";
    public static final String COLUNA_DATAFESTA = "DATA_FESTA";
    public static final String COLUNA_CRIADORFESTA = "CRIADOR_ID";

    private Logger logger = Logger.getGlobal();


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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        logger.log(Level.INFO, "Upgrading DB from "+oldVersion+" to " +newVersion);
        dropTables(db);
        onCreate(db);
    }

    private void criarTabelaFesta(SQLiteDatabase db) {
        String QUERY_COLUNAFESTA = "CREATE TABLE " + TABELA_FESTA + "("
                + COLUNA_IDFESTA + " INTEGER PRIMARY KEY, "
                + COLUNA_NOMEFESTA + " TEXT, "
                + COLUNA_PRECOFESTA + " TEXT, "
                + COLUNA_DATAFESTA + " TEXT, "
                + COLUNA_CRIADORFESTA + " INTEGER, "
                + COLUNA_DESCRICAOFESTA + " TEXT)";
        db.execSQL(QUERY_COLUNAFESTA);
    }

    private void criarTabelaUsuario(SQLiteDatabase db) {
        String QUERY_COLUNAUSUARIO = "CREATE TABLE " + DBHelper.TABELA_USUARIO + "("
                + DBHelper.COLUNA_ID + " INTEGER PRIMARY KEY, "
                + DBHelper.COLUNA_TIPO + " TEXT, "
                + DBHelper.COLUNA_NOME + " TEXT, "
                + DBHelper.COLUNA_EMAIL + " TEXT,"
                + DBHelper.COLUNA_SENHA + " TEXT)";
        db.execSQL(QUERY_COLUNAUSUARIO);
    }

    private void dropTables(SQLiteDatabase db) {
        for (String tabela : TABELAS) {
            db.execSQL("DROP TABLE IF EXISTS " + tabela + ";");
        }
    }
}
