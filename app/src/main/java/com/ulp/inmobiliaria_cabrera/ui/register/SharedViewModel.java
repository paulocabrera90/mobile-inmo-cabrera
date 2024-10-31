package com.ulp.inmobiliaria_cabrera.ui.register;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class SharedViewModel extends AndroidViewModel {
    private final MutableLiveData<String> nombreCompleto = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();

    public SharedViewModel(@NonNull Application application) {
        super(application);
        cargarDatosDesdePreferencias();
    }


    public LiveData<String> getNombreCompleto() {
        return nombreCompleto;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void cargarDatosDesdePreferencias() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("token_prefs", Context.MODE_PRIVATE);
        String nombreCompletoJson = sharedPreferences.getString("nombreCompleto", null);
        String emailJson = sharedPreferences.getString("email", null);

        if (nombreCompletoJson != null && emailJson != null) {
            nombreCompleto.setValue(nombreCompletoJson);
            email.setValue(emailJson);
        }
    }

    public void actualizarDatosPropietario(String nombre, String email) {
        nombreCompleto.setValue(nombre);
        this.email.setValue(email);
    }
}

