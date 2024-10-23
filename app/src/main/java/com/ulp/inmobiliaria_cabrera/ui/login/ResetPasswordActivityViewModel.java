package com.ulp.inmobiliaria_cabrera.ui.login;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.ResetPasswordRequest;
import com.ulp.inmobiliaria_cabrera.ui.resetear.ValidateCodeResetActivity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPasswordActivityViewModel extends AndroidViewModel {
    private Context context;

    public ResetPasswordActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public void resetPassword(String email) {

       Call<ResponseBody> call = ApiClient.getInmobiliariaService(context)
               .resetPassword(new ResetPasswordRequest(email));

       call.enqueue(new Callback<ResponseBody>() {
           @Override
           public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
               if(response.isSuccessful()) {
                   Intent intent = new Intent(context,  ValidateCodeResetActivity.class);
                   intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                   intent.putExtra("email", email);
                   context.startActivity(intent);
               }else {
                   Toast.makeText(getApplication(),"Datos Incorrectos para resetear.", Toast.LENGTH_SHORT).show();
               }
           }

           @Override
           public void onFailure(Call<ResponseBody> call, Throwable throwable) {
               Toast.makeText(getApplication(),"Datos OnFailure", Toast.LENGTH_SHORT).show();
               Log.e("Error failure", throwable.getMessage());
           }
       });
    }
}
