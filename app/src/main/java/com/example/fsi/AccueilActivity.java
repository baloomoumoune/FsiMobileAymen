package com.example.fsi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Écran d'accueil principal de l'application après la connexion.
 * Affiche un message de bienvenue personnalisé et propose deux actions principales :
 * - Consulter ses informations personnelles
 * - Consulter ses notes de bilans
 *
 * 🔗 Liens :
 * - Appelle l’API via Retrofit pour récupérer les infos utilisateur (nom/prénom).
 * - Délègue à `InformationsActivity` et `NotesActivity` selon les boutons cliqués.
 * - Peut rediriger vers `ConnexionActivity` en cas de déconnexion.
 *
 * 🛠️ À modifier si :
 * - Tu ajoutes de nouvelles fonctionnalités sur l'écran d'accueil (ex. messagerie, statistiques).
 * - Tu veux afficher plus d'infos utilisateur (photo, prénom, etc.).
 * - Tu changes les noms ou la structure du layout XML `accueil_activity.xml`.
 * - Tu modifies l'API et que `UserInfoResponse` change de structure.
 */
public class AccueilActivity extends AppCompatActivity {
    private TextView welcomeMessage;
    private ImageButton btnDeconnexion;
    private Button btnMesInformations, btnMesNotes;
    private int idUti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);

        // Récupère l'identifiant utilisateur depuis l'intent (envoyé après le login)
        idUti = getIntent().getIntExtra("idUti", -1);

        // Liaison des éléments du layout aux objets Java
        welcomeMessage = findViewById(R.id.welcomeMessage);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);
        btnMesInformations = findViewById(R.id.btnMesInformations);
        btnMesNotes = findViewById(R.id.btnMesNotes);

        // 🔗 Appel API pour afficher le nom de l’utilisateur connecté
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<UserInfoResponse> call = apiService.getUserInfo("getUserInfo", idUti);

        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Affiche le nom reçu depuis l’API
                    welcomeMessage.setText("Bienvenue sur FSI Notes, " + response.body().nomUti);
                } else {
                    welcomeMessage.setText("Bienvenue sur FSI Notes");
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Toast.makeText(AccueilActivity.this, "Erreur chargement nom", Toast.LENGTH_SHORT).show();
                welcomeMessage.setText("Bienvenue sur FSI Notes");
            }
        });

        // 🔘 Gestion du bouton de déconnexion → retour à la page de connexion
        btnDeconnexion.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, ConnexionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // Reset navigation
            startActivity(intent);
            finish();
        });

        // 🔘 Navigation vers l’écran des informations utilisateur
        btnMesInformations.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, InformationsActivity.class);
            intent.putExtra("idUti", idUti); // Passage d’ID pour récupérer les données dans l'autre activité
            startActivity(intent);
        });

        // 🔘 Navigation vers l’écran des notes et bilans
        btnMesNotes.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, NotesActivity.class);
            intent.putExtra("idUti", idUti);
            startActivity(intent);
        });
    }

    /**
     * Modèle local représentant les données retournées par l’API pour l’utilisateur.
     * Doit correspondre exactement à ce que retourne le backend (getUserInfo).
     */
    public static class UserInfoResponse {
        public String nomUti, preUti, mailUti, telUti, adrUti, vilUti, cpUti;
    }
}
