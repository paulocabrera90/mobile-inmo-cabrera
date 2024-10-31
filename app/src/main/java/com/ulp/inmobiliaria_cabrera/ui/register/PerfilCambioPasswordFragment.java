package com.ulp.inmobiliaria_cabrera.ui.register;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentPerfilCambioPasswordBinding;

public class PerfilCambioPasswordFragment extends Fragment {

    private PerfilCambioPasswordViewModel viewModel;
    private FragmentPerfilCambioPasswordBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this)
                .get(PerfilCambioPasswordViewModel.class);
        binding = FragmentPerfilCambioPasswordBinding
                .inflate(inflater, container, false);
        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        viewModel.getStatusMessage().observe(getViewLifecycleOwner(), message -> {
            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
        });

        viewModel.getNavigateBack().observe(getViewLifecycleOwner(), shouldNavigateBack -> {
            if (shouldNavigateBack) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        binding.btnChangePassword.setOnClickListener(v -> {
            String currentPassword = binding.etCurrentPassword.getText().toString();
            String newPassword = binding.etNewPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();
            viewModel.changePassword(currentPassword, newPassword, confirmPassword);

        });

        return binding.getRoot();
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