package com.ulp.inmobiliaria_cabrera.ui.contratos;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentContratoBinding;
import com.ulp.inmobiliaria_cabrera.models.Contrato;

import java.util.List;

public class ContratoFragment extends Fragment {

    private ContratoViewModel viewModel;
    private RecyclerView recyclerViewContrato;
    private ContratoAdapter contratoAdapter;
    private FragmentContratoBinding binding;

    public static ContratoFragment newInstance() {
        return new ContratoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(ContratoViewModel.class);
        binding = FragmentContratoBinding.inflate(inflater, container, false);

        final View rootView = binding.getRoot();

        recyclerViewContrato = (RecyclerView) rootView.findViewById(R.id.recyclerViewContratos);
        recyclerViewContrato
                .addItemDecoration(new DividerItemDecoration(this.getContext() ,
                        DividerItemDecoration.VERTICAL)
                );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewContrato.setLayoutManager(linearLayoutManager);

        viewModel.getListContratosLiveData().observe(getViewLifecycleOwner(), new Observer<List<Contrato>>() {
            @Override
            public void onChanged(List<Contrato> contratos) {
                contratoAdapter = new ContratoAdapter(contratos, rootView.getContext(), inflater);
                recyclerViewContrato.setAdapter(contratoAdapter);
            }
        });

        viewModel.setListContratosLiveData();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}