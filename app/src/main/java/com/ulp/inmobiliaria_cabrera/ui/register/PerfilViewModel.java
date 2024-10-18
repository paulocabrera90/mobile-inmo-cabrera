package com.ulp.inmobiliaria_cabrera.ui.register;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
    private MutableLiveData<Integer> buttonDateVisibility;
    private MutableLiveData<Propietario> pMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;
    private MutableLiveData<String> msgMutableLiveData;
    private MutableLiveData<Integer> msgVisibility;
    private Uri uri;
    private String uriString;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
    }
    public void setCurrentUser() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        int id = Integer.parseInt(sharedPreferences.getString("id", null));

        api.getPropietario(id).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    pMutableLiveData.setValue(response.body());
                } else {
                    msgMutableLiveData.setValue("Error al obtener el propietario");
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                msgMutableLiveData.setValue("Error de conexión");
            }
        });
    }

    public LiveData<Propietario> getCurrentUser() {
        if (pMutableLiveData == null) {
            pMutableLiveData = new MutableLiveData<>();
        }
        return pMutableLiveData;
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
