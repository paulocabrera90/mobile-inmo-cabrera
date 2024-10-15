package com.ulp.inmobiliaria_cabrera.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.MainActivity;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.LoginView;
import com.ulp.inmobiliaria_cabrera.request.response.LoginResponse;
import com.ulp.inmobiliaria_cabrera.ui.home.HomeFragment;
import com.ulp.inmobiliaria_cabrera.ui.register.RegistroActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;


    public LoginActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<String> getAviso() {
        if (avisoMutable == null) {
            avisoMutable = new MutableLiveData<>();
        }
        return avisoMutable;
    }

    public LiveData<Integer> getAvisoVisibility() {
        if (avisoVisibilityMutable == null) {
            avisoVisibilityMutable = new MutableLiveData<>();
        }
        return avisoVisibilityMutable;
    }


    public void login(String email, String contrasena) {

       Call<LoginResponse> call = ApiClient.getApiInmobiliaria().login(new LoginView(email, contrasena));

       call.enqueue(new Callback<LoginResponse>() {
           @Override
           public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
               if(response.isSuccessful()) {
                   Log.d("Salida", response.body().token);

                   Intent intent = new Intent(context, MainActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("isUser", true);
                   context.startActivity(intent);
               }else {
                   Toast.makeText(getApplication(),"Datos Incorrecotes", Toast.LENGTH_SHORT).show();
                   avisoMutable.setValue("Email o usuario incorrecto");
                   avisoVisibilityMutable.setValue(View.VISIBLE);
               }
           }

           @Override
           public void onFailure(Call<LoginResponse> call, Throwable throwable) {
               Toast.makeText(getApplication(),"Datos OnFailure", Toast.LENGTH_SHORT).show();

               Log.e("Error failure", throwable.getMessage());
           }
       });
//        if (usuario != null) {
//            Intent intent = new Intent(context, HomeFragment.class);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.putExtra("isUser", true);
//            context.startActivity(intent);
//        } else {
//            avisoMutable.setValue("Email o usuario incorrecto");
//            avisoVisibilityMutable.setValue(View.VISIBLE);
//        }
    }

    public void Registrarse() {
        Intent intent = new Intent(context, RegistroActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
