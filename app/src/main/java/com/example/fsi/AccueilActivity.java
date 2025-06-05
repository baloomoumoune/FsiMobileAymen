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

public class AccueilActivity extends AppCompatActivity {
    private TextView welcomeMessage;
    private ImageButton btnDeconnexion;
    private Button btnMesInformations, btnMesNotes;
    private int idUti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accueil_activity);

        idUti = getIntent().getIntExtra("idUti", -1);

        welcomeMessage = findViewById(R.id.welcomeMessage);
        btnDeconnexion = findViewById(R.id.btnDeconnexion);
        btnMesInformations = findViewById(R.id.btnMesInformations);
        btnMesNotes = findViewById(R.id.btnMesNotes);

        // Appel API pour récupérer le nom
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<UserInfoResponse> call = apiService.getUserInfo("getUserInfo", idUti);

        call.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
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

        btnDeconnexion.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, ConnexionActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        btnMesInformations.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, InformationsActivity.class);
            intent.putExtra("idUti", idUti);
            startActivity(intent);
        });

        btnMesNotes.setOnClickListener(v -> {
            Intent intent = new Intent(AccueilActivity.this, NotesActivity.class);
            intent.putExtra("idUti", idUti);
            startActivity(intent);
        });
    }

    public static class UserInfoResponse {
        public String nomUti, preUti, mailUti, telUti, adrUti, vilUti, cpUti;
    }
}
