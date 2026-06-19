package com.supra.miflota.ui.kilometraje;

import static android.view.View.GONE;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.supra.miflota.R;
import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.databinding.FragmentKilometrajeBinding;

public class kilometrajeFragment extends Fragment {

    private KilometrajeViewModel mViewModel;
    private FragmentKilometrajeBinding binding;
    public static kilometrajeFragment newInstance() {
        return new kilometrajeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(KilometrajeViewModel.class);
        binding = FragmentKilometrajeBinding.inflate(inflater, container, false);
        Bundle bundle = getArguments();
        mViewModel.descomprimirBundle(bundle);
        //Configurar Solo vista
        mViewModel.getIsOnlyView().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisibleBotones(0,1);   //QUitar la visibilidad a los botones
                    deshabilitarEscritura(1);//Deshabilitar la escritura
                }else{
                    setVisibleBotones(1,1);//Mostrar los botones
                    deshabilitarEscritura(0);// Deshabilitar la escritura
                }
            }
        });
        //configurar modo crear
        mViewModel.getIsCrear().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisibleBotones(1,0);
                    deshabilitarEscritura(0);
                    binding.btnCrearKilometraje.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String kilometrajeEscrito = binding.etKilometraje.getText().toString();
                            mViewModel.crearKilometraje(kilometrajeEscrito);
                        }
                    });
                }
            }
        });
        //configurar modo editar
        mViewModel.getIsEdicion().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisibleBotones(1,1);
                    deshabilitarEscritura(0);
                    binding.btnCrearKilometraje.setText("Guardar Cambios");
                    binding.btnCrearKilometraje.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String kilometrajeValue = binding.etKilometraje.getText().toString();
                            Boolean estadoValue = binding.switchEstadoKilometraje.isChecked();
                            mViewModel.editarKilometraje(kilometrajeValue, estadoValue);
                        }
                    });
                }
            }
        });
        //Configurar Visibilidad de datos
        mViewModel.getRegistroKilometrajeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<RegistroKilometraje>() {
            @Override
            public void onChanged(RegistroKilometraje registroKilometraje) {
                binding.etKilometraje.setText(registroKilometraje.getKilometraje()+ "");
                binding.switchEstadoKilometraje.setChecked(registroKilometraje.isEstado());
                binding.switchEstadoKilometraje.setText(registroKilometraje.isEstado() ? "Estado " +
                        "del registro: Activo" : "Estado del registro: Inactivo");
            }
        });
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(getContext(),errorMessage, Toast.LENGTH_LONG).show();
            }
        });
        binding.switchEstadoKilometraje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                compoundButton.setText( b ? "Estado " +
                        "del registro: Activo" : "Estado del registro: Inactivo");
            }
        });
        return binding.getRoot();
    }
    private void setVisibleBotones ( int eleccion, int allowSwitch){
        binding.btnCrearKilometraje.setVisibility(eleccion == 1 ? View.VISIBLE : View.GONE);
        binding.switchEstadoKilometraje.setEnabled(eleccion == 1);
        binding.switchEstadoKilometraje.setVisibility(allowSwitch == 1 ? View.VISIBLE : View.GONE);
    }
    private void deshabilitarEscritura ( int eleccion){
        binding.etKilometraje.setFocusable(eleccion == 1 ? false : true);
        binding.etKilometraje.setEnabled(eleccion == 1 ? false : true);
    }
}