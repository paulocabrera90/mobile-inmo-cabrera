package com.ulp.inmobiliaria_cabrera.constants;

import com.google.android.gms.maps.model.LatLng;

public class Constants {
    public static final String PROTOCOL = "http://";
    public static final String HOST = "192.168.1.181";
    public static final int PORT = 5000;
    public static final String BASE_PATH = "/api/";

    public static final String URL_BASE = PROTOCOL + HOST + ":" + PORT + BASE_PATH;

    //MAPA

    public static final Double LATITUD_INICIO = -33.301124;
    public static final Double LONGITUD_INICIO = -66.324545;
    public static final LatLng INMOBILIARIA_CABRERA = new LatLng(LATITUD_INICIO, LONGITUD_INICIO);
    public static final String INMOBILIARIA_CABRERA_NAME ="Inmobiliaria Cabrera";
    public static final int ZOOM = 19;
    public static final int BEARING = 50;
    public static final int TILT = 50;

    public static final String PREFS_NAME = "token_prefs";
    public static final String KEY_ID = "id";

    public static final int CODE_RESPONSE_UNAUTHORIZED = 401;
    public static final String NUMERO_INMO = "2664745225";
}
