package com.ufrpe.bsi.soresenha.infra.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrpe.bsi.soresenha.infra.app.SoResenhaApp;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class DBHelper  extends SQLiteOpenHelper {
    private static final int VERSAO_BANCO = 14;
    private static  final String BANCO_USUARIO = "bd_usuario";
    private static  final String TABELA_USUARIO = "tb_usuario";
    private static  final String COLUNA_ID = "id";
    private static  final String COLUNA_NOME = "nome";
    private static  final String COLUNA_EMAIL = "email";
    private static  final String COLUNA_SENHA = "senha";



    public DBHelper(Context context) {
        super(context, BANCO_USUARIO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String QUERY_COLUNA = "CREATE TABLE " + TABELA_USUARIO + "("
                + COLUNA_ID + " INTEGER PRIMARY KEY, " + COLUNA_NOME
                + " TEXT, " + COLUNA_EMAIL + " TEXT," + COLUNA_SENHA
                + " TEXT)";
        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABELA_USUARIO);
    }


    public long cadastraUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, usuario.getNome());
        values.put(COLUNA_EMAIL, usuario.getEmail());
        values.put(COLUNA_SENHA, usuario.getSenha());

        long res = db.insert(TABELA_USUARIO, null, values);
        db.close();
        return res;
    }

    public boolean checarUsuario(Usuario usuario){
        String[] coluna = {COLUNA_ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COLUNA_EMAIL + "=?" + " and " + COLUNA_SENHA + "=?";
        String[] selectionArgs = {usuario.getEmail(), usuario.getSenha()};
        Cursor cursor = db.query(TABELA_USUARIO, coluna, selection,selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count>0){
            return true;
        } else {
            return false;
        }
    }

    public void deletar(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] argumentos = {usuario.getEmail()};
        db.delete("tb_usuario", "email=?", argumentos);
        return;
    }

    public boolean checarEmail(Usuario usuario){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABELA_USUARIO + " where " + COLUNA_EMAIL + "=?", new String[]{usuario.getEmail()});
        long count = cursor.getCount();
        cursor.close();
        if (count>0){
            return false;
        } else {
            return true;
        }
    }
}
