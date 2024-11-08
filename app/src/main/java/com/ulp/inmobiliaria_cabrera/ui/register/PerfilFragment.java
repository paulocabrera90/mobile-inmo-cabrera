package com.ulp.inmobiliaria_cabrera.ui.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentPerfilBinding;
import com.ulp.inmobiliaria_cabrera.models.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;
    private SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(PerfilViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);

        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);

        init();

        viewModel.getLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        return binding.getRoot();
    }

    private void init() {

        viewModel.getBtnEditEnable().observe(getViewLifecycleOwner(),
                enabled -> binding.buttonEdit.setEnabled(enabled));
        viewModel.getBtnSaveEnable().observe(getViewLifecycleOwner(),
                enabled -> binding.buttonSave.setEnabled(enabled));

        viewModel.getCurrentUser().observe(
                getViewLifecycleOwner(), propietario -> {
                    binding.editTextEmail.setText(propietario.getEmail());
                    binding.editTextNombre.setText(propietario.getNombre());
                    binding.editTextApellido.setText(propietario.getApellido());
                    binding.editTextDni.setText(propietario.getDni());
                    binding.editTextTelefonoArea.setText(propietario.getTelefono_area());
                    binding.editTextTelefonoNumero.setText(propietario.getTelefono_numero());
                    binding.editTextDireccion.setText(propietario.getDireccion());
                }
        );

        viewModel.getEditEnabled().observe(getViewLifecycleOwner(), flag -> {
        //    binding.editTextEmail.setEnabled(flag);
            binding.editTextNombre.setEnabled(flag);
            binding.editTextApellido.setEnabled(flag);
            binding.editTextDni.setEnabled(flag);
            binding.editTextTelefonoArea.setEnabled(flag);
            binding.editTextTelefonoNumero.setEnabled(flag);
            binding.editTextDireccion.setEnabled(flag);
        });

        viewModel.getNavigateIdPropietario().observe(getViewLifecycleOwner(), idPropietario -> {
                Bundle bundle = new Bundle();
                bundle.putString("idPropietario", idPropietario);

                NavController navController = NavHostFragment.findNavController(this);
                navController.navigate(R.id.action_nav_profile_to_passFragment, bundle);
        });

        binding.buttonSave.setOnClickListener(view -> {
            Propietario propietario = new Propietario(
                    binding.editTextDni.getText().toString(),
                    binding.editTextNombre.getText().toString(),
                    binding.editTextApellido.getText().toString(),
                    binding.editTextEmail.getText().toString(),
                    binding.editTextDireccion.getText().toString(),
                    binding.editTextTelefonoArea.getText().toString(),
                    binding.editTextTelefonoNumero.getText().toString()

            );
            viewModel.saveChanges(propietario);
            sharedViewModel.actualizarDatosPropietario(propietario.getNombreCompleto(), propietario.getEmail());

        });

        binding.buttonEdit.setOnClickListener(view -> viewModel.enableEdit());
        binding.buttonChangePassword.setOnClickListener(view -> viewModel.changePassword());

        viewModel.setCurrentUser();
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
