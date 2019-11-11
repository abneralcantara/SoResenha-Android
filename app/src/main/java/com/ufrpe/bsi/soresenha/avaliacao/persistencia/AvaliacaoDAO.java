package com.ufrpe.bsi.soresenha.avaliacao.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ufrpe.bsi.soresenha.avaliacao.dominio.Avaliacao;
import com.ufrpe.bsi.soresenha.avaliacao.dominio.TipoAvaliacao;
import com.ufrpe.bsi.soresenha.eventos.persistencia.EventoDAO;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;
import com.ufrpe.bsi.soresenha.usuario.persistencia.UsuarioDAO;

import java.util.ArrayList;
import java.util.List;

public class AvaliacaoDAO {
    private DBHelper dbHelper;
    private UsuarioDAO usuarioDAO;
    private EventoDAO eventoDAO;

    public AvaliacaoDAO(Context context) {
        this.dbHelper = new DBHelper(context);
        this.usuarioDAO = new UsuarioDAO(context);
        this.eventoDAO = new EventoDAO(context);
    }

    public long cadastrar(Avaliacao avaliacao){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_IDEVENTOAVALIACOES, avaliacao.getEvento().getId());
        values.put(DBHelper.COLUNA_IDUSERAVALIACOES, avaliacao.getUsuario().getId());
        values.put(DBHelper.COLUNA_LIKE, avaliacao.getTipoAvaliacao().toString());
        long res = db.insert(DBHelper.TABELA_AVALIACOES, null, values);
        db.close();
        return res;
    }

    public Avaliacao get(long idEvento, long idUser){
        Avaliacao result = null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACOES
                + " U WHERE U." + DBHelper.COLUNA_IDEVENTOAVALIACOES + "=? AND "
                + "U." + DBHelper.COLUNA_IDUSERAVALIACOES + "=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idUser), String.valueOf(idEvento)});
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
        avaliacao.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUNA_IDAVALIACOES)));
        avaliacao.setEvento(eventoDAO.get(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUNA_IDEVENTOAVALIACOES))));
        avaliacao.setUsuario(usuarioDAO.getByID(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUNA_IDUSERAVALIACOES))));
        avaliacao.setTipoAvaliacao(tipo);
        return avaliacao;
    }

    public List<Avaliacao> list(long idEvento) {
        ArrayList<Avaliacao> avaliacaoList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACOES
                + " WHERE " + DBHelper.COLUNA_IDEVENTOAVALIACOES + "=?";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idEvento)});
        while (cursor.moveToNext()) {
            Avaliacao checkevento = createAvaliacao(cursor);
            avaliacaoList.add(checkevento);
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
                DBHelper.COLUNA_IDUSERAVALIACOES + "=?" +
                " WHERE " + DBHelper.COLUNA_IDAVALIACOES  + "=?;";
        db.execSQL(sql, new String[]{
                String.valueOf(avaliacao.getEvento().getId()),
                String.valueOf(avaliacao.getTipoAvaliacao()),
                String.valueOf(avaliacao.getUsuario().getId()),
                String.valueOf(avaliacao.getId())
        });
        db.close();
    }

    public boolean existsByUserId(long idUser, long idEvento) {
        boolean result = false;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABELA_AVALIACOES
                + " U WHERE U." + DBHelper.COLUNA_IDEVENTOAVALIACOES + "=? AND "
                + "U." + DBHelper.COLUNA_IDUSERAVALIACOES + "=?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(idEvento), String.valueOf(idUser)});
        result = cursor.moveToFirst();
        cursor.close();
        db.close();
        return result;
    }

    public void deleteByEventoId(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] argumentos = {String.valueOf(id)};
        db.delete(DBHelper.TABELA_AVALIACOES, DBHelper.COLUNA_IDEVENTOAVALIACOES + " =?", argumentos);
        db.close();
    }

    public int countLikes(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] argumentos = {String.valueOf(id), String.valueOf(TipoAvaliacao.LIKE)};
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.TABELA_AVALIACOES
                + " WHERE " + DBHelper.COLUNA_IDEVENTOAVALIACOES + "=?"
                + " AND " + DBHelper.COLUNA_LIKE + "=?;", argumentos);
        int likes = cursor.getCount();
        cursor.close();
        db.close();
        return likes;
    }
}
