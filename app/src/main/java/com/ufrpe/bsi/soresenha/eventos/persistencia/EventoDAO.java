package com.ufrpe.bsi.soresenha.eventos.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.dominio.Usuario;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventoDAO {
    private DBHelper dbHelper;

    public EventoDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long cadastrar(Evento evento){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_NOMEFESTA, evento.getNome());
        values.put(DBHelper.COLUNA_CRIADORFESTA, evento.getCriador().getId());
        values.put(DBHelper.COLUNA_PRECOFESTA, evento.getPreco().toString());
        values.put(DBHelper.COLUNA_DATAFESTA, DBHelper.dateTimeFormat.format(evento.getDate()));
        values.put(DBHelper.COLUNA_DESCRICAOFESTA, evento.getDescricao());
        values.put(DBHelper.COLUNA_QNTLIKEFESTA, String.valueOf(evento.getQntLikes()));
        long res = db.insert(DBHelper.TABELA_FESTA, null, values);
        db.close();
        return res;
    }

    public Evento get(long eventId){
        Evento result = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_FESTA + " U WHERE U." + DBHelper.COLUNA_IDFESTA + " = ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(eventId)});
        if (cursor.moveToFirst()) {
            result = createEvento(cursor);
        }
        cursor.close();
        db.close();
        return result;
    }

    public void update(Evento evento) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE "+ DBHelper.TABELA_FESTA + " SET " +
                DBHelper.COLUNA_NOMEFESTA  + "=?, " +
                DBHelper.COLUNA_PRECOFESTA  + "=?, " +
                DBHelper.COLUNA_DESCRICAOFESTA + "=?, " +
                DBHelper.COLUNA_QNTLIKEFESTA + "=?, " +
                DBHelper.COLUNA_DATAFESTA + "=?" +
                " WHERE " + DBHelper.COLUNA_IDFESTA + "=?;";
        db.execSQL(sql, new String[]{
                evento.getNome(),
                evento.getPreco().toString(),
                evento.getDescricao(),
                String.valueOf(evento.getQntLikes()),
                DBHelper.dateTimeFormat.format(evento.getDate()),
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
        evento.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUNA_IDFESTA)));
        evento.setDescricao(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_DESCRICAOFESTA)));
        evento.setCriador(new Usuario(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUNA_CRIADORFESTA))));
        evento.setPreco(new BigDecimal(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_PRECOFESTA))));
        try {
            evento.setDate(DBHelper.dateTimeFormat.parse(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_DATAFESTA))));
        } catch (ParseException e) {
            evento.setDate(new Date());
            e.printStackTrace();
        }
        return evento;
    }

    public List<Usuario> getListParticipantes(Evento evento){
        ArrayList<Usuario> listParticipantes = new ArrayList<Usuario>();
        return listParticipantes;

    }

}
