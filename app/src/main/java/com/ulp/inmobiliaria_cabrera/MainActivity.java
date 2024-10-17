package com.ulp.inmobiliaria_cabrera;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.ulp.inmobiliaria_cabrera.databinding.ActivityMainBinding;
import com.ulp.inmobiliaria_cabrera.databinding.NavHeaderMainBinding;
import com.ulp.inmobiliaria_cabrera.models.Propietario;
import com.ulp.inmobiliaria_cabrera.request.ApiClient;

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
                R.id.nav_contracts,
                R.id.nav_profile,
                R.id.nav_real_estates)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
        // Utilizamos View Binding para el header del NavigationView
        NavHeaderMainBinding headerBinding = NavHeaderMainBinding.bind(navigationView.getHeaderView(0));
        // Observar los cambios en los LiveData del ViewModel
        viewModel.getNombre().observe(this, headerBinding.textViewNombreNavHeader::setText);
        viewModel.getEmail().observe(this, headerBinding.textViewEmailNavHeader::setText);
//        viewModel.getAvatarUrl().observe(this, url -> {
//            Glide.with(this)
//                    .load(url)
//                    .into(headerBinding.imageViewAvatar);
//        });

        // Llamamos a fetchPropietario para cargar los datos del propietario
        viewModel.addHeadresNav();
    }
}