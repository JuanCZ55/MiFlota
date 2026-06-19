package com.supra.miflota.ui.servicioLista;

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

    private boolean verMisServicios = false;
    private boolean verAlta = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentServicioListaBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ServicioListaViewModel.class);

        b.rvServicios.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ServicioListaAdapter(new ArrayList<>(), getLayoutInflater(), service -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("servicio", service);
            Navigation.findNavController(b.getRoot()).navigate(R.id.action_nav_servicio_lista_to_servicioFragment, bundle);
        });
        b.rvServicios.setAdapter(adapter);

        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary);
        int colorBackground = ContextCompat.getColor(requireContext(), R.color.background);
/// filtro Todos
        b.btnTodos.setOnClickListener(v -> {
            verMisServicios = false;
            alternarColores(b.btnTodos, b.btnPropio, colorPrimary, colorBackground);
            recargarLista();
        });
/// filtro Mis Servicios
        b.btnPropio.setOnClickListener(v -> {
            verMisServicios = true;
            alternarColores(b.btnPropio, b.btnTodos, colorPrimary, colorBackground);
            recargarLista();
        });
/// filtro alta
        b.bAlta.setOnClickListener(v -> {
            verAlta = true;
            alternarColores(b.bAlta, b.bBaja, colorPrimary, colorBackground);
            recargarLista();
        });
/// filtro baja
        b.bBaja.setOnClickListener(v -> {
            verAlta = false;
            alternarColores(b.bBaja, b.bAlta, colorPrimary, colorBackground);
            recargarLista();
        });

/// observer de la lista
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
        recargarLista();

        return b.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        recargarLista();
    }

    private void recargarLista() {
        vm.listServicios(verMisServicios, verAlta);
    }


    private void alternarColores(View activo, View inactivo, int colorPrimario, int colorFondo) {
        activo.setBackgroundTintList(ColorStateList.valueOf(colorPrimario));
        inactivo.setBackgroundTintList(ColorStateList.valueOf(colorFondo));
    }
}
