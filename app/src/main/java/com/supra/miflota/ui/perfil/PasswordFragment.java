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
import com.supra.miflota.databinding.FragmentPasswordBinding;

public class PasswordFragment extends Fragment {

    private PasswordViewModel vm;
    private FragmentPasswordBinding b;

    public static PasswordFragment newInstance() {
        return new PasswordFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = com.supra.miflota.databinding.FragmentPasswordBinding.inflate(inflater, container, false);
        vm = new ViewModelProvider(this).get(PasswordViewModel.class);

        // Observar los mensajes
        vm.getMsj().observe(getViewLifecycleOwner(), msj -> {
            if (msj != null && !msj.isEmpty()) {
                Toast.makeText(getContext(), msj, android.widget.Toast.LENGTH_SHORT).show();
                vm.limpiarMsj();
            }
        });

        //cambiar contraseña
        b.btnCambiarPassword.setOnClickListener(v -> {
            String actual = b.etPasswordActual.getText().toString();
            String nueva = b.etPasswordNueva.getText().toString();
            vm.cambiarPassword(actual, nueva);
        });
        vm.getOperacionExitosa().observe(getViewLifecycleOwner(), exitoso -> {
            if (exitoso) {
                vm.limpiarOperacionExitosa();
                Navigation.findNavController(b.getRoot()).popBackStack();
            }
        });
        return b.getRoot();
    }
}