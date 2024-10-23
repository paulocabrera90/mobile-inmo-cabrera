package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

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

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(ResetChangePasswordActivityViewModel.class);

        email = getIntent().getStringExtra("email");
        verificationNumber = getIntent().getStringExtra("verificationNumber");

        binding.btnChangePassword.setOnClickListener(view -> {
            String newPassword = binding.etNewPassword.getText().toString();
            String confirmPassword = binding.etConfirmPassword.getText().toString();
            viewModel.resetPassword(email, newPassword, confirmPassword, verificationNumber);
        });

    }
}