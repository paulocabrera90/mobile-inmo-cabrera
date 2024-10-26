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
import com.ulp.inmobiliaria_cabrera.models.TipoInmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmuebleUso;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Boolean> buttonEditEnable;
    private MutableLiveData<Boolean> buttonSaveEnable;
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<List<TipoInmuebleUso>> listTipoInmuebleUsoMutableLiveData;
    private MutableLiveData<List<TipoInmueble>> listTipoinmuebleMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;
    private int ID_PROPIETARIO_LOG;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);

        api = ApiClient.getInmobiliariaService(application.getApplicationContext());

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        ID_PROPIETARIO_LOG =Integer.parseInt(sharedPreferences.getString("id", null));

        buttonEditEnable = new MutableLiveData<>(true);
        buttonSaveEnable = new MutableLiveData<>(false);
        editEnabled = new MutableLiveData<>(false);

    }

    public LiveData<Boolean> getBtnEditEnable() {
        if (buttonEditEnable == null) {
            buttonEditEnable = new MutableLiveData<>();
        }
        return buttonEditEnable;
    }

    public LiveData<Boolean> getBtnSaveEnable() {
        if (buttonSaveEnable == null) {
            buttonSaveEnable = new MutableLiveData<>();
        }
        return buttonSaveEnable;
    }

    public LiveData<Boolean> getEditEnabled() {
        if (editEnabled == null) {
            editEnabled = new MutableLiveData<>();
        }
        return editEnabled;
    }

    public LiveData<Inmueble> getInmueble() {
        if (inmuebleMutableLiveData == null) {
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }

    public LiveData<List<TipoInmuebleUso>> getTipoInmuebleUso() {
        if (listTipoInmuebleUsoMutableLiveData == null) {
            listTipoInmuebleUsoMutableLiveData = new MutableLiveData<List<TipoInmuebleUso>>();
        }
        return listTipoInmuebleUsoMutableLiveData;
    }

    public LiveData<List<TipoInmueble>> getTipoInmueble() {
        if (listTipoinmuebleMutableLiveData == null) {
            listTipoinmuebleMutableLiveData = new MutableLiveData<List<TipoInmueble>>();
        }
        return listTipoinmuebleMutableLiveData;
    }

    public void enableEdit() {
        this.buttonEditEnable.setValue(Boolean.FALSE);
        this.buttonSaveEnable.setValue(Boolean.TRUE);
        this.editEnabled.setValue(true);
    }

    public void setInmueble(int idInmueble){
        api.getInmueble(idInmueble).enqueue(new Callback<Inmueble>() {
            @Override
            public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                if (response.isSuccessful()) {
                    inmuebleMutableLiveData.setValue(response.body());
                    Toast.makeText(getApplication().getApplicationContext(), "Inmuebles cargados", Toast.LENGTH_SHORT).show();
                } else {
                   // avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Inmueble> call, Throwable throwable) {
             //   avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setTipoInmueble(){
        api.getTipoInmuebles().enqueue(new Callback<List<TipoInmueble>>() {
            @Override
            public void onResponse(Call<List<TipoInmueble>> call, Response<List<TipoInmueble>> response) {
                if (response.isSuccessful()) {
                    listTipoinmuebleMutableLiveData.setValue(response.body());
                } else {
                    // avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener listado de tipo inmueble", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoInmueble>> call, Throwable throwable) {
                //   avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setTipoInmuebleUso(){
        api.getTipoInmueblesUso().enqueue(new Callback<List<TipoInmuebleUso>>() {
            @Override
            public void onResponse(Call<List<TipoInmuebleUso>> call, Response<List<TipoInmuebleUso>> response) {
                if (response.isSuccessful()) {
                    listTipoInmuebleUsoMutableLiveData.setValue(response.body());
                } else {
                    // avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener listado de tipo inmueble usos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoInmuebleUso>> call, Throwable throwable) {
                //   avisoMutable.setValue("Error de conexión");
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }
}