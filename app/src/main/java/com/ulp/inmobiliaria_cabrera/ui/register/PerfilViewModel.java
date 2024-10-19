package com.ulp.inmobiliaria_cabrera.ui.register;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.models.Propietario;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;
    private MutableLiveData<Uri> uriMutableLiveData;
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Boolean> buttonEditEnable;
    private MutableLiveData<Boolean> buttonSaveEnable;
    private MutableLiveData<Propietario> propietarioMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;
    private Uri uri;
    private String uriString;
    private int ID_PROPIETARIO;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        ID_PROPIETARIO =Integer.parseInt(sharedPreferences.getString("id", null));
    }
    public void setCurrentUser() {

        api.getPropietario(ID_PROPIETARIO).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietarioMutableLiveData.setValue(response.body());
                } else {
                    avisoMutable.setValue("Error al obtener el propietario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                avisoMutable.setValue("Error de conexión");
            }
        });
    }

    public LiveData<Propietario> getCurrentUser() {
        if (propietarioMutableLiveData == null) {
            propietarioMutableLiveData = new MutableLiveData<>();
        }
        return propietarioMutableLiveData;
    }

    public LiveData<Boolean> getBtnEditVisibility() {
        if (buttonEditEnable == null) {
            buttonEditEnable = new MutableLiveData<>();
        }
        return buttonEditEnable;
    }

    public LiveData<Boolean> getBtnSaveVisibility() {
        if (buttonSaveEnable == null) {
            buttonSaveEnable = new MutableLiveData<>();
        }
        return buttonSaveEnable;
    }

    public LiveData<String> getAvisoMutable() {
        if (avisoMutable == null) {
            avisoMutable = new MutableLiveData<>();
        }
        return avisoMutable;
    }

    public LiveData<Integer> getAvisoVisibilityMutable() {
        if (avisoVisibilityMutable == null) {
            avisoVisibilityMutable = new MutableLiveData<>();
        }
        return avisoVisibilityMutable;
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public LiveData<Boolean> getEditEnabled() {
        if (editEnabled == null) {
            editEnabled = new MutableLiveData<>();
        }
        return editEnabled;
    }

    public void enableEdit() {
        this.buttonEditEnable.setValue(Boolean.FALSE);
        this.buttonSaveEnable.setValue(Boolean.TRUE);
        this.editEnabled.setValue(true);
    }

    public void saveChanges(Propietario p) {
        if(p.getId() == 0){
            p.setId(ID_PROPIETARIO);
        }

        api.actualizarPropietario(ID_PROPIETARIO, p).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietarioMutableLiveData.setValue(p);
                    avisoMutable.setValue("Datos guardados.");
                } else {
                    Log.d("ProfileViewModel", "Error al guardar los datos: " + call.request().body());
                    avisoMutable.setValue("Error al guardar los datos");
                }
                buttonSaveEnable.setValue(Boolean.FALSE);
                buttonEditEnable.setValue(Boolean.TRUE);
                editEnabled.setValue(false);
                avisoVisibilityMutable.setValue(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                avisoMutable.setValue("Error de conexión");
            }
        });
    }

    public Uri getDefaultImageUri() {
        return Uri.EMPTY;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                uri = data.getData();
                uriString = uri.toString();
                uriMutableLiveData.setValue(uri);
            }
        }
    }
}
