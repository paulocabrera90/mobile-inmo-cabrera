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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContratoDetalleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;

    private MutableLiveData<Contrato> contratoMutableLiveData;
    private MutableLiveData<Boolean> pagosEnabled;
    private MutableLiveData<Boolean> loading;

    public ContratoDetalleViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        pagosEnabled = new MutableLiveData<>(false);
        loading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void stopLoading() {
        loading.setValue(false);
    }

    public LiveData<Boolean> getPagosEnabled() {
        if (pagosEnabled == null) {
            pagosEnabled = new MutableLiveData<>();
        }
        return pagosEnabled;
    }

    public LiveData<Contrato> getContratoLiveData() {
        if (contratoMutableLiveData == null) {
            contratoMutableLiveData = new MutableLiveData<>();
        }
        return contratoMutableLiveData;
    }
    public void enablePagos() {
        pagosEnabled.setValue(true);
    }

    public void setContratoMutableLiveData(int id){
        loading.setValue(true);
            api.getContrato(id).enqueue(new Callback<Contrato>() {
                @Override
                public void onResponse(Call<Contrato> call, Response<Contrato> response) {
                    loading.setValue(false);
                    if (response.isSuccessful()) {
                        contratoMutableLiveData.setValue(response.body());
                    } else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                        // Token no válido, redirige a la pantalla de login
                        Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                        PreferencesUtil.redirectToLogin(getApplication());
                    } else {
                        Toast.makeText(getApplication(), "Error al obtener Contrato", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Contrato> call, Throwable throwable) {
                    loading.setValue(false);
                    Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
    }

}