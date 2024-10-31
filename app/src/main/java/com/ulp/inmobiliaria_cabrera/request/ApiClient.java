package com.ulp.inmobiliaria_cabrera.request;

import static com.ulp.inmobiliaria_cabrera.constants.Constants.URL_BASE;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ulp.inmobiliaria_cabrera.models.Contrato;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.Inquilino;
import com.ulp.inmobiliaria_cabrera.models.Propietario;
import com.ulp.inmobiliaria_cabrera.models.TipoInmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmuebleUso;
import com.ulp.inmobiliaria_cabrera.request.response.LoginResponse;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ApiClient {
    public static final String TOKEN = "token";
    private static InmobiliariaService inmobiliariaService;
    private static SharedPreferences sharedPreferences;

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
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .setLenient()
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

    public static void eliminarToken(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN);
        editor.apply();
    }

    public interface  InmobiliariaService {
        //LOGIN Y PASSWORD
        @POST("authentication/login")
        public Call<LoginResponse> login(@Body LoginRequest login);

        @POST("authentication/renove-password")
        Call<ResponseBody> renovePassword(@Body ResetChangePasswordRequest resetChangePasswordRequest);

        @POST("authentication/changePassword")
        public Call<ResponseBody> changePassword(@Body ChangePasswordRequest changePasswordView);

        //PROPIETARIO
        @GET("propietarios")
        Call<List<Propietario>> getPropietarios();

        @GET("propietarios/{id}")
        Call<Propietario> getPropietario(@Path("id") int id);

        @POST("propietarios")
        Call<Propietario> crearPropietario(@Body Propietario propietario);

        @PUT("propietarios/{id}")
        Call<Propietario> actualizarPropietario(@Body Propietario propietario);

        @DELETE("propietarios/{id}")
        Call<Void> eliminarPropietario(@Path("id") int id);

        @POST("propietarios/reset-password")
        Call<ResponseBody> resetPassword(@Body ResetPasswordRequest resetPassword);

        @POST("propietarios/validate-code")
        Call<Boolean> validateCode(@Body ConfirmPasswordResetRequest resetPassword);

        //INMUEBLE
        @GET("inmuebles/by-propietario/{id}")
        Call<List<Inmueble>> getInmueblesByPropietarioId(@Path("id") int id);

        @GET("inmuebles/by-propietario-with-contracts")
        Call<List<Inmueble>> getInmueblesByPropietarioIdWithContracts();

        @GET("inmuebles/{id}")
        Call<Inmueble> getInmueble(@Path("id") int id);

        @Multipart
        @POST("inmuebles")
        Call<Inmueble> crearInmueble(
                @Part("activo") RequestBody activo,
                @Part("ambientes") RequestBody ambientes,
                @Part("coordenadaLat") RequestBody coordenadaLat,
                @Part("coordenadaLon") RequestBody coordenadaLon,
                @Part("direccion") RequestBody direccion,
                @Part("fechaActualizacion") RequestBody fechaActualizacion,
                @Part("fechaCreacion") RequestBody fechaCreacion,
                @Part("idPropietario") RequestBody idPropietario,
                @Part("idTipoInmueble") RequestBody idTipoInmueble,
                @Part("idTipoInmuebleUso") RequestBody idTipoInmuebleUso,
                @Part("precio") RequestBody precio,
                @Part MultipartBody.Part image // Para la imagen, si es requerida
        );

        @Multipart
        @PUT("inmuebles")
        Call<Inmueble> actualizarInmueble(
                @Part("activo") RequestBody activo,
                @Part("ambientes") RequestBody ambientes,
                @Part("coordenadaLat") RequestBody coordenadaLat,
                @Part("coordenadaLon") RequestBody coordenadaLon,
                @Part("direccion") RequestBody direccion,
                @Part("fechaActualizacion") RequestBody fechaActualizacion,
                @Part("fechaCreacion") RequestBody fechaCreacion,
                @Part("id") RequestBody id,
                @Part("idPropietario") RequestBody idPropietario,
                @Part("idTipoInmueble") RequestBody idTipoInmueble,
                @Part("idTipoInmuebleUso") RequestBody idTipoInmuebleUso,
                @Part("precio") RequestBody precio,
                @Part MultipartBody.Part image // Para la imagen, si es requerida
        );

        //TIPOS
        @GET("tipos/tipos-inmueble")
        Call<List<TipoInmueble>> getTipoInmuebles();

        @GET("tipos/tipos-inmueble-uso")
        Call<List<TipoInmuebleUso>> getTipoInmueblesUso();

        //INQUILINO
        @GET("inquilinos")
        Call<List<Inquilino>> getInquilinos();

        @GET("inquilinos/by-inmueble/{id}")
        Call<Inquilino> getInquilinosByInmueble(@Path("id") int id);

        //CONTRATOS

        @GET("contratos/vigentes")
        Call<List<Contrato>> getContratosVigentes(
                @Query("flagVigente") boolean flagVigente
        );
        @GET("contratos/{id}")
        Call<Contrato> getContrato(@Path("id") int id);
    }
}