package com.ufrpe.bsi.soresenha.usuario.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class UsuarioDAO {
    private DBHelper dbHelper;

    public UsuarioDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long cadastrarUsuario(Usuario usuario){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOME, usuario.getNome());
        values.put(DBHelper.COLUNA_EMAIL, usuario.getEmail());
        values.put(DBHelper.COLUNA_SENHA, usuario.getSenha());

        long res = db.insert(DBHelper.TABELA_USUARIO, null, values);
        db.close();
        return res;
    }

    public Usuario getUsuario(String email, String password){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_USUARIO+ " U WHERE U." + DBHelper.COLUNA_EMAIL + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{email});

        if (cursor.moveToFirst()) {
            return createUsuario(cursor);
        }

        cursor.close();
        db.close();
        return null;
    }

    public void deletar(Usuario usuario) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] argumentos = {usuario.getEmail()};
        db.delete("tb_usuario", "email=?", argumentos);
        db.close();

    }

    public boolean checarEmail(String email){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + DBHelper.TABELA_USUARIO + " where " + DBHelper.COLUNA_EMAIL + "=?", new String[]{email});
        long count = cursor.getCount();
        cursor.close();
        if (count>0){
            return false;
        } else {
            return true;
        }
    }

    private Usuario createUsuario(Cursor cursor) {
        Usuario usuario = new Usuario();
        usuario.setEmail(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_EMAIL)));
        usuario.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUNA_ID)));
        usuario.setSenha(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_SENHA)));
        usuario.setNome(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_NOME)));
        return usuario;
    }


}
