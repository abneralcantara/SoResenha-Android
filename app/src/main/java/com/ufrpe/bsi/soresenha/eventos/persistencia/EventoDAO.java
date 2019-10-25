package com.ufrpe.bsi.soresenha.eventos.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EventoDAO {
    private DBHelper dbHelper;

    public EventoDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long cadastrarEvento(Evento evento){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOMEFESTA, evento.getNome());
        values.put(DBHelper.COLUNA_CRIADORFESTA, evento.getIdCriador());
        values.put(DBHelper.COLUNA_PRECOFESTA, evento.getPreco());
        values.put(DBHelper.COLUNA_DESCRICAOFESTA, evento.getDescricao());
        long res = db.insert(DBHelper.TABELA_FESTA, null, values);
        db.close();
        return res;
    }

    public Evento getEvento(long eventId){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_FESTA + " U WHERE U." + DBHelper.COLUNA_IDFESTA + " = ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(eventId)});
        if (cursor.moveToFirst()) {
            return createEvento(cursor);
        }
        cursor.close();
        db.close();
        return null;
    }

    public void save(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE "+ DBHelper.TABELA_FESTA + " SET " +
                DBHelper.COLUNA_NOMEFESTA  + "=?, " +
                DBHelper.COLUNA_PRECOFESTA  + "=?, " +
                DBHelper.COLUNA_DESCRICAOFESTA + "=? " +
                " WHERE " + DBHelper.COLUNA_IDFESTA + "=?;";
        db.execSQL(sql, new String[]{
                evento.getNome(),
                evento.getPreco(),
                evento.getDescricao(),
                String.valueOf(evento.getId())
        });
        db.close();
    }

    public void deletar(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] argumentos = {String.valueOf(evento.getId())};
        db.delete(DBHelper.TABELA_FESTA, DBHelper.COLUNA_IDFESTA + " =?", argumentos);
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

    public List<Evento> list() {
        ArrayList<Evento> eventoList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_FESTA;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            eventoList.add(createEvento(cursor));
        }
        cursor.close();
        db.close();
        return eventoList;
    }

    private Evento createEvento(Cursor cursor) {
        Evento evento = new Evento();
        evento.setNome(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_NOMEFESTA)));
        evento.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUNA_IDFESTA)));
        evento.setDescricao(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_DESCRICAOFESTA)));
        evento.setIdCriador(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUNA_CRIADORFESTA)));
        evento.setPreco(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_PRECOFESTA)));
        return evento;
    }

}
