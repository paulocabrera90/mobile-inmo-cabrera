package com.ulp.inmobiliaria_cabrera.ui.inquilinos;

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
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleBinding;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInquilinoBinding;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.models.Inquilino;
import com.ulp.inmobiliaria_cabrera.ui.inmuebles.InmuebleAdapter;
import com.ulp.inmobiliaria_cabrera.ui.inmuebles.InmuebleViewModel;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.util.List;

public class InquilinoFragment extends Fragment {

    private InquilinoViewModel viewModel;
    private FragmentInquilinoBinding binding;
    private RecyclerView recyclerViewInmueble;
    private InmuebleInquilinoAdapter inmuebleAdapter;
    private int ID_PROPIETARIO_LOG;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ID_PROPIETARIO_LOG = PreferencesUtil.getIdPropietario(getActivity());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(InquilinoViewModel.class);
        binding = FragmentInquilinoBinding.inflate(inflater, container, false);

        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        final View rootView = binding.getRoot();

        recyclerViewInmueble = (RecyclerView) rootView.findViewById(R.id.recyclerViewInmublesContrato);
        recyclerViewInmueble
                .addItemDecoration(new DividerItemDecoration(this.getContext() ,
                        DividerItemDecoration.VERTICAL)
                );

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewInmueble.setLayoutManager(linearLayoutManager);

        viewModel.getAvisoInquilinoData().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.tvAvisoEmptyList.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        viewModel.getListInmuebleContratoMutable().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmueblesContrato) {
                inmuebleAdapter = new InmuebleInquilinoAdapter(rootView.getContext(),inmueblesContrato, inflater);
                recyclerViewInmueble.setAdapter(inmuebleAdapter);
            }
        });

        viewModel.setListInmuebleContratoMutable();
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }


}