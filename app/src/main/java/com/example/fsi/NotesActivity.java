package com.example.fsi;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NotesActivity extends AppCompatActivity {

    private int idUti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notes_activity);

        idUti = getIntent().getIntExtra("idUti", -1);

        ImageButton btnRetour = findViewById(R.id.btnRetour);
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(NotesActivity.this, AccueilActivity.class);
            intent.putExtra("idUti", idUti);
            startActivity(intent);
            finish();
        });

        TextView txtNoteDossierBilan1 = findViewById(R.id.txtNoteDossierBilan1);
        TextView txtNoteOralBilan1 = findViewById(R.id.txtNoteOralBilan1);
        TextView txtNoteEntrepriseBilan1 = findViewById(R.id.txtNoteEntrepriseBilan1);
        TextView txtDateBilan1 = findViewById(R.id.txtDateBilan1);
        TextView txtMoyenneBilan1 = findViewById(R.id.txtMoyenneBilan1);
        TextView txtRemarquesBilan1 = findViewById(R.id.txtRemarquesBilan1);

        TextView txtSujetMemoireBilan2 = findViewById(R.id.txtSujetMemoireBilan2);
        TextView txtNoteDossierBilan2 = findViewById(R.id.txtNoteDossierBilan2);
        TextView txtNoteOralBilan2 = findViewById(R.id.txtNoteOralBilan2);
        TextView txtDateBilan2 = findViewById(R.id.txtDateBilan2);
        TextView txtMoyenneBilan2 = findViewById(R.id.txtMoyenneBilan2);
        TextView txtRemarquesBilan2 = findViewById(R.id.txtRemarquesBilan2);

        TextView txtMoyenneGenerale = findViewById(R.id.txtMoyenneGenerale);

        BilanDataSource ds = new BilanDataSource(this);
        ds.open();

        List<Bilan> bilan1 = ds.getBilansByType(idUti, 1);
        if (!bilan1.isEmpty()) {
            Bilan b = bilan1.get(0);
            txtNoteDossierBilan1.setText("Note dossier : " + b.getNotdoss());
            txtNoteOralBilan1.setText("Note d'oral : " + b.getNotor());
            txtNoteEntrepriseBilan1.setText("Note entreprise : " + b.getNotent());
            txtDateBilan1.setText("Date : " + b.getDatevisite());
            txtMoyenneBilan1.setText("Moyenne : " + b.getMoy());
            txtRemarquesBilan1.setText("Remarque : " + b.getRemarque());
        }

        List<Bilan> bilan2 = ds.getBilansByType(idUti, 2);
        if (!bilan2.isEmpty()) {
            Bilan b = bilan2.get(0);
            txtSujetMemoireBilan2.setText("Sujet mémoire : " + b.getSujmemo());
            txtNoteDossierBilan2.setText("Note dossier : " + b.getNotdoss());
            txtNoteOralBilan2.setText("Note orale : " + b.getNotor());
            txtDateBilan2.setText("Date : " + b.getDatevisite());
            txtMoyenneBilan2.setText("Moyenne : " + b.getMoy());
            txtRemarquesBilan2.setText("Remarque : " + b.getRemarque());
        }

        if (!bilan1.isEmpty() && !bilan2.isEmpty()) {
            float moyenneG = (float) ((bilan1.get(0).getMoy() + bilan2.get(0).getMoy()) / 2.0);
            txtMoyenneGenerale.setText("Moyenne générale : " + String.format("%.2f", moyenneG));
        } else {
            txtMoyenneGenerale.setText("Moyenne générale : N/A");
        }

        ds.close();
    }
}
