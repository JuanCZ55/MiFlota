package com.supra.miflota.ui.vehiculoLista;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.supra.miflota.R;

public class VehiculoListaFragment extends Fragment {

    private VehiculoListaViewModel mViewModel;

    public static VehiculoListaFragment newInstance() {
        return new VehiculoListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vehiculo_lista, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VehiculoListaViewModel.class);
        // TODO: Use the ViewModel
    }

}