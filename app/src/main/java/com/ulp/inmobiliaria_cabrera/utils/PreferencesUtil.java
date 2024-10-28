package com.ulp.inmobiliaria_cabrera.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.ulp.inmobiliaria_cabrera.constants.Constants;

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
}
