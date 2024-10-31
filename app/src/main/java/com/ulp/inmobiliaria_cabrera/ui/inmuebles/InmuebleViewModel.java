package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.app.Application;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {

    private ApiClient.InmobiliariaService api;
    private MutableLiveData<List<Inmueble>> listaInmuebles;
    private MutableLiveData<Boolean> loading;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        loading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public LiveData<List<Inmueble>> getListaInmuebles(){
        if(listaInmuebles == null){
            listaInmuebles = new MutableLiveData<>();
        }
        return listaInmuebles;
    }

    public void setListaInmuebles(int idPropeitario){
        loading.setValue(true);
        api.getInmueblesByPropietarioId(idPropeitario).enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    listaInmuebles.setValue(response.body());
                } else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                    // Token no válido, redirige a la pantalla de login
                    Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                    PreferencesUtil.redirectToLogin(getApplication());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                loading.setValue(false);
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


}