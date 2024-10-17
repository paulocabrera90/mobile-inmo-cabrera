package com.ulp.inmobiliaria_cabrera.request;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.inmobiliaria_cabrera.request.response.LoginResponse;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

public class ApiClient {
    public static final String URL_BASE = "http://192.168.1.181:5000/api/";
    private static InmobiliariaService inmobiliariaService;
    private static SharedPreferences sharedPreferences;

    // Configuración básica de Retrofit con Interceptor para JWT
    public static InmobiliariaService getInmobiliariaService(Context context) {
        if (inmobiliariaService == null) {
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request originalRequest = chain.request();
                    Request.Builder requestBuilder = originalRequest.newBuilder();

                    String token = sharedPreferences.getString("tokenSession", null);
                    if (token != null) {
                        requestBuilder.header("Authorization", "Bearer " + token);
                    }

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            //Gson gson = new GsonBuilder().setLenient().create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            inmobiliariaService = retrofit.create(InmobiliariaService.class);
        }
        return inmobiliariaService;
    }

    // Método para guardar el token en SharedPreferences
    public static void guardarToken(Context context, LoginResponse body) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", body.tokenSession);
        editor.putString("nombreCompleto", body.data.getNombreCompleto());
        editor.putString("email", body.data.getEmail());
        editor.putString("id", String.valueOf(body.data.getId()));


        editor.apply();
    }

    // Método para eliminar el token de SharedPreferences
    public static void eliminarToken(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("token");
        editor.apply();
    }



    public interface  InmobiliariaService {
        @POST("authentication/login")
        public Call<LoginResponse> login(@Body LoginView login);
    }
}