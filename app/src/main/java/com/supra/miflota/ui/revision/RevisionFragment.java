package com.supra.miflota.ui.revision;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.supra.miflota.databinding.FragmentRevisionBinding;

public class RevisionFragment extends Fragment {
    private FragmentRevisionBinding binding;

    private RevisionViewModel viewModel;

    public static RevisionFragment newInstance() {
        return new RevisionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRevisionBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(this).get(RevisionViewModel.class);

        Bundle bundle = getArguments();

        if(bundle != null){
            viewModel.cargarRevision(bundle);
        }


        /// Cargar datos del vehiculo
        viewModel.getVehiculoMutable().observe(getViewLifecycleOwner(), vehiculo -> {
            binding.tvPatente.setText(vehiculo.getPatente());
            binding.tvMarca.setText(vehiculo.getMarca().concat(" - ").concat(vehiculo.getModelo()));
        });

        /// Cargar datos de la revisión
        viewModel.getRevisionMutable().observe(getViewLifecycleOwner(), revision -> {
            binding.swFaroDelanteroIzquierdo.setChecked(revision.isFaroDelanteroIzquierdo());
            binding.swFaroDelanteroDerecho.setChecked(revision.isFaroDelanteroDerecho());
            binding.swFaroTraseroIzquierdo.setChecked(revision.isFaroTraseroIzquierdo());
            binding.swFaroTraseroDerecho.setChecked(revision.isFaroTraseroDerecho());

            binding.swNivelAceite.setChecked(revision.isNivelAceite());
            binding.swLiquidoFrenos.setChecked(revision.isLiquidoFrenos());
            binding.swNivelRefrigerante.setChecked(revision.isNivelRefrigerante());
            binding.swNivelAguaParabrisa.setChecked(revision.isNivelAguaParabrisas());

            binding.swPresionNeumaticos.setChecked(revision.isPresionNeumaticos());
            binding.swNivelFreno.setChecked(revision.isNivelFrenos());
            binding.swMatafuegoVigente.setChecked(revision.isMatafuegoVigente());

            binding.etObservaciones.setText(revision.getObservaciones());

            binding.swEstado.setChecked(revision.isEstado());

            binding.btnGuardarRevision.setVisibility(View.GONE);
            binding.btnActualizarRevision.setVisibility(View.VISIBLE);
            if(!revision.isCurrentUser()){
                binding.btnActualizarRevision.setVisibility(View.GONE);

                binding.swEstado.setEnabled(false);
                binding.swFaroDelanteroIzquierdo.setEnabled(false);
                binding.swFaroDelanteroDerecho.setEnabled(false);
                binding.swFaroTraseroIzquierdo.setEnabled(false);
                binding.swFaroTraseroDerecho.setEnabled(false);

                binding.swNivelAceite.setEnabled(false);
                binding.swLiquidoFrenos.setEnabled(false);
                binding.swNivelRefrigerante.setEnabled(false);
                binding.swNivelAguaParabrisa.setEnabled(false);

                binding.swPresionNeumaticos.setEnabled(false);
                binding.swNivelFreno.setEnabled(false);
                binding.swMatafuegoVigente.setEnabled(false);

                binding.etObservaciones.setFocusable(false);
                binding.etObservaciones.setClickable(false);
            }
        });

        viewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage -> {
            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
        });

        /// Accion para registrar una nueva revision
        binding.btnGuardarRevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.crearRevision(
                        binding.swFaroDelanteroIzquierdo.isChecked(),
                        binding.swFaroDelanteroDerecho.isChecked(),
                        binding.swFaroTraseroIzquierdo.isChecked(),
                        binding.swFaroTraseroDerecho.isChecked(),
                        binding.swLiquidoFrenos.isChecked(),
                        binding.swNivelAceite.isChecked(),
                        binding.swPresionNeumaticos.isChecked(),

                        binding.swNivelFreno.isChecked(),
                        binding.swNivelRefrigerante.isChecked(),
                        binding.swNivelAguaParabrisa.isChecked(),
                        binding.swMatafuegoVigente.isChecked(),

                        String.valueOf(binding.etObservaciones.getText()),
                        binding.swEstado.isChecked()
                );
            }
        });

        /// Accion para actualizar una nueva revision
        binding.btnActualizarRevision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.editarRevision(
                        binding.swFaroDelanteroIzquierdo.isChecked(),
                        binding.swFaroDelanteroDerecho.isChecked(),
                        binding.swFaroTraseroIzquierdo.isChecked(),
                        binding.swFaroTraseroDerecho.isChecked(),
                        binding.swLiquidoFrenos.isChecked(),
                        binding.swNivelAceite.isChecked(),
                        binding.swPresionNeumaticos.isChecked(),

                        binding.swNivelFreno.isChecked(),
                        binding.swNivelRefrigerante.isChecked(),
                        binding.swNivelAguaParabrisa.isChecked(),
                        binding.swMatafuegoVigente.isChecked(),

                        String.valueOf(binding.etObservaciones.getText()),
                        binding.swEstado.isChecked()
                );

            }
        });


        viewModel.getSuccessMessage().observe(getViewLifecycleOwner(), successMessage -> {
            Toast.makeText(getContext(), successMessage, Toast.LENGTH_SHORT).show();
        });

        viewModel.cargarVehiculo();

        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

}