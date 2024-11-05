package com.ulp.inmobiliaria_cabrera.ui.contratos.pagos;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentContratoBinding;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentPagosBinding;
import com.ulp.inmobiliaria_cabrera.models.Contrato;
import com.ulp.inmobiliaria_cabrera.models.Pago;
import com.ulp.inmobiliaria_cabrera.ui.contratos.ContratoAdapter;
import com.ulp.inmobiliaria_cabrera.ui.contratos.ContratoViewModel;

import java.util.List;

public class PagosFragment extends Fragment {

    private PagosViewModel viewModel;
    private RecyclerView recyclerViewPagos;
    private PagosAdapter pagoAdapter;
    private FragmentPagosBinding binding;
    private int ID_CONTRATO;

    public static PagosFragment newInstance() {
        return new PagosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(PagosViewModel.class);
        binding = FragmentPagosBinding.inflate(inflater, container, false);
        initConstants();

        final View rootView = binding.getRoot();

        recyclerViewPagos = (RecyclerView) rootView.findViewById(R.id.recyclerViewPagos);
        recyclerViewPagos
                .addItemDecoration(new DividerItemDecoration(this.getContext() ,
                        DividerItemDecoration.VERTICAL)
                );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewPagos.setLayoutManager(linearLayoutManager);

        viewModel.getAvisoListPago().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.tvAvisoEmptyList.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.getListPagosLiveData().observe(getViewLifecycleOwner(), new Observer<List<Pago>>() {
            @Override
            public void onChanged(List<Pago> pagos) {
                pagoAdapter = new PagosAdapter(pagos, rootView.getContext(), inflater);
                recyclerViewPagos.setAdapter(pagoAdapter);
            }
        });

        viewModel.setListPagosLiveData(ID_CONTRATO);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initConstants() {
        ID_CONTRATO = getArguments() != null ? getArguments().getInt("idContrato") : 0;
    }
}