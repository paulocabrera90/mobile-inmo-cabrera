package com.ulp.inmobiliaria_cabrera.ui.contratos.pagos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.models.Pago;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosDetalleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;

    private MutableLiveData<Pago> pagoMutableLiveData;

    public PagosDetalleViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
    }

    public LiveData<Pago> getPagoMutableLiveData() {
        if (pagoMutableLiveData == null) {
            pagoMutableLiveData = new MutableLiveData<>();
        }
        return pagoMutableLiveData;
    }

    public void setPagoMutableLiveData(int id){
        api.getPago(id).enqueue(new Callback<Pago>() {
            @Override
            public void onResponse(Call<Pago> call, Response<Pago> response) {
                if (response.isSuccessful()) {
                    pagoMutableLiveData.setValue(response.body());
                } else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                    // Token no válido, redirige a la pantalla de login
                    Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                    PreferencesUtil.redirectToLogin(getApplication());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener Pago", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Pago> call, Throwable throwable) {
                //   avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

}