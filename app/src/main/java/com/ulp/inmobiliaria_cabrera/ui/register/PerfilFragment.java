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
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.databinding.FragmentPerfilBinding;

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

        return binding.getRoot();
    }

    private void init() {

        viewModel.getCurrentUser().observe(
                getViewLifecycleOwner(), propietario -> {
                    binding.editTextEmail.setText(propietario.getEmail());
                    binding.editTextNombre.setText(propietario.getNombre());
                    binding.editTextApellido.setText(propietario.getApellido());
                    binding.editTextDni.setText(propietario.getDni());
                    binding.editTextTelefonoArea.setText(propietario.getTelefonoArea());
                    binding.editTextTelefonoNumero.setText(propietario.getTelefonoNumero());
                    binding.editTextDireccion.setText(propietario.getDireccion());
                }
        );
     //   viewModel.getCurrentUser()
             //   .observe(getViewLifecycleOwner(), visibility -> binding.buttonEdit.setVisibility(visibility));
     //   viewModel.getButtonSaveVisibility().observe(getViewLifecycleOwner(), visibility -> binding.buttonSave.setVisibility(visibility));

        viewModel.setCurrentUser();
    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        viewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
//       /// abrirGaleria();
//
//        viewModel.getAvisoMutable().observe(getViewLifecycleOwner(), s -> binding.tvAviso.setText(s));
//        viewModel.getAvisoVisibilityMutable().observe(getViewLifecycleOwner(), visibility -> binding.tvAviso.setVisibility(visibility));
//
//        viewModel.getUriMutable().observe(getViewLifecycleOwner(), uri -> binding.imageViewProfile.setImageURI(uri));
//
//        binding.buttonGaleria.setOnClickListener(v -> arl.launch(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)));
//
//        boolean flag = getActivity().getIntent().getBooleanExtra("isUser", false);
//        binding.buttonRegister.setText(flag ? "Editar" : "Registrar");
//        binding.buttonRegister.setOnClickListener(v -> {
//            String dni = binding.editTextDni.getText().toString();
//            String nombre = binding.editTextNombre.getText().toString();
//            String apellido = binding.editTextApellido.getText().toString();
//            String correo = binding.editTextEmail.getText().toString();
//            String password = binding.editTextContrasena.getText().toString();
//            // Handle save
//        });


//    }

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
