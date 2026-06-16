package com.supra.miflota.ui.vehiculoLista;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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
        vehiculoListaViewModel.getMutableLiveDataListaVehiculos().observe(getViewLifecycleOwner(), new Observer<List<Vehiculo>>() {
            @Override
            public void onChanged(List<Vehiculo> vehiculos) {
                VehiculoListarAdapter adapter = new VehiculoListarAdapter(vehiculos, getLayoutInflater(), new VehiculoListarAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Vehiculo vehiculo) {

                    }
                });
            }
        });
        vehiculoListaViewModel.cargarListadoDeVehiculos();
        return binding.getRoot();
    }



}