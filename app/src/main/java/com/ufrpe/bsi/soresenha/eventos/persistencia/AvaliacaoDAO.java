package com.ufrpe.bsi.soresenha.eventos.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.soresenha.eventos.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.eventos.dominio.Evento;
import com.ufrpe.bsi.soresenha.eventos.dominio.TipoAvaliacao;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private DBHelper dbHelper;

    public AvaliacaoDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public long cadastrar(Avaliacao avaliacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_IDEVENTOAVALIACOES, avaliacao.getIdEvento());
        values.put(DBHelper.COLUNA_IDUSERAVALIACOES, avaliacao.getIdUser());
        values.put(DBHelper.COLUNA_LIKE, avaliacao.getTipoAvaliacao().toString());
        long res = db.insert(DBHelper.TABELA_AVALIACOES, null, values);
        db.close();
        return res;
    }

    public Avaliacao get(String idevento, String iduser){
        Avaliacao result = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACOES
                + " U WHERE U." + DBHelper.COLUNA_IDEVENTOAVALIACOES + "=? AND "
                + "U." + DBHelper.COLUNA_IDUSERAVALIACOES + "=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{idevento, iduser});
        if (cursor.moveToFirst()) {
            result =  createAvaliacao(cursor);
        }
        cursor.close();
        db.close();
        return result;
    }

    private Avaliacao createAvaliacao(Cursor cursor) {
        Avaliacao avaliacao = new Avaliacao();
        TipoAvaliacao tipo = TipoAvaliacao.valueOf(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_LIKE)));
        avaliacao.setIdEvento(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_IDEVENTOAVALIACOES)));
        avaliacao.setIdUser(cursor.getString(cursor.getColumnIndex(DBHelper.COLUNA_IDUSERAVALIACOES)));
        avaliacao.setTipoAvaliacao(tipo);
        return avaliacao;
    }

    public List<Avaliacao> list(String idEvento) {
        ArrayList<Avaliacao> avaliacaoList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACOES;
        Cursor cursor = db.rawQuery(sql, new String[]{});
        while (cursor.moveToNext()) {
            Avaliacao checkevento = createAvaliacao(cursor);
            if (checkevento.getIdEvento() == idEvento){
                avaliacaoList.add(checkevento);
            }
        }
        cursor.close();
        db.close();
        return avaliacaoList;
    }

    public void update(Avaliacao avaliacao) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE "+ DBHelper.TABELA_AVALIACOES + " SET " +
                DBHelper.COLUNA_IDEVENTOAVALIACOES  + "=?, " +
                DBHelper.COLUNA_LIKE + "=?, " +
                " WHERE " + DBHelper.COLUNA_IDUSERAVALIACOES  + "=?;";
        db.execSQL(sql, new String[]{
                avaliacao.getIdEvento(),
                String.valueOf(avaliacao.getTipoAvaliacao()),
                avaliacao.getIdUser()
        });
        db.close();
    }
}
