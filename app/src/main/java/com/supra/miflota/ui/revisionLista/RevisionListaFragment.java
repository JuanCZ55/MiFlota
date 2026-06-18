package com.supra.miflota.ui.revisionLista;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.supra.miflota.R;
import com.supra.miflota.data.models.ChecklistDiario;
import com.supra.miflota.databinding.FragmentRevisionListaBinding;

import java.util.List;

public class RevisionListaFragment extends Fragment {
    private FragmentRevisionListaBinding binding;
    private RevisionListaViewModel viewModel;
    private RevisionListaAdapter adapter;

    public static RevisionListaFragment newInstance() {
        return new RevisionListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRevisionListaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RevisionListaViewModel.class);

        binding.listItem.setLayoutManager(new LinearLayoutManager(getContext()));

        /// Crear adapter
        adapter = new RevisionListaAdapter(new java.util.ArrayList<>(), getLayoutInflater(), revision -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("Revision", revision);
            androidx.navigation.Navigation.findNavController(requireView())
                    .navigate(R.id.nav_revision, bundle);
        });
        binding.listItem.setAdapter(adapter);

        /// Cargar datos del vehiculo
        viewModel.getVehiculoMutable().observe(getViewLifecycleOwner(), vehiculo -> {
            binding.tvPatente.setText(vehiculo.getPatente());
            binding.tvMarca.setText(vehiculo.getMarca().concat(" - ").concat(vehiculo.getModelo()));
        });

        /// Disparar metodo para obtener la lista de revisiones
        viewModel.getStatusMutable().observe(getViewLifecycleOwner(), status -> {
            viewModel.dispararBusquedaRevisiones();
        });


        /// Cargar lista de revisiones
        viewModel.getListaRevisiones().observe(getViewLifecycleOwner(), new Observer<List<ChecklistDiario>>() {
            @Override
            public void onChanged(List<ChecklistDiario> checklistDiarios) {
                adapter.updateList(checklistDiarios);
            }
        });

        /// Mostrar mensaje de error
        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });

        /// Filtrar por todas las revisiones
        binding.tgFiltros1.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                viewModel.setFiltros(checkedId == R.id.btnPropio, null);
            }
        });

        /// Filtrar por revisiones propias
        binding.tgFiltros2.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                viewModel.setFiltros(null, checkedId == R.id.bAlta);
            }
        });

        binding.flotante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                androidx.navigation.Navigation.findNavController(requireView())
                        .navigate(R.id.nav_revision);
            }
        });

        viewModel.cargarVehiculo();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.dispararBusquedaRevisiones();
    }

}