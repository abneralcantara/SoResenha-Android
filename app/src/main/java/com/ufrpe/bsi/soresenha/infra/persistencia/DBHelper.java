package com.ufrpe.bsi.soresenha.infra.persistencia;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;

public class DBHelper extends SQLiteOpenHelper {
    public static final int VERSAO_BANCO = 32;
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

    public static final String TABLE_IMAGEMEVENTO = "TB_IMAGEMEVENTO";
    public static final String COLUNA_IDIMAGEMEVENTO = "IDIMAGEMEVENTO";
    public static final String COLUNA_IDEVENTO = "IDEVENTO";
    public static final String COLUNA_IMAGEM = "IMAGEM";

    private static final String[] TABELAS = {
            TABELA_USUARIO,
            TABELA_FESTA,
            TABELA_AVALIACOES,
            TABLE_IMAGEMEVENTO
    };

    public DBHelper(Context context) {
        super(context, DBHelper.NOME_BANCO , null, DBHelper.VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        criarTabelaUsuario(db);
        criarTabelaFesta(db);
        criarTabelaAvaliacoes(db);
        criarTabelaImagemEvento(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DB Upgrade", "Upgrading DB from "+oldVersion+" to " +newVersion);
        dropTables(db);
        onCreate(db);
    }

    private void criarTabelaAvaliacoes(SQLiteDatabase db) {
        String QUERY_COLUNAAVALIACOES = "CREATE TABLE " + TABELA_AVALIACOES + "("
                + COLUNA_IDAVALIACOES + " INTEGER PRIMARY KEY, "
                + COLUNA_IDUSERAVALIACOES + " INTEGER, "
                + COLUNA_IDEVENTOAVALIACOES + " INTEGER, "
                + COLUNA_LIKE + " TEXT, "
                + "FOREIGN KEY(" + COLUNA_IDUSERAVALIACOES + ") REFERENCES "+ TABELA_USUARIO +"("+ COLUNA_ID +"), "
                + "FOREIGN KEY(" + COLUNA_IDEVENTOAVALIACOES + ") REFERENCES "+ TABELA_FESTA +"("+ COLUNA_IDFESTA +") "
                + ")";
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
        String QUERY_COLUNAUSUARIO = "CREATE TABLE " + TABELA_USUARIO + "("
                + COLUNA_ID + " INTEGER PRIMARY KEY, "
                + COLUNA_TIPO + " TEXT, "
                + COLUNA_NOME + " TEXT, "
                + COLUNA_EMAIL + " TEXT,"
                + COLUNA_SENHA + " TEXT)";
        db.execSQL(QUERY_COLUNAUSUARIO);
    }

    private void criarTabelaImagemEvento(SQLiteDatabase db) {
        String QUERY_TABELAIMAGEM = "CREATE TABLE " + TABLE_IMAGEMEVENTO + "("
                + COLUNA_IDIMAGEMEVENTO + " INTEGER PRIMARY KEY, "
                + COLUNA_IDEVENTO + " INTEGER, "
                + COLUNA_IMAGEM + " BLOB, "
                + "FOREIGN KEY(" + COLUNA_IDEVENTO + ") REFERENCES "+ TABELA_FESTA +"("+ COLUNA_IDFESTA +") "
                + ")";
        db.execSQL(QUERY_TABELAIMAGEM);
    }

    private void dropTables(SQLiteDatabase db) {
        for (String tabela : TABELAS) {
            db.execSQL("DROP TABLE IF EXISTS " + tabela + ";");
        }
    }
}
