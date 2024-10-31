package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.ActivityResetChangePasswordBinding;

public class ResetChangePasswordActivity extends AppCompatActivity {
    private ActivityResetChangePasswordBinding binding;
    private ResetChangePasswordActivityViewModel viewModel;
    private String email;
    private String verificationNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(ResetChangePasswordActivityViewModel.class);

        email = getIntent().getStringExtra("email");
        verificationNumber = getIntent().getStringExtra("verificationNumber");

        binding.btnChangePassword.setOnClickListener(view -> {
            String newPassword = binding.etNewPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();
            viewModel.resetPassword(email, newPassword, confirmPassword, verificationNumber);
        });

        viewModel.getLoading().observe(this, isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }

}