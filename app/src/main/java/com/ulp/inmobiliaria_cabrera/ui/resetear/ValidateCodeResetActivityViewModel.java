package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.request.ConfirmPasswordResetRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ValidateCodeResetActivityViewModel extends AndroidViewModel {
    private Context context;
    private MutableLiveData<String> code = new MutableLiveData<>();
    private MutableLiveData<Boolean> isCodeValid = new MutableLiveData<>(false);

    public ValidateCodeResetActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }

    public LiveData<String> getCode() {
        return code;
    }

    public LiveData<Boolean> isCodeValid() {
        return isCodeValid;
    }

    public void updateCode(String newCode) {
        if (isValidCode(newCode)) {
            code.setValue(newCode);
            isCodeValid.setValue(true);
        } else {
            Log.d("ViewModel", "Invalid code entered: " + newCode);
        }
    }

    private boolean isValidCode(String code) {
        return !TextUtils.isEmpty(code) && code.matches("\\d{6}");
    }

    public void resetOkPassword(String email) {
        String codeValue = code.getValue();
        Call<Boolean> call = ApiClient.getInmobiliariaService(context)
                .validateCode(new ConfirmPasswordResetRequest(email, codeValue));

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful() && response.body()) {
                    Intent intent = new Intent(context, ResetChangePasswordActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("email", email);
                    intent.putExtra("verificationNumber", codeValue);
                    context.startActivity(intent);
                }else {
                    Toast.makeText(getApplication(),"Error con el codigo de verificacion o tiempo transcurrido.", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable throwable) {
                Toast.makeText(getApplication(),"Datos OnFailure", Toast.LENGTH_SHORT).show();

                Log.e("Error failure", throwable.getMessage());
            }
        });
    }

}
