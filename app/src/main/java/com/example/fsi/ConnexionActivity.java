package com.example.fsi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConnexionActivity extends AppCompatActivity {
    private EditText editLogin, editPassword;
    private Button btnConnexion;
    private ImageButton togglePassword;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connexion_activity);

        editLogin = findViewById(R.id.login);
        editPassword = findViewById(R.id.password);
        btnConnexion = findViewById(R.id.btnConnexion);
        togglePassword = findViewById(R.id.togglePassword);

        togglePassword.setOnClickListener(v -> {
            if (isPasswordVisible) {
                // Masquer le mot de passe
                editPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye_closed);
            } else {
                // Afficher le mot de passe
                editPassword.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                togglePassword.setImageResource(R.drawable.ic_eye_open);
            }
            // Remet le curseur à la fin
            editPassword.setSelection(editPassword.getText().length());
            isPasswordVisible = !isPasswordVisible;
        });


        btnConnexion.setOnClickListener(v -> {
            String login = editLogin.getText().toString();
            String mdp = editPassword.getText().toString();

            ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
            Call<ApiResponse> call = apiService.login("login", login, mdp);

            call.enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    Log.d("API_DEBUG", "Réponse login JSON: " + new Gson().toJson(response.body()));
                    try {
                        Log.d("API_DEBUG", "Response raw body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                        int idUti = response.body().getIdUti();

                        // ➤ Récupération des infos utilisateur
                        Call<AccueilActivity.UserInfoResponse> infoCall = apiService.getUserInfo("getUserInfo", idUti);
                        infoCall.enqueue(new Callback<AccueilActivity.UserInfoResponse>() {
                            @Override
                            public void onResponse(Call<AccueilActivity.UserInfoResponse> call, Response<AccueilActivity.UserInfoResponse> response) {
                                if (response.isSuccessful() && response.body() != null) {
                                    AccueilActivity.UserInfoResponse info = response.body();

                                    // ➤ Ensuite, entreprise + maître
                                    Call<EntrepriseResponse> entCall = apiService.getEntrepriseMaitre("getEntrepriseMaitre", idUti);
                                    entCall.enqueue(new Callback<EntrepriseResponse>() {
                                        @Override
                                        public void onResponse(Call<EntrepriseResponse> call, Response<EntrepriseResponse> responseEnt) {
                                            if (responseEnt.isSuccessful() && responseEnt.body() != null) {
                                                EntrepriseResponse ent = responseEnt.body();

                                                // ➤ Puis le tuteur
                                                Call<TuteurResponse> tutCall = apiService.getTuteur("getTuteur", idUti);
                                                tutCall.enqueue(new Callback<TuteurResponse>() {
                                                    @Override
                                                    public void onResponse(Call<TuteurResponse> call, Response<TuteurResponse> responseTut) {
                                                        if (responseTut.isSuccessful() && responseTut.body() != null) {
                                                            TuteurResponse tut = responseTut.body();

                                                            // ➤ Insertion dans SQLite
                                                            Utilisateur utilisateur = new Utilisateur(idUti, login, mdp,
                                                                    info.nomUti, info.preUti, info.mailUti, info.telUti,
                                                                    info.adrUti, info.vilUti, info.cpUti,
                                                                    ent.nomEnt, ent.nomMaitapp, ent.preMaitapp, tut.nomTut);

                                                            UtilisateurDataSource dataSource = new UtilisateurDataSource(ConnexionActivity.this);
                                                            dataSource.open();
                                                            dataSource.insertUtilisateur(utilisateur);
                                                            dataSource.close();

                                                            // ➤ Charger les bilans
                                                            chargerBilansDepuisAPI(apiService, idUti);
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<TuteurResponse> call, Throwable t) {
                                                        Toast.makeText(ConnexionActivity.this, "Erreur Tuteur", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<EntrepriseResponse> call, Throwable t) {
                                            Toast.makeText(ConnexionActivity.this, "Erreur Entreprise", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onFailure(Call<AccueilActivity.UserInfoResponse> call, Throwable t) {
                                Toast.makeText(ConnexionActivity.this, "Erreur Infos utilisateur", Toast.LENGTH_SHORT).show();
                            }
                        });

                    } else {
                        Toast.makeText(ConnexionActivity.this, "Identifiants incorrects", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    Toast.makeText(ConnexionActivity.this, "Erreur API: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }

    private void chargerBilansDepuisAPI(ApiService apiService, int idUti) {
        BilanDataSource bilanDS = new BilanDataSource(this);
        bilanDS.open();

        apiService.getBilan1("getBilan1", idUti).enqueue(new Callback<List<Bilan>>() {
            @Override
            public void onResponse(Call<List<Bilan>> call, Response<List<Bilan>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Bilan b : response.body()) {
                        Bilan bilanComplet = new Bilan(idUti, 1,
                                b.getNotdoss(), b.getNotent(), b.getNotor(),
                                b.getMoy(), b.getRemarque(), null, b.getDatevisite());
                        bilanDS.insertBilan(bilanComplet);
                    }
                }

                apiService.getBilan2("getBilan2", idUti).enqueue(new Callback<List<Bilan>>() {
                    @Override
                    public void onResponse(Call<List<Bilan>> call, Response<List<Bilan>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            for (Bilan b : response.body()) {
                                Bilan bilanComplet = new Bilan(idUti, 2,
                                        b.getNotdoss(), null, b.getNotor(),
                                        b.getMoy(), b.getRemarque(), b.getSujmemo(), b.getDatevisite());
                                bilanDS.insertBilan(bilanComplet);
                            }
                        }
                        bilanDS.close();
                        Intent intent = new Intent(ConnexionActivity.this, AccueilActivity.class);
                        intent.putExtra("idUti", idUti);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<List<Bilan>> call, Throwable t) {
                        Log.e("API", "Erreur getBilan2", t);
                        bilanDS.close();
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Bilan>> call, Throwable t) {
                Log.e("API", "Erreur getBilan1", t);
                bilanDS.close();
            }
        });
    }
}
