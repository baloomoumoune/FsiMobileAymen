package com.example.fsi;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class UtilisateurDataSource {
    private SQLiteDatabase db;
    private MySQLiteHelper helper;

    public UtilisateurDataSource(Context context) {
        helper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        db = helper.getWritableDatabase();
    }

    public void close() {
        helper.close();
    }

    public void insertUtilisateur(Utilisateur utilisateur) {
        // Nettoyage avant insertion
        db.delete("Utilisateur", null, null);

        ContentValues values = new ContentValues();
        values.put("idUti", utilisateur.getIdUti());
        values.put("login", utilisateur.getLogin());
        values.put("motdepasse", utilisateur.getMotdepasse());
        values.put("nomUti", utilisateur.getNomUti());
        values.put("preUti", utilisateur.getPreUti());
        values.put("mailUti", utilisateur.getMailUti());
        values.put("telUti", utilisateur.getTelUti());
        values.put("adrUti", utilisateur.getAdrUti());
        values.put("vilUti", utilisateur.getVilUti());
        values.put("cpUti", utilisateur.getCpUti());
        values.put("nomEnt", utilisateur.getNomEnt());
        values.put("nomMaitapp", utilisateur.getNomMaitapp());
        values.put("preMaitapp", utilisateur.getPreMaitapp());
        values.put("nomTut", utilisateur.getNomTut());

        db.insert("Utilisateur", null, values);
    }
}
