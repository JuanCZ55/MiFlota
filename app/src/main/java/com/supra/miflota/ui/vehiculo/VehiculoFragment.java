package com.supra.miflota.ui.vehiculo;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.Navigation;
import com.supra.miflota.R;
import com.supra.miflota.databinding.FragmentVehiculoBinding;

public class VehiculoFragment extends Fragment {
    private FragmentVehiculoBinding binding;
    private VehiculoViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentVehiculoBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(VehiculoViewModel.class);
        Bundle bundle = getArguments();
        viewModel.cargarVehiculo(bundle);

        /// Mostrar datos del vehiculo
        viewModel.getVehiculoMutable().observe(getViewLifecycleOwner(), vehiculo -> {
            binding.etPatente.setText(vehiculo.getPatente());
            binding.etMarca.setText(vehiculo.getMarca());
            binding.etAnio.setText(String.valueOf(vehiculo.getAnio()));
            binding.etColor.setText(vehiculo.getColor());
            binding.etNumeroChasis.setText(vehiculo.getNumeroChasis());
            binding.etNumeroMotor.setText(vehiculo.getNumeroMotor());
            binding.etCantNeumaticos.setText(String.valueOf(vehiculo.getCantidadNeumaticos()));
            binding.etCantAuxilios.setText(String.valueOf(vehiculo.getCantidadAuxilios()));
            if(vehiculo.getMatafuego() != null){
                binding.btnMatafuego.setVisibility(View.VISIBLE);
                binding.btnMatafuego.setOnClickListener(v -> {
                    MatafuegoDialog.dialog(
                        getContext(),
                        String.valueOf(vehiculo.getMatafuego().getNroSerie()),
                        vehiculo.getMatafuego().getProveedor(),
                        vehiculo.getMatafuego().getFechaCarga(),
                        vehiculo.getMatafuego().getFechaVencimiento());
                });
            }
        });

        viewModel.getMensajeError().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });

        NavController navController = NavHostFragment.findNavController(this);
        /// Accion del retroceso
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navController.navigate(R.id.nav_vehiculo_lista);
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

}
