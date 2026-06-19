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

    // Variables para manejar la lógica de datos y el enlazado de vistas
    private KilometrajeViewModel mViewModel;
    private FragmentKilometrajeBinding binding;

    public static kilometrajeFragment newInstance() {
        return new kilometrajeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inicialización de ViewModel y ViewBinding
        mViewModel = new ViewModelProvider(this).get(KilometrajeViewModel.class);
        binding = FragmentKilometrajeBinding.inflate(inflater, container, false);

        // Extracción de argumentos de navegación y envío al ViewModel para determinar el modo operativo
        Bundle bundle = getArguments();
        mViewModel.descomprimirBundle(bundle);

        // Observador del Modo "Solo Vista" (Lectura)
        mViewModel.getIsOnlyView().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisibleBotones(0,1);   // Quita la visibilidad a los botones principales
                    deshabilitarEscritura(1); // Deshabilita la edición de campos
                }else{
                    setVisibleBotones(1,1);   // Muestra los controles
                    deshabilitarEscritura(0); // Habilita la edición de campos
                }
            }
        });

        // Observador del Modo "Crear" (Nuevo Registro)
        mViewModel.getIsCrear().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisibleBotones(1,0); // Muestra botón de acción, oculta switch de estado
                    deshabilitarEscritura(0); // Habilita la edición
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

        // Observador del Modo "Editar" (Modificar Registro Existente)
        mViewModel.getIsEdicion().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    setVisibleBotones(1,1); // Muestra todos los controles
                    deshabilitarEscritura(0); // Habilita la edición
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

        // Observador para poblar la interfaz con los datos del registro cargado
        mViewModel.getRegistroKilometrajeMutableLiveData().observe(getViewLifecycleOwner(), new Observer<RegistroKilometraje>() {
            @Override
            public void onChanged(RegistroKilometraje registroKilometraje) {
                binding.etKilometraje.setText(registroKilometraje.getKilometraje()+ "");
                binding.switchEstadoKilometraje.setChecked(registroKilometraje.isEstado());
                binding.switchEstadoKilometraje.setText(registroKilometraje.isEstado() ? "Estado " +
                        "del registro: Activo" : "Estado del registro: Inactivo");
            }
        });

        // Observador para capturar mensajes de error del ViewModel y mostrarlos en pantalla
        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(getContext(),errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        // Listener para actualizar dinámicamente el texto del Switch según su estado
        binding.switchEstadoKilometraje.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton compoundButton, boolean b) {
                compoundButton.setText( b ? "Estado " +
                        "del registro: Activo" : "Estado del registro: Inactivo");
            }
        });

        return binding.getRoot();
    }

    // Método auxiliar para gestionar la visibilidad y estado del botón principal y el switch
    private void setVisibleBotones ( int eleccion, int allowSwitch){
        binding.btnCrearKilometraje.setVisibility(eleccion == 1 ? View.VISIBLE : View.GONE);
        binding.switchEstadoKilometraje.setEnabled(eleccion == 1);
        binding.switchEstadoKilometraje.setVisibility(allowSwitch == 1 ? View.VISIBLE : View.GONE);
    }

    // Método auxiliar para bloquear o permitir la entrada de texto en el EditText
    private void deshabilitarEscritura ( int eleccion){
        binding.etKilometraje.setFocusable(eleccion == 1 ? false : true);
        binding.etKilometraje.setEnabled(eleccion == 1 ? false : true);
    }
}