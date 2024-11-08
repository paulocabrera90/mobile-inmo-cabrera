package com.ulp.inmobiliaria_cabrera.ui.inmuebles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentInmuebleBinding;
import com.ulp.inmobiliaria_cabrera.models.Inmueble;
import com.ulp.inmobiliaria_cabrera.utils.PreferencesUtil;

import java.util.List;

public class InmuebleFragment extends Fragment {

    private RecyclerView recyclerViewInmueble;
    private FragmentInmuebleBinding binding;
    private InmuebleViewModel viewModel;
    private InmuebleAdapter inmuebleAdapter;
    private int ID_PROPIETARIO_LOG;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ID_PROPIETARIO_LOG = PreferencesUtil.getIdPropietario(getActivity());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(InmuebleViewModel.class);
        binding = FragmentInmuebleBinding.inflate(inflater, container, false);

        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        final View rootView = binding.getRoot();

        recyclerViewInmueble = (RecyclerView) rootView.findViewById(R.id.recyclerViewInmuebles);
        recyclerViewInmueble
                .addItemDecoration(new DividerItemDecoration(this.getContext() ,
                        DividerItemDecoration.VERTICAL)
                );

        viewModel.getLoading().observe(getViewLifecycleOwner() , isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getAvisoListInmueble().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                binding.tvAvisoEmptyList.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        recyclerViewInmueble.setLayoutManager(linearLayoutManager);

        viewModel.getListaInmuebles().observe(getViewLifecycleOwner(), new Observer<List<Inmueble>>() {
            @Override
            public void onChanged(List<Inmueble> inmuebles) {
                inmuebleAdapter = new InmuebleAdapter(inmuebles, rootView.getContext(), inflater);
                recyclerViewInmueble.setAdapter(inmuebleAdapter);
            }
        });

        binding.fabAddInmuebles.setOnClickListener(view -> {
            NavController navController = NavHostFragment.findNavController(this);
            Bundle bundle = new Bundle();
            bundle.putInt("idPropietario", ID_PROPIETARIO_LOG);
            bundle.putBoolean("newInmueble", true);
            navController.navigate(R.id.action_nav_inmuebles_to_nav_inmueble_detalle, bundle);
        });

        viewModel.setListaInmuebles(ID_PROPIETARIO_LOG);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }

}