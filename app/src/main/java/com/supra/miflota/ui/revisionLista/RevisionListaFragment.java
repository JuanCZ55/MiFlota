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

    public static RevisionListaFragment newInstance() {
        return new RevisionListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRevisionListaBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RevisionListaViewModel.class);

        binding.listItem.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getVehiculoMutable().observe(getViewLifecycleOwner(), vehiculo -> {
            binding.tvPatente.setText(vehiculo.getPatente());
            binding.tvMarca.setText(vehiculo.getMarca().concat(" - ").concat(vehiculo.getModelo()));
            viewModel.cargarRevisiones(vehiculo.getIdVehiculo(), false, true);
        });

        viewModel.getListaRevisiones().observe(getViewLifecycleOwner(), new Observer<List<ChecklistDiario>>() {
            @Override
            public void onChanged(List<ChecklistDiario> checklistDiarios) {

                RevisionListaAdapter adapter = new RevisionListaAdapter(checklistDiarios, getLayoutInflater(), new RevisionListaAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(ChecklistDiario revision) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Revision", revision);
                        androidx.navigation.Navigation.findNavController(requireView())
                                .navigate(R.id.nav_revision, bundle);
                    }
                });
                binding.listItem.setAdapter(adapter);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), mensaje -> {
            Toast.makeText(getContext(), mensaje, Toast.LENGTH_SHORT).show();
        });

        viewModel.cargarVehiculoYRevisiones();
        return binding.getRoot();
    }


}