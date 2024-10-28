package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.Propietario;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleViewModel extends AndroidViewModel {

    private ApiClient.InmobiliariaService api;
    private MutableLiveData<List<Inmueble>> listaInmuebles;

    public InmuebleViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
    }

    public LiveData<List<Inmueble>> getListaInmuebles(){
        if(listaInmuebles == null){
            listaInmuebles = new MutableLiveData<>();
        }
        return listaInmuebles;
    }

    public void setListaInmuebles(int idPropeitario){

        api.getInmueblesByPropietarioId(idPropeitario).enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                if (response.isSuccessful()) {
                    //propietarioMutableLiveData.setValue(response.body());
                    //  Toast.makeText(getApplication().getApplicationContext(), "Inmuebles cargados", Toast.LENGTH_SHORT).show();
                    listaInmuebles.setValue(response.body());
                } else {
                    //avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                //avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}