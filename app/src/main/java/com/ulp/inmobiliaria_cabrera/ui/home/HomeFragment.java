package com.ulp.inmobiliaria_cabrera.ui.home;

import static com.ulp.inmobiliaria_cabrera.constants.Constants.BEARING;
import static com.ulp.inmobiliaria_cabrera.constants.Constants.INMOBILIARIA_CABRERA;
import static com.ulp.inmobiliaria_cabrera.constants.Constants.INMOBILIARIA_CABRERA_NAME;
import static com.ulp.inmobiliaria_cabrera.constants.Constants.TILT;
import static com.ulp.inmobiliaria_cabrera.constants.Constants.ZOOM;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ulp.inmobiliaria_cabrera.R;
import com.ulp.inmobiliaria_cabrera.databinding.FragmentHomeBinding;
import com.ulp.inmobiliaria_cabrera.ui.login.LoginActivityViewModel;

public class HomeFragment extends Fragment {

    private GoogleMap mapStart;
    private FragmentHomeBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        LoginActivityViewModel viewModel = new ViewModelProvider(this).get(LoginActivityViewModel.class);
        viewModel.stopLoading();

        View loadingOverlay = binding.getRoot().findViewById(R.id.loadingOverlay);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            getChildFragmentManager().beginTransaction().replace(R.id.map, mapFragment).commit();
        }

        mapFragment.getMapAsync(new CurrentMap());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private class CurrentMap implements OnMapReadyCallback {
        @Override
        public void onMapReady(GoogleMap mapa) {
            mapStart= mapa;
            mapStart.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            CameraPosition camPos = new CameraPosition.Builder()
                    .target(INMOBILIARIA_CABRERA)
                    .zoom(ZOOM)
                    .bearing(BEARING)
                    .tilt(TILT)
                    .build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPos);
            MarkerOptions marcadorInmueble = new MarkerOptions().position(INMOBILIARIA_CABRERA);
            marcadorInmueble.title(INMOBILIARIA_CABRERA_NAME);

            mapa.animateCamera(cameraUpdate);
            mapa.addMarker(marcadorInmueble);
        }
    }
}