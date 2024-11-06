package com.ulp.inmobiliaria_cabrera.ui.inquilinos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {

    private ApiClient.InmobiliariaService api;
    private MutableLiveData<List<Inmueble>> listInmuebleContratoMutable;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<Boolean> avisoListInquilino;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        loading = new MutableLiveData<>(false);
        avisoListInquilino = new MutableLiveData<>(true);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void stopLoading() {
        loading.setValue(false);
    }

    public LiveData<List<Inmueble>> getListInmuebleContratoMutable(){
        if(listInmuebleContratoMutable == null){
            listInmuebleContratoMutable = new MutableLiveData<>();
        }
        return listInmuebleContratoMutable;
    }

    public LiveData<Boolean> getAvisoInquilinoData() {
        return avisoListInquilino;
    }

    public void setListInmuebleContratoMutable() {
        loading.setValue(true);
        api.getInmueblesByPropietarioIdWithContracts().enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    listInmuebleContratoMutable.setValue(response.body());
                    avisoListInquilino.setValue(response.body().isEmpty());
                } else {
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener Inquilinos", Toast.LENGTH_SHORT).show();

                    avisoListInquilino.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                loading.setValue(false);
                avisoListInquilino.setValue(true);
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}