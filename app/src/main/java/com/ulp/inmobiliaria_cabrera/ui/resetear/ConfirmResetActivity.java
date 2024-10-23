package com.ulp.inmobiliaria_cabrera.ui.resetear;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.ActivityConfirmResetBinding;

public class ConfirmResetActivity extends AppCompatActivity {

    private ActivityConfirmResetBinding binding;
    private ConfirmResetActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmResetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())
                .create(ConfirmResetActivityViewModel.class);

        binding.etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.updateCode(s.toString());
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.resetOkPassword();
            }
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
}