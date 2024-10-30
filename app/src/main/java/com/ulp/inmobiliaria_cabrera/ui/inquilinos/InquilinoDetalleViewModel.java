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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InquilinoDetalleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Inquilino> inquilinoMutableLiveData;

    public InquilinoDetalleViewModel(@NonNull Application application) {
        super(application);

        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
    }
    public LiveData<Inquilino> getInquilinoLiveData() {
        if (inquilinoMutableLiveData == null) {
            inquilinoMutableLiveData = new MutableLiveData<>();
        }
        return inquilinoMutableLiveData;
    }

    public void setInquilinoLiveData(int idInmueble){
            api.getInquilinosByInmueble(idInmueble).enqueue(new Callback<Inquilino>() {
                @Override
                public void onResponse(Call<Inquilino> call, Response<Inquilino> response) {
                    if (response.isSuccessful()) {
                        inquilinoMutableLiveData.setValue(response.body());

                    } else {
                        // avisoMutable.setValue("Error al obtener el propietario");
                        Toast.makeText(getApplication().getApplicationContext(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Inquilino> call, Throwable throwable) {
                    //   avisoMutable.setValue("Error de conexión");
                    Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
    }

}