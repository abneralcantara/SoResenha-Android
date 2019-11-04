package com.ufrpe.bsi.soresenha.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.TipoUsuario;
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
        values.put(DBHelper.COLUNA_TIPO, usuario.getTipo().toString());
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

    public void update(Usuario user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE "+ DBHelper.TABELA_USUARIO + " SET " +
                DBHelper.COLUNA_NOME  + "=?, " +
                DBHelper.COLUNA_EMAIL  + "=?, " +
                DBHelper.COLUNA_SENHA + "=?, " +
                DBHelper.COLUNA_TIPO + "=?" +
                " WHERE " + DBHelper.COLUNA_ID + "=?;";
        db.execSQL(sql, new String[]{
                user.getNome(),
                user.getEmail(),
                user.getSenha(),
                user.getTipo().toString(),
                String.valueOf(user.getId())
        });
        db.close();
    }

    private Usuario createUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        TipoUsuario tipo = TipoUsuario.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_TIPO)));
        usuario.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_EMAIL)));
        usuario.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUNA_ID)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_SENHA)));
        usuario.setNome(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_NOME)));
        usuario.setTipo(tipo);
        return usuario;
    }

    public Usuario getByID(int id) {
        Usuario result = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO
                + " U WHERE U." + DBHelper.COLUNA_ID + "=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            result =  createUsuario(cursor);
        }
        cursor.close();
        db.close();
        return result;
    }
}

