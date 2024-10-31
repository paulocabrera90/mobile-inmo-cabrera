package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import static android.app.Activity.RESULT_OK;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.Gson;
import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmuebleUso;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InmuebleDetalleViewModel extends AndroidViewModel {
    private ApiClient.InmobiliariaService api;
    private MutableLiveData<Inmueble> inmuebleMutableLiveData;
    private MutableLiveData<List<TipoInmuebleUso>> listTipoInmuebleUsoMutableLiveData;
    private MutableLiveData<List<TipoInmueble>> listTipoinmuebleMutableLiveData;
    private MutableLiveData<Boolean> editEnabled;
    private final MutableLiveData<Bitmap> selectedImgBitmap = new MutableLiveData<>();
    private MutableLiveData<Uri> uriMutableLiveData;
    private MutableLiveData<Boolean> loading;
    private Uri uri;
    private String uriString;

    public InmuebleDetalleViewModel(@NonNull Application application) {
        super(application);
        api = ApiClient.getInmobiliariaService(application.getApplicationContext());

        editEnabled = new MutableLiveData<>(false);
        loading = new MutableLiveData<>(false);
    }

    public LiveData<Boolean> getLoading() {
        return loading;
    }

    public void setSelectedImgUri(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), imageUri);
            selectedImgBitmap.setValue(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public LiveData<Bitmap> getSelectedImgBitmap() {
        return selectedImgBitmap;
    }

    public LiveData<Boolean> getEditEnabled() {
        if (editEnabled == null) {
            editEnabled = new MutableLiveData<>();
        }
        return editEnabled;
    }

    public LiveData<Inmueble> getInmueble() {
        if (inmuebleMutableLiveData == null) {
            inmuebleMutableLiveData = new MutableLiveData<>();
        }
        return inmuebleMutableLiveData;
    }

    public LiveData<Uri> getUriMutable(){

        if(uriMutableLiveData==null){
            uriMutableLiveData=new MutableLiveData<>();
        }
        return uriMutableLiveData;
    }


    public LiveData<List<TipoInmuebleUso>> getTipoInmuebleUso() {
        if (listTipoInmuebleUsoMutableLiveData == null) {
            listTipoInmuebleUsoMutableLiveData = new MutableLiveData<List<TipoInmuebleUso>>();
        }
        return listTipoInmuebleUsoMutableLiveData;
    }

    public LiveData<List<TipoInmueble>> getTipoInmueble() {
        if (listTipoinmuebleMutableLiveData == null) {
            listTipoinmuebleMutableLiveData = new MutableLiveData<List<TipoInmueble>>();
        }
        return listTipoinmuebleMutableLiveData;
    }

    public void enableEdit() {
        this.editEnabled.setValue(true);
    }

    public void setInmueble(int idInmueble, boolean newInmueble){
        loading.setValue(true);
        if (!newInmueble){
            api.getInmueble(idInmueble).enqueue(new Callback<Inmueble>() {
                @Override
                public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                    loading.setValue(false);
                    if (response.isSuccessful()) {
                        inmuebleMutableLiveData.setValue(response.body());

                    } else {
                        // avisoMutable.setValue("Error al obtener el propietario");
                        Toast.makeText(getApplication().getApplicationContext(), "Error al obtener inmuebles", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Inmueble> call, Throwable throwable) {
                    loading.setValue(false);
                    Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void setTipoInmueble(){
        loading.setValue(true);
        api.getTipoInmuebles().enqueue(new Callback<List<TipoInmueble>>() {
            @Override
            public void onResponse(Call<List<TipoInmueble>> call, Response<List<TipoInmueble>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    listTipoinmuebleMutableLiveData.setValue(response.body());
                } else {
                    // avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener listado de tipo inmueble", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoInmueble>> call, Throwable throwable) {
                loading.setValue(false);
                Toast.makeText(getApplication().getApplicationContext(), "Error conexion", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void setTipoInmuebleUso(){
        loading.setValue(true);
        api.getTipoInmueblesUso().enqueue(new Callback<List<TipoInmuebleUso>>() {
            @Override
            public void onResponse(Call<List<TipoInmuebleUso>> call, Response<List<TipoInmuebleUso>> response) {
                loading.setValue(false);
                if (response.isSuccessful()) {
                    listTipoInmuebleUsoMutableLiveData.setValue(response.body());
                } else {
                    // avisoMutable.setValue("Error al obtener el propietario");
                    Toast.makeText(getApplication().getApplicationContext(), "Error al obtener listado de tipo inmueble usos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TipoInmuebleUso>> call, Throwable throwable) {
                loading.setValue(false);
                Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void saveInmueble(Inmueble inmueble, int idInmueble){
        loading.setValue(false);
        Gson gson = new Gson();
        String inmuebleJsonString = gson.toJson(inmueble);
        RequestBody inmuebleJsonBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), inmuebleJsonString);

        Bitmap imgBit = selectedImgBitmap.getValue();

        // Crear cada RequestBody a partir de los datos del inmueble
        RequestBody activo = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.isActivo()));
        RequestBody ambientes = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getAmbientes()));
        RequestBody coordenadaLat = RequestBody.create(MediaType.parse("text/plain"), inmueble.getCoordenadaLat());
        RequestBody coordenadaLon = RequestBody.create(MediaType.parse("text/plain"), inmueble.getCoordenadaLon());
        RequestBody direccion = RequestBody.create(MediaType.parse("text/plain"), inmueble.getDireccion());
        RequestBody fechaActualizacion = RequestBody.create(MediaType.parse("text/plain"), "2024-10-28T16:15:41");
        RequestBody fechaCreacion = RequestBody.create(MediaType.parse("text/plain"), "2024-10-28T16:15:41");
        RequestBody id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idInmueble));
        RequestBody idPropietario = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getIdPropietario()));
        RequestBody idTipoInmueble = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getIdTipoInmueble()));
        RequestBody idTipoInmuebleUso = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getIdTipoInmuebleUso()));
        RequestBody precio = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(inmueble.getPrecio()));

        try {
            MultipartBody.Part imagePart = null;
            if (selectedImgBitmap.getValue() != null) {
                File imageFile = convertBitmapToFile(imgBit);
                if (imageFile.exists() && imageFile.length() > 0) {
                    RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile);
                    imagePart = MultipartBody.Part.createFormData("image", imageFile.getName(), imageBody);
                } else {
                    Log.e("InmuebleDetalleViewModel", "Image file not created or is empty.");
                    return;
                }
            }

            if (idInmueble != 0) {
                api.actualizarInmueble(activo, ambientes, coordenadaLat, coordenadaLon, direccion, fechaActualizacion,
                    fechaCreacion, id, idPropietario, idTipoInmueble, idTipoInmuebleUso, precio, imagePart)
                            .enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        loading.setValue(false);
                        if (response.isSuccessful()) {
                            inmuebleMutableLiveData.setValue(inmueble);
                            Toast.makeText(getApplication().getApplicationContext(), "Datos guardados", Toast.LENGTH_SHORT).show();
                            editEnabled.setValue(true);
                        } else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                            // Token no válido, redirige a la pantalla de login
                            Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                            PreferencesUtil.redirectToLogin(getApplication());
                        } else {
                            Toast.makeText(getApplication(), "Error al obtener inmueble", Toast.LENGTH_SHORT).show();
                            editEnabled.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable throwable) {
                        loading.setValue(false);
                        Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                //api.crearInmueble(inmuebleJsonBody).enqueue(new Callback<Inmueble>() {
                api.crearInmueble(activo, ambientes, coordenadaLat, coordenadaLon, direccion, fechaActualizacion,
                        fechaCreacion, idPropietario, idTipoInmueble, idTipoInmuebleUso, precio, imagePart)
                        .enqueue(new Callback<Inmueble>() {
                    @Override
                    public void onResponse(Call<Inmueble> call, Response<Inmueble> response) {
                        loading.setValue(false);
                        if (response.isSuccessful()) {
                            inmuebleMutableLiveData.setValue(response.body());
                            editEnabled.setValue(true);
                        }else if (response.code() == Constants.CODE_RESPONSE_UNAUTHORIZED) {
                            // Token no válido, redirige a la pantalla de login
                            Toast.makeText(getApplication(), "Sesión expirada. Inicie sesión nuevamente.", Toast.LENGTH_SHORT).show();
                            PreferencesUtil.redirectToLogin(getApplication());
                        } else {
                            Toast.makeText(getApplication(), "Error al obtener inmueble", Toast.LENGTH_SHORT).show();
                            editEnabled.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<Inmueble> call, Throwable throwable) {
                        loading.setValue(false);
                        Toast.makeText(getApplication().getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
            loading.setValue(false);
            Toast.makeText(getApplication(), "Error al preparar la imagen", Toast.LENGTH_SHORT).show();
        }

    }

    private File convertBitmapToFile(Bitmap bitmap) throws IOException {
        File file = new File(getApplication().getCacheDir(), "real_estate_image.jpg");
        FileOutputStream fos = new FileOutputStream(file);
        resizeBitmap(bitmap, 1280, 720).compress(Bitmap.CompressFormat.JPEG, 100, fos);
        fos.flush();
        fos.close();
        return file;
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int maxWidth, int maxHeight) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float ratio = Math.min((float) maxWidth / width, (float) maxHeight / height);
        int newWidth = Math.round(ratio * width);
        int newHeight = Math.round(ratio * height);
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }
}