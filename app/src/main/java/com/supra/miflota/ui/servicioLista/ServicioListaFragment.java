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

import java.util.List;

public class ServicioListaFragment extends Fragment {

    private ServicioListaViewModel vm;

    private FragmentServicioListaBinding b;

    public static ServicioListaFragment newInstance() {
        return new ServicioListaFragment();
    }

    private boolean verMisServicios = false;
    private boolean verAlta = true;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentServicioListaBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ServicioListaViewModel.class);

        // Configurar RecyclerView
        b.rvServicios.setLayoutManager(new LinearLayoutManager(getContext()));

        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary);
        int colorBackground = ContextCompat.getColor(requireContext(), R.color.background);

        b.btnTodos.setOnClickListener(v -> {
            verMisServicios = false;
            alternarColores(b.btnTodos, b.btnPropio, colorPrimary, colorBackground);
            recargarLista();
        });

        b.btnPropio.setOnClickListener(v -> {
            verMisServicios = true;
            alternarColores(b.btnPropio, b.btnTodos, colorPrimary, colorBackground);
            recargarLista();
        });

        b.bAlta.setOnClickListener(v -> {
            verAlta = true;
            alternarColores(b.bAlta, b.bBaja, colorPrimary, colorBackground);
            recargarLista();
        });

        b.bBaja.setOnClickListener(v -> {
            verAlta = false;
            alternarColores(b.bBaja, b.bAlta, colorPrimary, colorBackground);
            recargarLista();
        });

        vm.getServiceList().observe(getViewLifecycleOwner(), lista -> {

            ServicioListaAdapter adapter = new ServicioListaAdapter(lista, getLayoutInflater(), service -> {
                Bundle bundle = new Bundle();
                bundle.putSerializable("servicio", service);
                Navigation.findNavController(b.getRoot()).navigate(R.id.action_nav_servicio_lista_to_servicioFragment, bundle);
            });
            b.rvServicios.setAdapter(adapter);
        });

        vm.getErrorMessage().observe(getViewLifecycleOwner(), msj -> {
            Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
        });

        recargarLista();

        return b.getRoot();
    }

    private void recargarLista() {
        vm.listServicios(verMisServicios, verAlta);
    }


    private void alternarColores(View activo, View inactivo, int colorPrimario, int colorFondo) {
        activo.setBackgroundTintList(ColorStateList.valueOf(colorPrimario));
        inactivo.setBackgroundTintList(ColorStateList.valueOf(colorFondo));
    }
}
