package com.ulp.inmobiliaria_cabrera.ui.register;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulp.inmobiliaria_cabrera.R;

public class PerfilCambioPasswordFragment extends Fragment {

    private PerfilCambioPasswordViewModel viewModel;

    public static PerfilCambioPasswordFragment newInstance() {
        return new PerfilCambioPasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_perfil_cambio_password, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(PerfilCambioPasswordViewModel.class);
        // TODO: Use the ViewModel
    }

}