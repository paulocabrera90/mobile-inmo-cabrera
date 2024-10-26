package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleDetalleBinding;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmueble;
import com.ulp.inmobiliaria_cabrera.models.TipoInmuebleUso;

import java.util.ArrayList;
import java.util.List;

public class InmuebleDetalleFragment extends Fragment {

    private InmuebleDetalleViewModel viewModel;
    private FragmentInmuebleDetalleBinding binding;
    private List<TipoInmueble> tiposInmueble = new ArrayList<>();
    private List<TipoInmuebleUso> tiposInmuebleUso = new ArrayList<>();
    private int ID_INMUEBLE;
    private int ID_PROPIETARIO;
    private int idTipoInmueble;
    private int idTipoInmuebleUso;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(InmuebleDetalleViewModel.class);

        binding = FragmentInmuebleDetalleBinding.inflate(inflater, container, false);
        ID_INMUEBLE = getArguments() != null ? getArguments().getInt("idInmueble") : 0;
        ID_PROPIETARIO = getArguments() != null ? getArguments().getInt("idPropietario") : 0;

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

        viewModel.getBtnEditEnable().observe(getViewLifecycleOwner(),
                enabled -> binding.buttonEdit.setEnabled(enabled));
        viewModel.getBtnSaveEnable().observe(getViewLifecycleOwner(),
                enabled -> binding.buttonSave.setEnabled(enabled));

        viewModel.getInmueble().observe(
            getViewLifecycleOwner(), inmueble  -> {
                binding.editTextDireccion.setText(inmueble.getNombreInmueble());
                binding.editTextAmbientes.setText(String.valueOf(inmueble.getAmbientes()));
                binding.editTextPrecio.setText(String.valueOf(inmueble.getPrecio()));
                binding.editTextLatitud.setText(inmueble.getCoordenadaLat());
                binding.editTextLongitud.setText(inmueble.getCoordenadaLon());
                binding.switchActivo.setChecked(inmueble.isActivo());
                // CARGO LOS SPINNER DE TIPOS DE INMUEBLE
                binding.spinnerTipoInmueble.setAdapter(tipoInmuebleAdapter);
                binding.spinnerTipoInmuebleUso.setAdapter(tipoInmuebleUsoAdapter);
        });

        viewModel.getEditEnabled().observe(getViewLifecycleOwner(), flag -> {
            binding.imageInmueble.setEnabled(flag);
            binding.editTextPrecio.setEnabled(flag);
            binding.editTextAmbientes.setEnabled(flag);
            binding.spinnerTipoInmueble.setEnabled(flag);
            binding.spinnerTipoInmuebleUso.setEnabled(flag);
            binding.switchActivo.setEnabled(flag);
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
            Inmueble inmueble = new Inmueble(
                    binding.editTextDireccion.getText().toString(),
                    idTipoInmuebleUso,
                    idTipoInmueble,
                    Integer.parseInt(binding.editTextAmbientes.getText().toString()),
                    binding.editTextLatitud.getText().toString(),
                    Double.parseDouble(binding.editTextPrecio.getText().toString()),
                    binding.editTextLongitud.getText().toString(),
                    ID_PROPIETARIO,
                    binding.switchActivo.isChecked(),
                    null//binding.imageInmueble.
            );
            viewModel.saveInmueble(inmueble, ID_INMUEBLE);
        });

        binding.buttonEdit.setOnClickListener(view -> viewModel.enableEdit());
        viewModel.setInmueble(ID_INMUEBLE);
        viewModel.setTipoInmueble();
        viewModel.setTipoInmuebleUso();
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}