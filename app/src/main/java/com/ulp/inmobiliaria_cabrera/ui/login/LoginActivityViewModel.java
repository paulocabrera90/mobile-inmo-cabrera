package com.ulp.inmobiliaria_cabrera.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.MainActivity;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.LoginRequest;
import com.ulp.inmobiliaria_cabrera.request.response.LoginResponse;
import com.ulp.inmobiliaria_cabrera.ui.register.PerfilFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private int activador = 0;
    private MutableLiveData<Boolean> estadoM;

    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<Boolean> getEstadoM() {
        if(estadoM == null){
            estadoM = new MutableLiveData<>();
        }
        return estadoM;
    }

    public void login(String email, String contrasena) {

       Call<LoginResponse> call = ApiClient.getInmobiliariaService(context)
               .login(new LoginRequest(email, contrasena));

       call.enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               if(response.isSuccessful()) {
                   Log.d("Salida token", response.body().tokenSession);
                   ApiClient.guardarToken(context, response.body());

                   Intent intent = new Intent(context, MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("isUser", true);
                   context.startActivity(intent);
               }else {
                   Toast.makeText(getApplication(),"Datos Incorrecotes", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<LoginResponse> call, Throwable throwable) {
               Toast.makeText(getApplication(),"Datos OnFailure", Toast.LENGTH_SHORT).show();
               Log.e("Error failure", throwable.getMessage());
           }
       });
    }

    public void sensorG(float movi){
        //Log.d("Salida sensor", String.valueOf(movi));
        if(movi > 8 || movi < -12){
            activador++;
        }
        if(activador > 30){
            activador = 0;
            estadoM.setValue(true);

        }
    }

    public void reset() {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("isUser", true);
        context.startActivity(intent);
    }
}
