package com.ulp.inmobiliaria_cabrera.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.constants.Constants;
import com.ulp.inmobiliaria_cabrera.databinding.ActivityLoginBinding;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginActivityViewModel viewModel;
    private SensorManager sensorManager;
    private ReadSensor readSensor;
    private List<Sensor> listaSensores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(LoginActivityViewModel.class);
        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);


        initViews();
        initializeSensor();
        solicitarPermisos();

        viewModel.getEstadoM().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+ Constants.NUMERO_INMO));
                startActivity(i);
                Toast.makeText(LoginActivity.this, "Llamando Inmobiliaria "+ Constants.INMOBILIARIA_CABRERA_NAME, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

    }

    private void initializeSensor() {
        readSensor = new ReadSensor();
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        listaSensores = sensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    private void initViews() {
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.loadingOverlay.progressBar.setVisibility(View.VISIBLE);
                viewModel.login(binding.email.getText().toString(), binding.contrasena.getText().toString());
            }
        });

        binding.resetTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.reset();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(readSensor);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(readSensor, listaSensores.get(0), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(readSensor);
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }

    private void solicitarPermisos() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permisosNecesarios = new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CALL_PHONE
            };
            List<String> listaPermisos = new ArrayList<>();

            for (String permiso : permisosNecesarios) {
                if (checkSelfPermission(permiso) != PackageManager.PERMISSION_GRANTED) {
                    listaPermisos.add(permiso);
                }
            }

            String[] permisos = new String[listaPermisos.size()];
            listaPermisos.toArray(permisos);

            if (permisos.length > 0) {
                ActivityCompat.requestPermissions(this, permisos, 100);
            } else {
                Toast.makeText(this, "Todos los permisos concedidos", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Sin Permisos", Toast.LENGTH_SHORT).show();
        }
    }


    private class ReadSensor implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            viewModel.sensorG(sensorEvent.values[0]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}
