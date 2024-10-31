package com.ulp.inmobiliaria_cabrera.ui.contratos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentContratoDetalleBinding;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleDetalleBinding;
import com.ulp.inmobiliaria_cabrera.ui.inmuebles.InmuebleDetalleViewModel;

import java.text.SimpleDateFormat;

public class ContratoDetalleFragment extends Fragment {

    private ContratoDetalleViewModel viewModel;
    private FragmentContratoDetalleBinding binding;
    private int ID_CONTRATO;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(ContratoDetalleViewModel.class);
        binding = FragmentContratoDetalleBinding.inflate(inflater, container, false);
        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);

        initConstants();

        viewModel.getContratoLiveData().observe(
                getViewLifecycleOwner(), contrato -> {
                    binding.textId.setText("0000" + contrato.getId());
                    binding.textMontoAlquiler.setText(String.format("$%.2f", contrato.getMontoAlquiler()));
                    binding.textDireccionInmueble.setText(contrato.getInmueble().getDireccion());
                    binding.textDetalleInquilino.setText(contrato.getInquilino().getNombreCompleto());

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    binding.textFechaDesde.setText(dateFormat.format(contrato.getFechaDesde()));
                    binding.textFechaHasta.setText(dateFormat.format(contrato.getFechaHasta()));
                    binding.textCantidadCuotas.setText("0"+contrato.getCuotasPagas()+"/0"+contrato.getCantidadCuotas());
                    binding.buttonPagos.setEnabled(true);

                    if (contrato.getMulta() != null && contrato.getMulta() > 0) {
                        binding.textMulta.setText(String.format("$%.2f", contrato.getMulta()));
                        binding.textFechaFinalizacion
                                .setText(dateFormat.format(contrato.getFechaFinalizacionAnticipada().toString()));

                        binding.textMulta.setVisibility(View.VISIBLE);
                        binding.textFechaFinalizacion.setVisibility(View.VISIBLE);
                    } else {
                        binding.labelMulta.setVisibility(View.GONE);
                        binding.textMulta.setVisibility(View.GONE);
                        binding.labelFechaFinalizacion.setVisibility(View.GONE);
                        binding.textFechaFinalizacion.setVisibility(View.GONE);
                    }
                }
        );

        viewModel.getPagosEnabled().observe(getViewLifecycleOwner(), flag -> {
            binding.buttonPagos.setEnabled(flag);
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        binding.buttonPagos.setOnClickListener(view -> {
             viewModel.enablePagos();
             Bundle bundle = new Bundle();
             bundle.putInt("idContrato", ID_CONTRATO);
             NavController navController = Navigation.findNavController(view);
             navController.navigate(R.id.action_nav_contratos_detalle_to_nav_pagos, bundle);
        });

        viewModel.setContratoMutableLiveData(ID_CONTRATO);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initConstants() {
            ID_CONTRATO = getArguments() != null ? getArguments().getInt("idContrato") : 0;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }

}