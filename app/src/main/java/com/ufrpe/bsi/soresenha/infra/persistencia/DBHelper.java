package com.ufrpe.bsi.soresenha.infra.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrpe.bsi.soresenha.infra.app.SoResenhaApp;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class DBHelper {
    public static final int VERSAO_BANCO = 14;
    public static final String BANCO_USUARIO = "bd_usuario";
    public static final String TABELA_USUARIO = "tb_usuario";
    public static final String COLUNA_ID = "id";
    public static final String COLUNA_NOME = "nome";
    public static final String COLUNA_EMAIL = "email";
    public static final String COLUNA_SENHA = "senha";
}
