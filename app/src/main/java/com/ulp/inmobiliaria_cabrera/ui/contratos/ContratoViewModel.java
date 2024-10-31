package com.ulp.inmobiliaria_cabrera.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliaria_cabrera.models.Contrato;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoViewModel extends AndroidViewModel {

    private static final boolean ES_VIGENTE = true;
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<List<Contrato>> listContratosLiveData;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
    }

    public MutableLiveData<List<Contrato>> getListContratosLiveData() {
        if (listContratosLiveData == null) {
            listContratosLiveData = new MutableLiveData<>();
        }
        return listContratosLiveData;
    }

    public void setListContratosLiveData() {
        api.getContratosVigentes(ES_VIGENTE).enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                if (response.isSuccessful()) {
                    listContratosLiveData.setValue(response.body());
                } else if (response.code() == 401) {
                    // Token no válido, redirige a la pantalla de login
                    Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                    PreferencesUtil.redirectToLogin(getApplication());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener Contratos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable throwable) {
                //avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}