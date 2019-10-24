package com.ufrpe.bsi.soresenha.eventos.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

public class EventoDAO {
    private DBHelper dbHelper;

    public EventoDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    public long cadastrarEvento(Evento evento){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOMEFESTA, evento.getNome());
        values.put(DBHelper.COLUNA_CRIADORFESTA, evento.getCriador());
        values.put(DBHelper.COLUNA_DESCRICAOFESTA, evento.getDescricao());

        long res = db.insert(DBHelper.TABELA_FESTA, null, values);
        db.close();
        return res;
    }

    public Evento getEvento(String nomeEvento){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_FESTA + " U WHERE U." + DBHelper.COLUNA_NOMEFESTA + " LIKE ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{nomeEvento});

        if (cursor.moveToFirst()) {
            return createEvento(cursor);
        }

        cursor.close();
        db.close();
        return null;
    }

    public Evento getEventobyid(String idevento){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_FESTA + " U WHERE U." + DBHelper.COLUNA_IDFESTA + " LIKE ?;";
       Cursor cursor = db.rawQuery(sql, new String[]{idevento});
//essa função não esta completa ainda falta alterala pra entrar em forma de long
        if (cursor.moveToFirst()) {
            return createEvento(cursor);
        }

        cursor.close();
        db.close();
        return null;
    }

    public void deletar(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] argumentos = {evento.getNome()};
        db.delete("tb_festa", "nome_festa=?", argumentos);
        db.close();

    }

    public boolean checarEvento(String nomeEvento){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + DBHelper.TABELA_FESTA + " where " + DBHelper.COLUNA_NOMEFESTA + "=?", new String[]{nomeEvento});
        long count = cursor.getCount();
        cursor.close();
        if (count>0){
            return false;
        } else {
            return true;
        }
    }

    private Evento createEvento(Cursor cursor) {
        Evento evento = new Evento();
        evento.setNome(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_NOMEFESTA)));
        evento.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUNA_IDFESTA)));
        evento.setDescricao(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_DESCRICAOFESTA)));
        evento.setCriador(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_CRIADORFESTA)));
        return evento;
    }

}
