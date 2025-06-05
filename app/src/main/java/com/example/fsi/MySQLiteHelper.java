package com.example.fsi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "fsi.db";
    private static final int DATABASE_VERSION = 10;

    public static final String TABLE_UTILISATEUR = "Utilisateur";
    public static final String COLUMN_ID = "idUti";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_MDP = "motdepasse";

    private static final String CREATE_UTILISATEUR = "CREATE TABLE " + TABLE_UTILISATEUR + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY, " +
            COLUMN_LOGIN + " TEXT, " +
            COLUMN_MDP + " TEXT, " +
            "nomUti TEXT, preUti TEXT, mailUti TEXT, telUti TEXT, " +
            "adrUti TEXT, vilUti TEXT, cpUti TEXT, " +
            "nomEnt TEXT, nomMaitapp TEXT, nomTut TEXT);";

    // Table Bilan adaptée à toutes les colonnes nécessaires
    private static final String CREATE_BILAN = "CREATE TABLE Bilan (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idUti INTEGER, " +
            "type INTEGER, " +
            "notdoss REAL, " +
            "notent REAL, " +
            "notor REAL, " +
            "moy REAL, " +
            "remarque TEXT, " +
            "sujmemo TEXT, " +
            "datevisite TEXT" +
            ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_UTILISATEUR);
        db.execSQL(CREATE_BILAN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UTILISATEUR);
        db.execSQL("DROP TABLE IF EXISTS Bilan");
        onCreate(db);
    }
}
