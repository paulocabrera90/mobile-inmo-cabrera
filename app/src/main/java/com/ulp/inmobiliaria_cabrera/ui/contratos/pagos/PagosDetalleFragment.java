package com.ulp.inmobiliaria_cabrera.ui.contratos.pagos;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria_cabrera.databinding.FragmentPagosDetalleBinding;

import java.text.SimpleDateFormat;

public class PagosDetalleFragment extends Fragment {

    private PagosDetalleViewModel viewModel;
    private FragmentPagosDetalleBinding binding;
    private int ID_PAGO;

    public static PagosDetalleFragment newInstance() {
        return new PagosDetalleFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(PagosDetalleViewModel.class);

        binding = FragmentPagosDetalleBinding.inflate(inflater, container, false);
        initConstants();

        viewModel.getPagoMutableLiveData().observe(
                getViewLifecycleOwner(), pago -> {

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy:hh:mm");
                    binding.textContrato.setText(String.format("Nro de Contrato: %06d", pago.getContrato().getId()));
                    binding.textFechaPago.setText(dateFormat.format(pago.getFechaPago()));
                    binding.textNumeroPago.setText(String.valueOf(pago.getNumeroPago()));
                    binding.textDetalle.setText(pago.getDetalle());
                    binding.textImporte.setText(String.format("$%.2f", pago.getImporte()));
                    binding.textEstado.setText(pago.getEstado().getDisplayName());
                    binding.textInquilino.setText(pago.getContrato().getInquilino().getNombreCompleto());

                     if (pago.getFechaAnulacion() != null) {
                        binding.textFecAnulacion.setText(dateFormat.format(pago.getFechaAnulacion()));
                        binding.textFecAnulacion.setVisibility(View.VISIBLE);
                    } else {
                        binding.labelFecahAnul.setVisibility(View.GONE);
                        binding.textFecAnulacion.setVisibility(View.GONE);
                    }
                }
        );

        viewModel.setPagoMutableLiveData(ID_PAGO);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initConstants() {
        ID_PAGO = getArguments() != null ? getArguments().getInt("idPago") : 0;
    }

}