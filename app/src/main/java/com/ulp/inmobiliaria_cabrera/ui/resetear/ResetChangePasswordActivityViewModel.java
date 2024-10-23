package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class ResetChangePasswordActivityViewModel extends AndroidViewModel {
    private Context context;

    public ResetChangePasswordActivityViewModel(@NonNull Application application) {
        super(application);
        context=application.getApplicationContext();
    }
}
