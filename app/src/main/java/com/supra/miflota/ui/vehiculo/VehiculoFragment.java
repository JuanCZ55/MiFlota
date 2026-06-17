package com.supra.miflota.ui.vehiculo;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;
import com.supra.miflota.R;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.databinding.FragmentVehiculoBinding;

public class VehiculoFragment extends Fragment {
    private FragmentVehiculoBinding binding;
    private VehiculoViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVehiculoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(VehiculoViewModel.class);
        Bundle bundle = getArguments();
        NavController navController = NavHostFragment.findNavController(this);
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navController.navigate(R.id.nav_vehiculo_lista);
            }
        });

        viewModel.getVehiculoMutableLiveData().observe(getViewLifecycleOwner(), new Observer<Vehiculo>() {
            @Override
            public void onChanged(Vehiculo vehiculo) {
                binding.etMatricula.setText(vehiculo.getPatente());
                binding.etModelo.setText(vehiculo.getModelo());
                binding.etAno.setText( String.valueOf(vehiculo.getAnio()) );
                binding.etMarca.setText(vehiculo.getMarca());
                binding.etColor.setText(vehiculo.getColor());
                binding.etNeumaticos.setText(String.valueOf(vehiculo.getCantidadNeumaticos()));
                binding.etAuxilio.setText(String.valueOf(vehiculo.getCantidadAuxilios()));
                binding.etChasis.setText(String.valueOf(vehiculo.getNumeroChasis()));
                binding.etNMotor.setText(String.valueOf(vehiculo.getNumeroMotor()));
                if(vehiculo.getMatafuego() == null)
                    binding.btnVerMatafuego.setVisibility(GONE);
                else
                    binding.btnVerMatafuego.setVisibility(VISIBLE);

            }
        });
        viewModel.getVehiculo(bundle);
        return binding.getRoot();
    }

}
