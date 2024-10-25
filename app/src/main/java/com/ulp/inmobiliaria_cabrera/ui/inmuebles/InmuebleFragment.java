package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleBinding;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;

import java.util.List;

public class InmuebleFragment extends Fragment {

    private RecyclerView recyclerViewInmueble;
    private FragmentInmuebleBinding binding;
    private InmuebleViewModel viewModel;
    private InmuebleAdapter inmuebleAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(InmuebleViewModel.class);
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);

        final View rootView = binding.getRoot();

        recyclerViewInmueble = (RecyclerView) rootView.findViewById(R.id.recyclerViewInmuebles);
        recyclerViewInmueble
                .addItemDecoration(new DividerItemDecoration(this.getContext() ,
                        DividerItemDecoration.VERTICAL)
                );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewInmueble.setLayoutManager(linearLayoutManager);

        viewModel.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                inmuebleAdapter = new InmuebleAdapter(inmuebles, rootView.getContext(), inflater);
                recyclerViewInmueble.setAdapter(inmuebleAdapter);
            }
        });
        viewModel.setListaInmuebles();

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}