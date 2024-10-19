package com.ulp.inmobiliaria_cabrera.request;

import static com.ulp.inmobiliaria_cabrera.constants.Constants.URL_BASE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.inmobiliaria_cabrera.models.Propietario;
import com.ulp.inmobiliaria_cabrera.request.response.LoginResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public class ApiClient {
    public static final String TOKEN = "token";
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

                    String token = sharedPreferences.getString(TOKEN, null);
                    if (token != null) {
                        requestBuilder.header("Authorization", "Bearer " + token);
                    }

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
            //Gson gson = new GsonBuilder().setLenient().create();
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create(gson))
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
        editor.putString(TOKEN, body.tokenSession);
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
        editor.remove(TOKEN);
        editor.apply();
    }

    public interface  InmobiliariaService {
        @POST("authentication/login")
        public Call<LoginResponse> login(@Body LoginView login);

        //PROPIETARIO
        @GET("propietarios")
        Call<List<Propietario>> getPropietarios();

        @GET("propietarios/{id}")
        Call<Propietario> getPropietario(@Path("id") int id);

        @POST("propietarios")
        Call<Propietario> crearPropietario(@Body Propietario propietario);

        // El que va a utilizar el propietario logueado en la app
//        @PUT("propietarios/{id}")
//        Call<Propietario> actualizarPropietario(@Body Propietario propietario);

        @PUT("propietarios/{id}")
        Call<Propietario> actualizarPropietario(@Path("id") int id, @Body Propietario propietario);

        @DELETE("propietarios/{id}")
        Call<Void> eliminarPropietario(@Path("id") int id);
    }
}