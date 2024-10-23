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

import retrofit2.Response;

public class PerfilCambioPasswordViewModel extends AndroidViewModel {
    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;
    private MutableLiveData<String> statusMessage = new MutableLiveData<>();
    private MutableLiveData<Boolean> navigateBack = new MutableLiveData<>();

    private ApiClient.InmobiliariaService api;
    private int ID_PROPIETARIO;

    public PerfilCambioPasswordViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());

        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        ID_PROPIETARIO = Integer.parseInt(sharedPreferences.getString("id", null));
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }
    public LiveData<Boolean> getNavigateBack() {
        return navigateBack;
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

    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        ChangePasswordRequest changePasswordView =
                new ChangePasswordRequest(ID_PROPIETARIO, currentPassword, newPassword);

        if (newPassword.equals(confirmPassword)) {
            api.changePassword(changePasswordView).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
        }

    }
}