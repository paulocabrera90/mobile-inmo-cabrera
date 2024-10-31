package com.ulp.inmobiliaria_cabrera;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.ulp.inmobiliaria_cabrera.databinding.ActivityMainBinding;
import com.ulp.inmobiliaria_cabrera.databinding.NavHeaderMainBinding;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;
import com.ulp.inmobiliaria_cabrera.ui.login.LoginActivity;
import com.ulp.inmobiliaria_cabrera.ui.register.SharedViewModel;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null)
                        .setAnchorView(R.id.fab).show();
            }
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        viewModel = ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication()).create(MainActivityViewModel.class);

        setHeader(navigationView);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_contratos,
                R.id.nav_profile,
                R.id.nav_inmuebles)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setHeader(NavigationView navigationView) {

        NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        SharedViewModel sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        sharedViewModel.getNombreCompleto().observe(this, headerBinding.textViewNombreNavHeader::setText);
        sharedViewModel.getEmail().observe(this, headerBinding.textViewEmailNavHeader::setText);
     //   sharedViewModel. IMAGE
    }

    public void logout(MenuItem item) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_lock_idle_lock)
                .setTitle("¿Realmente desea cerrar sesión?")
                .setCancelable(false)
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton("Sí", (dialogInterface, i) -> {
                    ApiClient.eliminarToken(this.getApplicationContext());
                    startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
                    this.finish(); // Cierra la actividad actual para evitar regresar a ella
                }).show();
    }
}