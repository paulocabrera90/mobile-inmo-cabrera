package com.ulp.inmobiliaria_cabrera.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.ui.login.LoginActivity;

public class PreferencesUtil {

    public static int getIdPropietario(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Constants.PREFS_NAME, Context.MODE_PRIVATE);
        String idString = sharedPreferences.getString(Constants.KEY_ID, null);
        if (idString != null) {
            return Integer.parseInt(idString);
        } else {
            return -1;
        }
    }

    public static void redirectToLogin(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }
}
