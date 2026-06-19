package com.supra.miflota.ui.vehiculoLista;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supra.miflota.R;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.databinding.FragmentVehiculoListaBinding;

import java.util.List;

public class VehiculoListaFragment extends Fragment {

    private VehiculoListaViewModel vehiculoListaViewModel;
    private FragmentVehiculoListaBinding binding;

    public static VehiculoListaFragment newInstance() {
        return new VehiculoListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        vehiculoListaViewModel = new ViewModelProvider(this).get(VehiculoListaViewModel.class);
        binding = FragmentVehiculoListaBinding.inflate(inflater, container, false);
        binding.rvListadoVehiculos.setLayoutManager(new LinearLayoutManager(getContext()));

        vehiculoListaViewModel.getMutableLiveDataListaVehiculos().observe(getViewLifecycleOwner(), new Observer<List<Vehiculo>>() {
            @Override
            public void onChanged(List<Vehiculo> vehiculos) {
                VehiculoListarAdapter adapter = new VehiculoListarAdapter(vehiculos, getLayoutInflater(), new VehiculoListarAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Vehiculo vehiculo) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("vehiculo", vehiculo);

                        NavOptions navOptions = new NavOptions.Builder()
                                .setPopUpTo(R.id.nav_vehiculo_lista, true)
                                .build();

                        Navigation.findNavController(getView()).navigate(R.id.nav_vehiculo, bundle, navOptions);
                    }
                });
                binding.rvListadoVehiculos.setAdapter(adapter);
            }
        });

        binding.btnSalirListadoVehiculos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Navigation.findNavController(getView()).navigate(R.id.nav_logout);
            }
        });
        vehiculoListaViewModel.cargarListadoDeVehiculos();

        vehiculoListaViewModel.limpiarDatosVehiculo();

        /// Accion del retroceso
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
        return binding.getRoot();
    }



}