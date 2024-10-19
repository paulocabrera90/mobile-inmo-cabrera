package com.ulp.inmobiliaria_cabrera.ui.register;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.databinding.FragmentPerfilBinding;
import com.ulp.inmobiliaria_cabrera.models.Propietario;

public class PerfilFragment extends Fragment {

    private PerfilViewModel viewModel;
    private FragmentPerfilBinding binding;
    private ActivityResultLauncher<Intent> arl;
    private Uri uriImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity()
                .getApplication()).create(PerfilViewModel.class);
        binding = FragmentPerfilBinding.inflate(inflater, container, false);
        init();

        viewModel.getAvisoMutable().observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                binding.tvAviso.setText(s);
            }
        });

        viewModel.getAvisoVisibilityMutable().observe(getActivity(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer visibility) {
                binding.tvAviso.setVisibility(visibility);
            }
        });
        return binding.getRoot();
    }

    private void init() {

        viewModel.getBtnEditVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonEdit.setEnabled(visibility));
        viewModel.getBtnSaveVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonSave.setEnabled(visibility));

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
            binding.editTextEmail.setEnabled(flag);
            binding.editTextNombre.setEnabled(flag);
            binding.editTextApellido.setEnabled(flag);
            binding.editTextDni.setEnabled(flag);
            binding.editTextTelefonoArea.setEnabled(flag);
            binding.editTextTelefonoNumero.setEnabled(flag);
            binding.editTextDireccion.setEnabled(flag);
           // binding.fechaNacimiento.setEnabled(flag);
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
        });

        binding.buttonEdit.setOnClickListener(view -> viewModel.enableEdit());

        viewModel.setCurrentUser();
    }

//    private void abrirGaleria() {
//        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        arl = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                viewModel.recibirFoto(result);
//            }
//        });
//    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
