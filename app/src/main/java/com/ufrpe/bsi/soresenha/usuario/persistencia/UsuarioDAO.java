package com.ufrpe.bsi.soresenha.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.soresenha.infra.negocio.SessaoUsuario;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class UsuarioDAO {
    private DBHelper dbHelper;


    public UsuarioDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long cadastrar(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOME, usuario.getNome());
        values.put(DBHelper.COLUNA_EMAIL, usuario.getEmail());
        values.put(DBHelper.COLUNA_SENHA, usuario.getSenha());
        values.put(DBHelper.COLUNA_SERPARCEIRO, usuario.getParceiro());
        long res = db.insert(DBHelper.TABELA_USUARIO, null, values);
        db.close();
        return res;
    }

    public Usuario get(String email, String password){
        Usuario result = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO
                + " U WHERE U." + DBHelper.COLUNA_EMAIL + "=? AND "
                + "U." + DBHelper.COLUNA_SENHA + "=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{email, password});
        if (cursor.moveToFirst()) {
            result =  createUsuario(cursor);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void deletar(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] argumentos = {usuario.getEmail()};
        db.delete(DBHelper.TABELA_USUARIO, DBHelper.COLUNA_EMAIL + "=?", argumentos);
        db.close();
    }

    public boolean checarEmail(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABELA_USUARIO + " WHERE " + DBHelper.COLUNA_EMAIL + "=?", new String[]{email});
        return !cursor.moveToNext();
    }

    private Usuario createUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_EMAIL)));
        usuario.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUNA_ID)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_SENHA)));
        usuario.setNome(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_NOME)));
        return usuario;
    }
    public void alterarEmail(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOME, usuario.getEmail());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.COLUNA_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    //altera a senha
    public void alterarSenha(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_SENHA, usuario.getSenha());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.COLUNA_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }

    //altera o nome
    public void alterarNome(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOME, usuario.getNome());
        db.update(DBHelper.TABELA_USUARIO, values, DBHelper.COLUNA_ID + " = ?", new String[]{String.valueOf(usuario.getId())});
        db.close();
    }


}
