package com.ulp.inmobiliaria_cabrera;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private final MutableLiveData<String> nombre = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    private final MutableLiveData<String> avatarUrl = new MutableLiveData<>();

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<String> getNombre() {
        return nombre;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public LiveData<String> getAvatarUrl() {
        return avatarUrl;
    }

    public void addHeadresNav() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("token_prefs", Context.MODE_PRIVATE);

        String nombreCompletoJson = sharedPreferences.getString("nombreCompleto", null);
        String emailJson = sharedPreferences.getString("email", null);
        if (nombreCompletoJson != null && emailJson != null ) {
            nombre.setValue(nombreCompletoJson);
            email.setValue(emailJson);
        }
    }
}
