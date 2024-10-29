package com.ulp.inmobiliaria_cabrera.ui.logout;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.ulp.inmobiliaria_cabrera.databinding.FragmentLogoutBinding;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.ui.login.LoginActivity;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setIcon(android.R.drawable.ic_lock_idle_lock)
                .setMessage("¿Desea cerrar sesión?")
                .setPositiveButton("Sí", (dialogInterface, i) -> {
                    ApiClient.eliminarToken(requireContext());
                    startActivity(new Intent(requireContext(), LoginActivity.class));
                    requireActivity().finish(); // Cierra la actividad actual para evitar regresar a ella
                })
                .setNegativeButton("No", (dialogInterface, i) ->
                        Navigation.findNavController(requireView()).popBackStack())
                .show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}