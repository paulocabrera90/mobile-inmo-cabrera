package com.ulp.inmobiliaria_cabrera.ui.register;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PerfilViewModel extends ViewModel {
    private MutableLiveData<String> avisoMutable;
    private MutableLiveData<Integer> avisoVisibilityMutable;
    private MutableLiveData<Uri> uriMutableLiveData;
    private Uri uri;
    private String uriString;

    public LiveData<String> getAvisoMutable() {
        if (avisoMutable == null) {
            avisoMutable = new MutableLiveData<>();
        }
        return avisoMutable;
    }

    public LiveData<Integer> getAvisoVisibilityMutable() {
        if (avisoVisibilityMutable == null) {
            avisoVisibilityMutable = new MutableLiveData<>();
        }
        return avisoVisibilityMutable;
    }

    public LiveData<Uri> getUriMutable() {
        if (uriMutableLiveData == null) {
            uriMutableLiveData = new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }

    public void recibirFoto(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            if (data != null && data.getData() != null) {
                uri = data.getData();
                uriString = uri.toString();
                uriMutableLiveData.setValue(uri);
            }
        }
    }

    public Uri getDefaultImageUri() {
        // Uri for default image when no image is provided
        return Uri.EMPTY; // Use Uri.EMPTY as a placeholder
    }
}
