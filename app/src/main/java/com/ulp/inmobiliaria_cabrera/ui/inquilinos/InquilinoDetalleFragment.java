package com.ulp.inmobiliaria_cabrera.ui.inquilinos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleDetalleBinding;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInquilinoDetalleBinding;

public class InquilinoDetalleFragment extends Fragment {
    private int ID_INMUEBLE;
    private boolean FLAG_NEW_INMUEBLE = false;
    private int ID_PROPIETARIO;

    private InquilinoDetalleViewModel viewModel;
    private FragmentInquilinoDetalleBinding binding;

    public static InquilinoDetalleFragment newInstance() {
        return new InquilinoDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(InquilinoDetalleViewModel.class);

        binding = FragmentInquilinoDetalleBinding.inflate(inflater, container, false);
        initConstants();

        viewModel.getInquilinoLiveData().observe(
                getViewLifecycleOwner(), inquilino  -> {
                    binding.textDireccion.setText(inquilino.getDireccion());
                    binding.textDni.setText(String.valueOf(inquilino.getDni()));
                    binding.textApellido.setText(String.valueOf(inquilino.getApellido()));
                    binding.textNombre.setText(String.valueOf(inquilino.getNombre()));
                    binding.textTelefono.setText(String.valueOf(inquilino.getTelefono()));
                    binding.textEmail.setText(String.valueOf(inquilino.getEmail()));

                    // CARGO LOS SPINNER DE TIPOS DE INMUEBLE

                });


        viewModel.setInquilinoLiveData(ID_INMUEBLE);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(InquilinoDetalleViewModel.class);
        // TODO: Use the ViewModel
    }

    private void initConstants() {
        ID_INMUEBLE = getArguments() != null ? getArguments().getInt("idInmueble") : 0;
        ID_PROPIETARIO = getArguments() != null ? getArguments().getInt("idPropietario") : 0;
        FLAG_NEW_INMUEBLE = getArguments() != null ? getArguments().getBoolean("newInmueble") : false;
    }

}