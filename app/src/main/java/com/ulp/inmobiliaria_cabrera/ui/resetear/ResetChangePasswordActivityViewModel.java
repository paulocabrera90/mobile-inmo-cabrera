package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.ResetChangePasswordRequest;
import com.ulp.inmobiliaria_cabrera.ui.login.LoginActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetChangePasswordActivityViewModel extends AndroidViewModel {
    private Context context;
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Boolean> loading;

    public ResetChangePasswordActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();

        api = ApiClient.getInmobiliariaService(application.getApplicationContext());
        loading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void stopLoading() {
        loading.setValue(false);
    }

    public void resetPassword(String email, String newPassword, String confirmPassword, String verificationNumber) {
        loading.setValue(true);
        ResetChangePasswordRequest resetChangePasswordRequest =
                new ResetChangePasswordRequest(email, newPassword, verificationNumber);

        if (newPassword.equals(confirmPassword)) {
            api.renovePassword(resetChangePasswordRequest).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    loading.setValue(false);
                    if (response.isSuccessful()) {
                        Toast.makeText(context,
                                "Cambio de contraseña exitoso.",
                                Toast.LENGTH_SHORT)
                                .show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    } else {
                        Toast.makeText(context,
                                "Error en el cambio de contraseña.",
                                Toast.LENGTH_SHORT)
                                .show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context,
                                    "Error conexion.",
                                    Toast.LENGTH_SHORT)
                            .show();
                }
            });
        } else {
            Toast.makeText(context,
                            "Las contraseñas no coinciden.",
                            Toast.LENGTH_SHORT)
                    .show();
            loading.setValue(false);
        }

    }
}
