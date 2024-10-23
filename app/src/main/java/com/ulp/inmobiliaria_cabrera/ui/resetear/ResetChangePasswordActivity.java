package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.databinding.ActivityResetChangePasswordBinding;

public class ResetChangePasswordActivity extends AppCompatActivity {
    private ActivityResetChangePasswordBinding binding;
    private ResetChangePasswordActivityViewModel viewModel;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResetChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(ResetChangePasswordActivityViewModel.class);

        email = getIntent().getStringExtra("email");

    }
}