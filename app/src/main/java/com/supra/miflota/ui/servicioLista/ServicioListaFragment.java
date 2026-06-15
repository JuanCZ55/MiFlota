package com.supra.miflota.ui.servicioLista;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supra.miflota.R;
import com.supra.miflota.data.models.Service;
import com.supra.miflota.databinding.FragmentServicioListaBinding;

import java.util.ArrayList;
import java.util.List;

public class ServicioListaFragment extends Fragment {

    private ServicioListaViewModel mViewModel;

    private FragmentServicioListaBinding b;

    public static ServicioListaFragment newInstance() {
        return new ServicioListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentServicioListaBinding.inflate(inflater, container, false);
        mViewModel = new ViewModelProvider(this).get(ServicioListaViewModel.class);
        listadoHard();

        int colorPrimary = ContextCompat.getColor(requireContext(), R.color.primary);
        int colorBackground = ContextCompat.getColor(requireContext(), R.color.background);

        b.btnTodos.setOnClickListener(v -> {
            alternarColores(b.btnTodos, b.btnPropio, colorPrimary, colorBackground);
        });

        b.btnPropio.setOnClickListener(v -> {
            alternarColores(b.btnPropio, b.btnTodos, colorPrimary, colorBackground);
        });

        b.bAlta.setOnClickListener(v -> {
            alternarColores(b.bAlta, b.bBaja, colorPrimary, colorBackground);
        });

        b.bBaja.setOnClickListener(v -> {
            alternarColores(b.bBaja, b.bAlta, colorPrimary, colorBackground);
        });

        return b.getRoot();
    }


    private void alternarColores(View activo, View inactivo, int colorPrimario, int colorFondo) {
        activo.setBackgroundTintList(ColorStateList.valueOf(colorPrimario));
        inactivo.setBackgroundTintList(ColorStateList.valueOf(colorFondo));
    }

    private void listadoHard() {
        RecyclerView rv = b.rvServicios;
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        List<Service> serviceList = new ArrayList<>();
        Service s1 = new Service(15000, "10/06/2026", "Taller Supra", true);
        Service s2 = new Service(30000, "12/06/2026", "Mecánica Rápida", false);
        Service s3 = new Service(45000, "15/06/2026", "Repuestos Pepe", true);

        serviceList.add(s1);
        serviceList.add(s2);
        serviceList.add(s3);

        serviceList.add(s1);
        serviceList.add(s2);
        serviceList.add(s3);
        serviceList.add(s1);
        serviceList.add(s2);
        serviceList.add(s3);
        serviceList.add(s1);
        serviceList.add(s2);
        serviceList.add(s3);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        ServicioListaAdapter adapter = new ServicioListaAdapter(serviceList, inflater);
        rv.setAdapter(adapter);

    }


}
