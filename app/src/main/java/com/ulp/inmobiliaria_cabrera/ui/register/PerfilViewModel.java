package com.ulp.inmobiliaria_cabrera.ui.register;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.ulp.inmobiliaria_cabrera.models.Propietario;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilViewModel extends AndroidViewModel {
    private MutableLiveData<Uri> uriMutableLiveData;
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Boolean> buttonEditEnable;
    private MutableLiveData<Boolean> buttonSaveEnable;
    private MutableLiveData<Propietario> propietarioMutableLiveData;
    private MutableLiveData<String> navigateToIdPropietario= new MutableLiveData<>();
    private MutableLiveData<Boolean> editEnabled;
    private Uri uri;
    private String uriString;
    private int ID_PROPIETARIO_LOG;
    private MutableLiveData<Boolean> loading;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        ID_PROPIETARIO_LOG = PreferencesUtil.getIdPropietario(getApplication());
        loading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void stopLoading() {
        loading.setValue(false);
    }

    public LiveData<Propietario> getCurrentUser() {
        if (propietarioMutableLiveData == null) {
            propietarioMutableLiveData = new MutableLiveData<>();
        }
        return propietarioMutableLiveData;
    }

    public LiveData<String> getNavigateIdPropietario() {
        return navigateToIdPropietario;
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

    public void setCurrentUser() {

        api.getPropietario(ID_PROPIETARIO_LOG).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                if (response.isSuccessful()) {
                    propietarioMutableLiveData.setValue(response.body());

                } else {
                    Toast.makeText(getApplication(), "Error al obtener el propietario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable throwable) {
                Log.e("Error failure", throwable.getMessage());
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void saveChanges(Propietario p) {
        loading.setValue(true);
        api.actualizarPropietario(p).enqueue(new Callback<Propietario>() {
            @Override
            public void onResponse(Call<Propietario> call, Response<Propietario> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    propietarioMutableLiveData.setValue(p);
                    Toast.makeText(getApplication(), "Datos guardados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("ProfileViewModel", "Error al guardar los datos: " + call.request().body());
                    Toast.makeText(getApplication(), "Error al guardar los datos", Toast.LENGTH_SHORT).show();
                }
                buttonSaveEnable.setValue(Boolean.FALSE);
                buttonEditEnable.setValue(Boolean.TRUE);
                editEnabled.setValue(false);
            }

            @Override
            public void onFailure(Call<Propietario> call, Throwable t) {
                Log.e("Error failure", t.getMessage());
                loading.setValue(false);
                Toast.makeText(getApplication(), "Error de conexión", Toast.LENGTH_SHORT).show();
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

    public void changePassword() {
       if (getCurrentUser().getValue() != null){
           Log.d("Salida token", getCurrentUser().getValue().toString());

           Propietario propietario = getCurrentUser().getValue();
           if (propietario != null) {
               navigateToIdPropietario.setValue(String.valueOf(propietario.getId()));
           }
       }
    }


}
