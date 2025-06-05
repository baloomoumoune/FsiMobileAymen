package com.example.fsi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class BilanDataSource {
    private SQLiteDatabase db;
    private MySQLiteHelper helper;

    public BilanDataSource(Context context) {
        helper = new MySQLiteHelper(context);
    }

    public void open() {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public void insertBilan(Bilan bilan) {
        ContentValues values = new ContentValues();
        values.put("idUti", bilan.getIdUti());
        values.put("type", bilan.getType());
        values.put("notdoss", bilan.getNotdoss());
        values.put("notent", bilan.getNotent());
        values.put("notor", bilan.getNotor());
        values.put("moy", bilan.getMoy());
        values.put("remarque", bilan.getRemarque());
        values.put("sujmemo", bilan.getSujmemo());
        values.put("datevisite", bilan.getDatevisite());

        db.insert("Bilan", null, values);
    }

    public List<Bilan> getBilansByType(int idUti, int type) {
        List<Bilan> bilans = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM Bilan WHERE idUti = ? AND type = ?",
                new String[]{String.valueOf(idUti), String.valueOf(type)});

        if (cursor.moveToFirst()) {
            do {
                Bilan bilan = new Bilan(
                        idUti,
                        type,
                        cursor.getDouble(cursor.getColumnIndexOrThrow("notdoss")),
                        cursor.isNull(cursor.getColumnIndexOrThrow("notent")) ? null : cursor.getDouble(cursor.getColumnIndexOrThrow("notent")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("notor")),
                        cursor.getDouble(cursor.getColumnIndexOrThrow("moy")),
                        cursor.getString(cursor.getColumnIndexOrThrow("remarque")),
                        cursor.getString(cursor.getColumnIndexOrThrow("sujmemo")),
                        cursor.getString(cursor.getColumnIndexOrThrow("datevisite"))
                );
                bilans.add(bilan);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return bilans;
    }
}
