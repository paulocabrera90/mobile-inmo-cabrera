package com.ulp.inmobiliaria_cabrera.ui.register;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ulp.inmobiliaria_cabrera.models.Propietario;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> nombreCompleto = new MutableLiveData<>();
    private final MutableLiveData<String> email = new MutableLiveData<>();
    // Añadir otros datos si es necesario, como avatar, apellido, etc.

    public LiveData<String> getNombreCompleto() {
        return nombreCompleto;
    }

    public LiveData<String> getEmail() {
        return email;
    }

    public void actualizarDatosPropietario(Propietario propietario) {
        nombreCompleto.setValue(propietario.getNombreCompleto());
        email.setValue(propietario.getEmail());
        // Añadir otros datos si es necesario
    }
}
