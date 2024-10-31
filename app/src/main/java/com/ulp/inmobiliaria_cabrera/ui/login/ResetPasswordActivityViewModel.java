package com.ulp.inmobiliaria_cabrera.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.ResetPasswordRequest;
import com.ulp.inmobiliaria_cabrera.ui.resetear.ValidateCodeResetActivity;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivityViewModel extends AndroidViewModel {
    private Context context;

    private MutableLiveData<Boolean> loading;

    public ResetPasswordActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
        loading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void stopLoading() {
        loading.setValue(false);
    }

    public void resetPassword(String email) {
        loading.setValue(true);
       Call<ResponseBody> call = ApiClient.getInmobiliariaService(context)
               .resetPassword(new ResetPasswordRequest(email));

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               loading.setValue(false);
               if(response.isSuccessful()) {
                   Intent intent = new Intent(context,  ValidateCodeResetActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("email", email);
                   context.startActivity(intent);
               }else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                   // Token no válido, redirige a la pantalla de login
                   Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                   PreferencesUtil.redirectToLogin(getApplication());
               } else {
                   Toast.makeText(getApplication(), "Error al obtener respuesta resetear password", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable throwable) {
               Toast.makeText(getApplication(),"Datos OnFailure", Toast.LENGTH_SHORT).show();
               Log.e("Error failure", throwable.getMessage());
               loading.setValue(false);
           }
       });
    }
}
