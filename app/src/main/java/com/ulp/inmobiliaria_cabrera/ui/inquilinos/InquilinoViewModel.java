package com.ulp.inmobiliaria_cabrera.ui.inquilinos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.Inquilino;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoViewModel extends AndroidViewModel {

    private ApiClient.InmobiliariaService api;
    private MutableLiveData<List<Inmueble>> listInmuebleContratoMutable;
    private MutableLiveData<Boolean> loading;

    public InquilinoViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        loading = new MutableLiveData<>(false);
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

    public void setListInmuebleContratoMutable() {
        loading.setValue(true);
        api.getInmueblesByPropietarioIdWithContracts().enqueue(new Callback<List<Inmueble>>() {
            @Override
            public void onResponse(Call<List<Inmueble>> call, Response<List<Inmueble>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    //propietarioMutableLiveData.setValue(response.body());
                      listInmuebleContratoMutable.setValue(response.body());
                } else {
                    //avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener Inquilinos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Inmueble>> call, Throwable throwable) {
                loading.setValue(false);
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}