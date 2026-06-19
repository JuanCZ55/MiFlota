package com.supra.miflota.ui.servicio;

import static android.view.View.*;

import androidx.activity.OnBackPressedCallback;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.supra.miflota.R;
import com.supra.miflota.data.models.Service;
import com.supra.miflota.databinding.FragmentServicioBinding;

public class ServicioFragment extends Fragment {

    private ServicioViewModel vm;
    private FragmentServicioBinding b;


    public static ServicioFragment newInstance() {
        return new ServicioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = FragmentServicioBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(ServicioViewModel.class);

        vm.initialize(getArguments());
        vm.getService().observe(getViewLifecycleOwner(), servicio -> {

            b.etFecha.setText(servicio.getFecha());
            b.etKilometraje.setText(String.valueOf(servicio.getKmService()));

            b.cbAceite.setChecked(servicio.isAceite());
            b.cbAlineoBalanceo.setChecked(servicio.isAlineoBalanceo());

            b.cbBombaAceite.setChecked(servicio.isBombaAceite());
            b.cbBombaAgua.setChecked(servicio.isBombaAgua());
            b.cbBombaCombustible.setChecked(servicio.isBombaCombustible());

            b.cbBujias.setChecked(servicio.isBujias());

            b.cbCorreaDentada.setChecked(servicio.isCorreaDentada());
            b.cbCorreaPolyV.setChecked(servicio.isCorreaPolyV());

            b.cbFiltroDeAceite.setChecked(servicio.isFiltroDeAceite());
            b.cbFiltroDeAire.setChecked(servicio.isFiltroDeAire());
            b.cbFiltroDeCombustible.setChecked(servicio.isFiltroDeCombustible());

            b.etProveedor.setText(servicio.getProveedor());
            b.etDetalle.setText(servicio.getDetalle());

            b.etDetalle2.setText(servicio.getServicioExcepcional());

            b.cbRealizado.setChecked(servicio.isRealizado());
            b.cbEstado.setChecked(servicio.isEstado());
        });

        // Observador para el modo
        vm.getModo().observe(getViewLifecycleOwner(), modo -> {
            // 0 = Crear, 1 = Editar, 2 = Ver

            // ocultar fecha cuando se crea
            b.etFecha.setVisibility(modo == 0 ? GONE : VISIBLE);
            b.tvFecha.setVisibility(modo == 0 ? GONE : VISIBLE);
            if (modo == 0) {
                b.cbEstado.setChecked(true);
            }
            b.etFecha.setEnabled(false); // Siempre desactivado

            // Controlar visibilidad de botones
            b.bCrear.setVisibility(modo == 0 ? VISIBLE : GONE);
            b.bEditar.setVisibility(modo == 1 ? VISIBLE : GONE);

            // Habilitar campos si el modo es Crear o Editar
            boolean habilitarCampos = false;
            if (modo == 0 || modo == 1) {
                habilitarCampos = true;
            }
            setCamposEditables(habilitarCampos);
        });
        // Acción para el botón Crear
        b.bCrear.setOnClickListener(v -> {
            Service s = getServiceFromFields();
            vm.crearServicio(s);
        });

        // Acción para el botón Editar
        b.bEditar.setOnClickListener(v -> {
            Service s = getServiceFromFields();
            vm.actualizarServicio(s);
        });

        // Volver atrás al terminar con éxito
        vm.getOperationSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Guardado exitosamente", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(b.getRoot()).popBackStack();
            }
        });
        vm.getMsj().observe(getViewLifecycleOwner(), msj -> {
            Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
        });
        return b.getRoot();
    }

    private Service getServiceFromFields() {
        // Obtenemos ID ya asignado en modo edición
        Service servicio = vm.getService().getValue();
        if (servicio == null) {
            servicio = new Service();
        }

        try {
            String kmStr = b.etKilometraje.getText().toString().trim();
            servicio.setKmService(kmStr.isEmpty() ? 0 : Integer.parseInt(kmStr));
        } catch (NumberFormatException e) {
            servicio.setKmService(0);
        }

        servicio.setAceite(b.cbAceite.isChecked());
        servicio.setAlineoBalanceo(b.cbAlineoBalanceo.isChecked());
        servicio.setBombaAceite(b.cbBombaAceite.isChecked());
        servicio.setBombaAgua(b.cbBombaAgua.isChecked());
        servicio.setBombaCombustible(b.cbBombaCombustible.isChecked());
        servicio.setBujias(b.cbBujias.isChecked());
        servicio.setCorreaDentada(b.cbCorreaDentada.isChecked());
        servicio.setCorreaPolyV(b.cbCorreaPolyV.isChecked());
        servicio.setFiltroDeAceite(b.cbFiltroDeAceite.isChecked());
        servicio.setFiltroDeAire(b.cbFiltroDeAire.isChecked());
        servicio.setFiltroDeCombustible(b.cbFiltroDeCombustible.isChecked());

        servicio.setProveedor(b.etProveedor.getText().toString().trim());
        servicio.setDetalle(b.etDetalle.getText().toString().trim());
        servicio.setServicioExcepcional(b.etDetalle2.getText().toString().trim());

        servicio.setRealizado(b.cbRealizado.isChecked());
        servicio.setEstado(b.cbEstado.isChecked());

        return servicio;
    }

    private void setCamposEditables(boolean enabled) {
        b.etKilometraje.setEnabled(enabled);
        b.cbAceite.setEnabled(enabled);
        b.cbAlineoBalanceo.setEnabled(enabled);
        b.cbBombaAceite.setEnabled(enabled);
        b.cbBombaAgua.setEnabled(enabled);
        b.cbBombaCombustible.setEnabled(enabled);
        b.cbBujias.setEnabled(enabled);
        b.cbCorreaDentada.setEnabled(enabled);
        b.cbCorreaPolyV.setEnabled(enabled);
        b.cbFiltroDeAceite.setEnabled(enabled);
        b.cbFiltroDeAire.setEnabled(enabled);
        b.cbFiltroDeCombustible.setEnabled(enabled);
        b.etProveedor.setEnabled(enabled);
        b.etDetalle.setEnabled(enabled);
        b.etDetalle2.setEnabled(enabled);
        b.cbRealizado.setEnabled(enabled);
        b.cbEstado.setEnabled(enabled);
    }

}