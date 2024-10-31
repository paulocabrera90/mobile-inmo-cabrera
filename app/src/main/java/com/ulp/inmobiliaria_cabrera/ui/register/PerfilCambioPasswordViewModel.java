package com.ulp.inmobiliaria_cabrera.ui.register;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.ChangePasswordRequest;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import retrofit2.Response;

public class PerfilCambioPasswordViewModel extends AndroidViewModel {
    private MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateBack = new MutableLiveData<>();
    private ApiClient.InmobiliariaService api;
    private final int ID_PROPIETARIO_LOG;
    private MutableLiveData<Boolean> loading;

    public PerfilCambioPasswordViewModel(@NonNull Application application) {
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
    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }
    public LiveData<Boolean> getNavigateBack() {
        return navigateBack;
    }

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        loading.setValue(true);
        ChangePasswordRequest changePasswordView =
                new ChangePasswordRequest(ID_PROPIETARIO_LOG, currentPassword, newPassword);

        if (newPassword.equals(confirmPassword)) {
            api.changePassword(changePasswordView).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    loading.setValue(false);
                    if (response.isSuccessful()) {
                        statusMessage.postValue("Cambio de contraseña exitoso.");
                        navigateBack.postValue(true);
                    } else {
                        statusMessage.postValue("Error en el cambio de contraseña.");
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    statusMessage.postValue("Error de conexión.");
                }
            });
        } else {
            statusMessage.postValue("Las contraseñas no coinciden.");
            loading.setValue(false);
        }

    }
}