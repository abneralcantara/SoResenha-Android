package com.ufrpe.bsi.soresenha.infra.persistencia;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSAO_BANCO = 31;
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

    public static final String TABELA_AVALIACOES = "TB_AVALIACOES";
    public static final String COLUNA_IDAVALIACOES = "ID_AVALIACOES";
    public static final String COLUNA_IDEVENTOAVALIACOES = "IDEVENTO_AVALIACOES";
    public static final String COLUNA_IDUSERAVALIACOES = "IDUSER_AVALIACOES";
    public static final String COLUNA_LIKE = "LIKE_AVALIACOES";

    private Logger logger = Logger.getGlobal();

    private static final String[] TABELAS = {
            TABELA_USUARIO,
            TABELA_FESTA,
            TABELA_AVALIACOES
    };

    public DBHelper(Context context) {
        super(context, DBHelper.NOME_BANCO , null, DBHelper.VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        criarTabelaUsuario(db);
        criarTabelaFesta(db);
        criarTabelaAvaliacoes(db);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        logger.log(Level.INFO, "Upgrading DB from "+oldVersion+" to " +newVersion);
        dropTables(db);
        onCreate(db);
    }

    private void criarTabelaAvaliacoes(SQLiteDatabase db) {
        String QUERY_COLUNAAVALIACOES = "CREATE TABLE " + TABELA_AVALIACOES + "("
                + COLUNA_IDAVALIACOES + " INTEGER PRIMARY KEY, "
                + COLUNA_IDUSERAVALIACOES + " INTEGER, "
                + COLUNA_IDEVENTOAVALIACOES + " INTEGER, "
                + COLUNA_LIKE + " TEXT)";
        db.execSQL(QUERY_COLUNAAVALIACOES);

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
                + COLUNA_ID + " INTEGER PRIMARY KEY, "
                + COLUNA_TIPO + " TEXT, "
                + COLUNA_NOME + " TEXT, "
                + COLUNA_EMAIL + " TEXT,"
                + COLUNA_SENHA + " TEXT)";
        db.execSQL(QUERY_COLUNAUSUARIO);
    }

    private void dropTables(SQLiteDatabase db) {
        for (String tabela : TABELAS) {
            db.execSQL("DROP TABLE IF EXISTS " + tabela + ";");
        }
    }
}
