package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleDetalleBinding;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmuebleUso;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel viewModel;
    private FragmentInmuebleDetalleBinding binding;
    private List<TipoInmueble> tiposInmueble = new ArrayList<>();
    private List<TipoInmuebleUso> tiposInmuebleUso = new ArrayList<>();
    private int ID_INMUEBLE;
    private boolean FLAG_NEW_INMUEBLE = false;
    private int ID_PROPIETARIO;
    private int idTipoInmueble;
    private int idTipoInmuebleUso;

    private ActivityResultLauncher<Intent> galleryLauncher;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ActivityResultLauncher<Intent> arl;
    private Uri uriImage;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(InmuebleDetalleViewModel.class);

        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);

        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        initConstants();

        setupGalleryLauncher();
        //SPINNERS
        ArrayAdapter<TipoInmueble> tipoInmuebleAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_build, tiposInmueble);
        ArrayAdapter<TipoInmuebleUso> tipoInmuebleUsoAdapter = new ArrayAdapter<>(getContext(), R.layout.spinner_item_build, tiposInmuebleUso);
        tipoInmuebleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tipoInmuebleUsoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.spinnerTipoInmueble.setAdapter(tipoInmuebleAdapter);
        binding.spinnerTipoInmuebleUso.setAdapter(tipoInmuebleUsoAdapter);

        viewModel.getTipoInmueble().observe(
                getViewLifecycleOwner(), tipoInmuebles -> {
                    tiposInmueble.clear();
                    tiposInmueble.addAll(tipoInmuebles);
                    tipoInmuebleAdapter.notifyDataSetChanged();
                });

        viewModel.getTipoInmuebleUso().observe(
                getViewLifecycleOwner(), tipoInmueblesUso -> {
                    tiposInmuebleUso.clear();
                    tiposInmuebleUso.addAll(tipoInmueblesUso);
                    tipoInmuebleUsoAdapter.notifyDataSetChanged();
                });

        viewModel.getUriMutable().observe(
                getViewLifecycleOwner(), uri -> {
                    binding.imageInmueble.setImageURI(uri);
                });

        viewModel.getSelectedImgBitmap().observe(getViewLifecycleOwner(), bitmap -> {
                binding.imageInmueble.setImageBitmap(bitmap);
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getInmueble().observe(

                getViewLifecycleOwner(), inmueble  -> {

                    binding.editTextDireccion.setText(inmueble.getDireccion());
                    binding.editTextAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                    binding.editTextPrecio.setText(String.format("$%.2f", inmueble.getPrecio()));
                    binding.editTextLatitud.setText(inmueble.getCoordenadaLat());
                    binding.editTextLongitud.setText(inmueble.getCoordenadaLon());
                    binding.switchActivo.setChecked(inmueble.isActivo());
                    // CARGO LOS SPINNER DE TIPOS DE INMUEBLE
                    binding.spinnerTipoInmueble.setAdapter(tipoInmuebleAdapter);
                    binding.spinnerTipoInmuebleUso.setAdapter(tipoInmuebleUsoAdapter);

                    String imagenBase64 = inmueble.getImageBlob();
                    Glide.with(getActivity())
                            .load(imagenBase64 != null ? "data:image/jpeg;base64," + imagenBase64 : null)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .placeholder(R.drawable.not_found)
                            .error(R.drawable.not_found)
                            .into(binding.imageInmueble);

                    setEnableBinding(FLAG_NEW_INMUEBLE);
                });

        viewModel.getEditEnabled().observe(getViewLifecycleOwner(), flag -> {
            setEnableBinding(FLAG_NEW_INMUEBLE?!flag:flag);
        });

        binding.spinnerTipoInmuebleUso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoInmuebleUso selectedTipoInmuebleUso = (TipoInmuebleUso) parent.getItemAtPosition(position);
                idTipoInmuebleUso = selectedTipoInmuebleUso.getId();
                Log.d("SpinnerValue", "Valor seleccionado: " + idTipoInmuebleUso);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Llamado cuando no hay ninguna selección
            }
        });

        binding.spinnerTipoInmueble.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TipoInmueble selectedTipoInmueble = (TipoInmueble) parent.getItemAtPosition(position);
                idTipoInmueble = selectedTipoInmueble.getId();
                Log.d("SpinnerValue", "Valor seleccionado: " + idTipoInmueble);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Llamado cuando no hay ninguna selección
            }
        });

        binding.buttonSave.setOnClickListener(view -> {
            double price = parseCurrencyString(binding.editTextPrecio.getText().toString());
            Inmueble inmueble = new Inmueble(
                    binding.editTextDireccion.getText().toString(),
                    idTipoInmuebleUso,
                    idTipoInmueble,
                    Integer.parseInt(binding.editTextAmbientes.getText().toString()),
                    binding.editTextLatitud.getText().toString(),
                    price,
                    binding.editTextLongitud.getText().toString(),
                    ID_PROPIETARIO,
                    binding.switchActivo.isChecked()
            );
            viewModel.saveInmueble(inmueble, ID_INMUEBLE);
        });

        // Listener para el botón de seleccionar imagen
        binding.buttonAddImage.setOnClickListener(v -> openGallery());

        binding.buttonEdit.setOnClickListener(view -> viewModel.enableEdit());
        viewModel.setInmueble(ID_INMUEBLE, FLAG_NEW_INMUEBLE);
        viewModel.setTipoInmueble();
        viewModel.setTipoInmuebleUso();
       // viewModel.galleryOpen();

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setEnableBinding(Boolean flag) {
        binding.imageInmueble.setEnabled(flag);
        binding.editTextDireccion.setEnabled(flag);
        binding.editTextLongitud.setEnabled(flag);
        binding.editTextLatitud.setEnabled(flag);
        binding.editTextPrecio.setEnabled(flag);
        binding.editTextAmbientes.setEnabled(flag);
        binding.spinnerTipoInmueble.setEnabled(flag);
        binding.spinnerTipoInmuebleUso.setEnabled(flag);
        binding.switchActivo.setEnabled(flag);
        binding.imageInmueble.setEnabled(flag);
        binding.buttonAddImage.setEnabled(flag);

        binding.buttonEdit.setEnabled(!flag);
        binding.buttonSave.setEnabled(flag);
    }

    private void initConstants() {
        ID_INMUEBLE = getArguments() != null ? getArguments().getInt("idInmueble") : 0;
        ID_PROPIETARIO = getArguments() != null ? getArguments().getInt("idPropietario") : 0;
        FLAG_NEW_INMUEBLE = getArguments() != null ? getArguments().getBoolean("newInmueble") : false;
    }

    private void setupGalleryLauncher() {
        // Registrar el callback para abrir la galería
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        viewModel.setSelectedImgUri(imageUri);  // Pasar la URI al ViewModel
                    }
                });

        // Registrar el callback para manejar la respuesta al permiso solicitado
        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {
                openGallery();
            } else {
                Toast.makeText(getContext(), "Permiso a galería denegado", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }

    private double parseCurrencyString(String currencyString) {
        String cleanString = currencyString.replaceAll("[$,]", "");
        try {
            return Double.parseDouble(cleanString);
        } catch (NumberFormatException e) {
            Log.e("NumberFormat", "Parsing error on: " + cleanString, e);
            return 0.0;
        }
    }
}