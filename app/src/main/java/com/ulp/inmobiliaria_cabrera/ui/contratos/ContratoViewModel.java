package com.ulp.inmobiliaria_cabrera.ui.contratos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.models.Contrato;
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
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<Boolean> avisoListIContrato;

    public ContratoViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        loading = new MutableLiveData<>(false);
        avisoListIContrato = new MutableLiveData<>(true);
    }

    public LiveData<Boolean> getAvisoListContrato() {
        return avisoListIContrato;
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void stopLoading() {
        loading.setValue(false);
    }

    public MutableLiveData<List<Contrato>> getListContratosLiveData() {
        if (listContratosLiveData == null) {
            listContratosLiveData = new MutableLiveData<>();
        }
        return listContratosLiveData;
    }

    public void setListContratosLiveData() {
        loading.setValue(true);
        api.getContratosVigentes(ES_VIGENTE).enqueue(new Callback<List<Contrato>>() {
            @Override
            public void onResponse(Call<List<Contrato>> call, Response<List<Contrato>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    listContratosLiveData.setValue(response.body());
                    avisoListIContrato.setValue(response.body().isEmpty());
                } else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                    // Token no válido, redirige a la pantalla de login
                    Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                    PreferencesUtil.redirectToLogin(getApplication());
                    avisoListIContrato.setValue(true);
                } else {
                    avisoListIContrato.setValue(true);
                    Toast.makeText(getApplication(), "Error al obtener Contratos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Contrato>> call, Throwable throwable) {
                loading.setValue(false);
                avisoListIContrato.setValue(true);
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}