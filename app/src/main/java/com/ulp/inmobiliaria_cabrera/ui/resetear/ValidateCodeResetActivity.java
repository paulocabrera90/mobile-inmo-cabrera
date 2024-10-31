package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.ActivityValidateCodeResetBinding;

public class ValidateCodeResetActivity extends AppCompatActivity {

    private ActivityValidateCodeResetBinding binding;
    private ValidateCodeResetActivityViewModel viewModel;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityValidateCodeResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(ValidateCodeResetActivityViewModel.class);
        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        email = getIntent().getStringExtra("email");

        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.updateCode(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.resetOkPassword(email);
            }
        });

        viewModel.getLoading().observe(this, isLoading -> {
            // Cambiar la visibilidad de loadingOverlay según el estado de carga
            loadingOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.isCodeValid().observe(this, isValid -> {
            binding.btnConfirm.setEnabled(isValid);
        });

        viewModel.getCode().observe(this, code -> {
            if (!code.equals(binding.etCode.getText().toString())) {
                binding.etCode.setText(code);
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        binding.getRoot().findViewById(R.id.loadingOverlay).setVisibility(View.GONE);
    }

}