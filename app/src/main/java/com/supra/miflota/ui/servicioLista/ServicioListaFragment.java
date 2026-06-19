package com.supra.miflota.ui.servicioLista;

import androidx.activity.OnBackPressedCallback;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.supra.miflota.R;
import com.supra.miflota.data.models.Service;
import com.supra.miflota.databinding.FragmentServicioListaBinding;

import java.util.ArrayList;
import java.util.List;

public class ServicioListaFragment extends Fragment {
    private ServicioListaViewModel vm;
    private FragmentServicioListaBinding b;
    private ServicioListaAdapter adapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentServicioListaBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ServicioListaViewModel.class);

        b.rvServicios.setLayoutManager(new LinearLayoutManager(getContext()));
//listenr click lista
        adapter = new ServicioListaAdapter(new ArrayList<>(), getLayoutInflater(), service -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("servicio", service);
            Navigation.findNavController(b.getRoot()).navigate(R.id.action_nav_servicio_lista_to_servicioFragment, bundle);
        });
        b.rvServicios.setAdapter(adapter);
        /// cargar datos del vehiculo
        vm.getVehiculoMutable().observe(getViewLifecycleOwner(), vehiculo -> {
            b.tvPatente.setText(vehiculo.getPatente());
            b.tvMarca.setText(vehiculo.getMarca().concat(" - ").concat(vehiculo.getModelo()));
        });
        //
        vm.getStatusMutable().observe(getViewLifecycleOwner(), status -> {
            vm.dispararBusquedaRevisiones();
        });
        // observer carga lista de servicios
        vm.getServiceList().observe(getViewLifecycleOwner(), lista -> {
            adapter.setServiceList(lista);
        });


/// mensaje de error
        vm.getErrorMessage().observe(getViewLifecycleOwner(), msj -> {
            if (msj != null && !msj.isEmpty()) {
                Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
                vm.clearErrorMessage();
            }
        });
        /// boton agregar
        b.fabAgregarVehiculo.setOnClickListener(v -> {
            Navigation.findNavController(b.getRoot()).navigate(R.id.action_nav_servicio_lista_to_servicioFragment);
        });
        /// Filtrar por todas las servicios
        b.tgFiltros1.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                vm.setFiltros(checkedId == R.id.btnPropio, null);
            }
        });

        /// Filtrar por servicios propias
        b.tgFiltros2.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                vm.setFiltros(null, checkedId == R.id.bAlta);
            }
        });

        /// Accion del retroceso
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getView()).navigate(R.id.nav_vehiculo);
            }
        });

        vm.cargarVehiculo();
        return b.getRoot();
    }


}
