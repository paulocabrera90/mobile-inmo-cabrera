package com.ulp.inmobiliaria_cabrera.ui.contratos.pagos;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.models.Contrato;
import com.ulp.inmobiliaria_cabrera.models.Pago;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PagosViewModel extends AndroidViewModel {

    private ApiClient.InmobiliariaService api;
    private MutableLiveData<List<Pago>> listPagosLiveData;

    public PagosViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
    }

    public MutableLiveData<List<Pago>> getListPagosLiveData() {
        if (listPagosLiveData == null) {
            listPagosLiveData = new MutableLiveData<>();
        }
        return listPagosLiveData;
    }

    public void setListPagosLiveData(int idContrato) {
        api.getPagosByContrato(idContrato).enqueue(new Callback<List<Pago>>() {
            @Override
            public void onResponse(Call<List<Pago>> call, Response<List<Pago>> response) {
                if (response.isSuccessful()) {
                    listPagosLiveData.setValue(response.body());
                } else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                    // Token no válido, redirige a la pantalla de login
                    Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                    PreferencesUtil.redirectToLogin(getApplication());
                } else {
                    Toast.makeText(getApplication(), "Error al obtener Pagos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Pago>> call, Throwable throwable) {
                //avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
}