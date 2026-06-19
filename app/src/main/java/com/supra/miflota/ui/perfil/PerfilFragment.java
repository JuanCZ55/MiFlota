package com.supra.miflota.ui.perfil;

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
import com.supra.miflota.databinding.FragmentPerfilBinding;

public class PerfilFragment extends Fragment {

    private PerfilViewModel vm;
    private FragmentPerfilBinding b;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        vm = new ViewModelProvider(this).get(PerfilViewModel.class);
        b = FragmentPerfilBinding.inflate(inflater, container, false);
        vm.getUsuario().observe(getViewLifecycleOwner(), usuario -> {
            b.etNombre.setText(usuario.getPersona().getNombre());
            b.etApellido.setText(usuario.getPersona().getApellido());
            b.etFechaNacimiento.setText(usuario.getPersona().getFechaNac());
            b.etDNI.setText(usuario.getPersona().getDni()+"");
            b.etEmail.setText(usuario.getGmail());
        });
        vm.getMsj().observe(getViewLifecycleOwner(), msj -> {
            if (msj != null && !msj.isEmpty()) {
                Toast.makeText(getContext(), msj, Toast.LENGTH_SHORT).show();
                vm.limpiarMsj();
            }
        });
        vm.getOperationSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                vm.cargarUsuario();
                vm.limpiarBoolean();
            }
        });
        b.btnEditar.setOnClickListener(v -> {
            vm.actualizarUsuario(b.etEmail.getText().toString());
        });
        b.btnCambiarPassword.setOnClickListener(v -> {
            Navigation.findNavController(b.getRoot()).navigate(R.id.action_nav_perfil_to_passwordFragment);
        });
        vm.cargarUsuario();
        return b.getRoot();
    }

}