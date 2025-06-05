package com.example.fsi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Field;

public interface ApiService {
    @FormUrlEncoded
    @POST("ApiAymen.php")
    Call<ApiResponse> login(
            @Field("action") String action,
            @Field("logUti") String login,
            @Field("mdpUti") String password
    );



    @FormUrlEncoded
    @POST("ApiAymen.php")
    Call<AccueilActivity.UserInfoResponse> getUserInfo(
            @Field("action") String action,
            @Field("idUti") int idUti
    );

    @FormUrlEncoded
    @POST("ApiAymen.php")
    Call<EntrepriseResponse> getEntrepriseMaitre(
            @Field("action") String action,
            @Field("idUti") int idUti
    );

    @FormUrlEncoded
    @POST("ApiAymen.php")
    Call<TuteurResponse> getTuteur(
            @Field("action") String action,
            @Field("idUti") int idUti
    );

    @FormUrlEncoded
    @POST("ApiAymen.php")
    Call<List<Bilan>> getBilan1(
            @Field("action") String action,
            @Field("idUti") int idUti
    );

    @FormUrlEncoded
    @POST("ApiAymen.php")
    Call<List<Bilan>> getBilan2(
            @Field("action") String action,
            @Field("idUti") int idUti
    );




}


