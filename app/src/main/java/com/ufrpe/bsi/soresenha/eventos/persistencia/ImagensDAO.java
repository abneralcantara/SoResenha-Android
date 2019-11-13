package com.ufrpe.bsi.soresenha.eventos.persistencia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ufrpe.bsi.soresenha.eventos.dominio.ImagemEvento;
import com.ufrpe.bsi.soresenha.infra.persistencia.DBHelper;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ImagensDAO {
    private DBHelper dbHelper;

    public ImagensDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    public List<ImagemEvento> getByEventoID(long id) {
        List<ImagemEvento> result = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT * FROM " + DBHelper.TABLE_IMAGEMEVENTO + " I WHERE I." + DBHelper.COLUNA_IDEVENTO + " = ?;";
        Cursor cursor = db.rawQuery(sql, new String[]{String.valueOf(id)});
        while (cursor.moveToNext()) {
            result.add(createImagem(cursor));
        }
        cursor.close();
        db.close();
        return result;
    }

    public void inserir(long eventoId, ImagemEvento img) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        byte[] byteArray = getByteFromImage(img);
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_IDEVENTO, eventoId);
        values.put(DBHelper.COLUNA_IMAGEM, byteArray);
        db.insert(DBHelper.TABLE_IMAGEMEVENTO, null, values);
        db.close();
    }

    private ImagemEvento createImagem(Cursor cursor) {
        ImagemEvento res = new ImagemEvento();
        res.setId(cursor.getLong(cursor.getColumnIndex(DBHelper.COLUNA_IDIMAGEMEVENTO)));
        byte[] image = cursor.getBlob(cursor.getColumnIndex(DBHelper.COLUNA_IMAGEM));
        res.setImagem(BitmapFactory.decodeByteArray(image, 0 , image.length));
        return res;
    }

    public void update(long id, ImagemEvento image) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUNA_IDEVENTO, id);
        values.put(DBHelper.COLUNA_IMAGEM, getByteFromImage(image));
        db.update(DBHelper.TABLE_IMAGEMEVENTO,
                values,
                DBHelper.COLUNA_IDIMAGEMEVENTO + "=?",
                new String[]{String.valueOf(image.getId())}
        );
    }


    private byte[] getByteFromImage(ImagemEvento img) {
        //https://stackoverflow.com/questions/4989182/converting-java-bitmap-to-byte-array
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.getImagem().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        img.getImagem().recycle();
        // -
        return byteArray;
    }
}
