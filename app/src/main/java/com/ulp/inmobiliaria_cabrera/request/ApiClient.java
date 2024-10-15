package com.ulp.inmobiliaria_cabrera.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.inmobiliaria_cabrera.request.response.LoginResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiClient {
    public static final String URL_BASE = "http://192.168.1.181:5001/api/";

    public static InmobiliariaService getApiInmobiliaria(){

        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(InmobiliariaService.class);
    };


    public interface  InmobiliariaService {
        @POST("authentication/login")
        public Call<LoginResponse> login(@Body LoginView login);
    }
}