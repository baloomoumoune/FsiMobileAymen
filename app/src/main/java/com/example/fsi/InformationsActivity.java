package com.example.fsi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class InformationsActivity extends AppCompatActivity {

    private TextView txtNomEtudiant, txtPrenomEtudiant, txtEmailEtudiant, txtTelEtudiant,
            txtAdresseEtudiant, txtVilleEtudiant, txtCodePostalEtudiant,
            txtNomEntreprise, txtMaitreApprentissage, txtNomEcole, txtTuteurEcole;

    private ImageButton btnRetour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.informations_activity);

        int idUti = getIntent().getIntExtra("idUti", -1);

        // Lier les vues
        btnRetour = findViewById(R.id.btnRetour);
        txtNomEtudiant = findViewById(R.id.txtNomEtudiant);
        txtPrenomEtudiant = findViewById(R.id.txtPrenomEtudiant);
        txtEmailEtudiant = findViewById(R.id.txtEmailEtudiant);
        txtTelEtudiant = findViewById(R.id.txtTelEtudiant);
        txtAdresseEtudiant = findViewById(R.id.txtAdresseEtudiant);
        txtVilleEtudiant = findViewById(R.id.txtVilleEtudiant);
        txtCodePostalEtudiant = findViewById(R.id.txtCodePostalEtudiant);
        txtNomEntreprise = findViewById(R.id.txtNomEntreprise);
        txtMaitreApprentissage = findViewById(R.id.txtMaitreApprentissage);
        txtNomEcole = findViewById(R.id.txtNomEcole);
        txtTuteurEcole = findViewById(R.id.txtTuteurEcole);

        // Bouton retour vers l'accueil
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(InformationsActivity.this, AccueilActivity.class);
            intent.putExtra("idUti", idUti);
            startActivity(intent);
            finish();
        });

        // Charger les infos utilisateur depuis SQLite
        MySQLiteHelper helper = new MySQLiteHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Utilisateur WHERE idUti = ?", new String[]{String.valueOf(idUti)});

        if (cursor.moveToFirst()) {
            txtNomEtudiant.setText("Nom : " + cursor.getString(cursor.getColumnIndexOrThrow("nomUti")));
            txtPrenomEtudiant.setText("Prénom : " + cursor.getString(cursor.getColumnIndexOrThrow("preUti")));
            txtEmailEtudiant.setText("Email : " + cursor.getString(cursor.getColumnIndexOrThrow("mailUti")));
            txtTelEtudiant.setText("Téléphone : " + cursor.getString(cursor.getColumnIndexOrThrow("telUti")));
            txtAdresseEtudiant.setText("Adresse : " + cursor.getString(cursor.getColumnIndexOrThrow("adrUti")));
            txtVilleEtudiant.setText("Ville : " + cursor.getString(cursor.getColumnIndexOrThrow("vilUti")));
            txtCodePostalEtudiant.setText("Code Postal : " + cursor.getString(cursor.getColumnIndexOrThrow("cpUti")));
            txtNomEntreprise.setText("Nom de l'entreprise : " + cursor.getString(cursor.getColumnIndexOrThrow("nomEnt")));
            txtMaitreApprentissage.setText("Maître d'apprentissage : " + cursor.getString(cursor.getColumnIndexOrThrow("nomMaitapp")));
            txtNomEcole.setText("Nom de l'école : ORT");
            txtTuteurEcole.setText("Tuteur : " + cursor.getString(cursor.getColumnIndexOrThrow("nomTut")));

        }

        cursor.close();
        db.close();
    }
}
