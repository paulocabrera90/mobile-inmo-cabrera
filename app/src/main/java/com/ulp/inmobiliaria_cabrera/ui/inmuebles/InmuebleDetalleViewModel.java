package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmuebleUso;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<List<TipoInmuebleUso>> listTipoInmuebleUsoMutableLiveData;
    private MutableLiveData<List<TipoInmueble>> listTipoinmuebleMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);

        api = ApiClient.getInmobiliariaService(application.getApplicationContext());

        editEnabled = new MutableLiveData<>(false);

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
        this.editEnabled.setValue(true);
    }

    public void setInmueble(int idInmueble, boolean newInmueble){
        if (!newInmueble){
            api.getInmueble(idInmueble).enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        inmuebleMutableLiveData.setValue(response.body());

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

    public void saveInmueble(Inmueble inmueble, int idInmueble){
        if (idInmueble != 0) {
            inmueble.setId(idInmueble); // TODO TENER EN CUENTA ESTO!
            api.actualizarInmueble(inmueble).enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        inmuebleMutableLiveData.setValue(inmueble);
                        //avisoMutable.setValue("Datos guardados.");
                        Toast.makeText(getApplication().getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.d("InmuebleDetalleViewModle", "Error al guardar los datos: " + call.request().body());
                        //avisoMutable.setValue("Error al guardar los datos");
                        Toast.makeText(getApplication().getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                    editEnabled.setValue(true);
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    //avisoMutable.setValue("Error de conexión");
                    Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            api.crearInmueble(inmueble).enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    if (response.isSuccessful()) {
                        inmuebleMutableLiveData.setValue(response.body());

                    } else {
                        Log.d("InmuebleDetalleViewModle", "Error al guardar los datos: " + call.request().body());
                        Toast.makeText(getApplication().getApplicationContext(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                    }
                    editEnabled.setValue(true);
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    //avisoMutable.setValue("Error de conexión");
                    Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}