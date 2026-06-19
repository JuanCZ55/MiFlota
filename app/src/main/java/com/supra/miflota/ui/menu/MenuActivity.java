package com.supra.miflota.ui.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.supra.miflota.R;
import com.supra.miflota.databinding.ActivityMenuBinding;

public class MenuActivity extends AppCompatActivity {
    private ActivityMenuBinding binding;
    private AppBarConfiguration mAppBarConfiguration;
    private MenuViewModel viewModel;
    private SidebarViewModel sidebarViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MenuViewModel.class);
        sidebarViewModel = new ViewModelProvider(this).get(SidebarViewModel.class);

        binding = ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        View headerView = binding.navView.getHeaderView(0);
        TextView name = headerView.findViewById(R.id.nameLabelHeader);
        TextView mail = headerView.findViewById(R.id.tvMailHeader);
        viewModel.getUsuario().observe(this, usuario -> {
            name.setText(usuario.getPersona().getNombre().concat(" ").concat(usuario.getPersona().getApellido()));
            mail.setText(usuario.getGmail());
        });


        sidebarViewModel.getUserEmail().observe(this, email -> {
            mail.setText(email);
        });

        viewModel.cargarUsuario();

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
            .findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();


        // Configuracion del menu lateral
        mAppBarConfiguration = new AppBarConfiguration.Builder(
            R.id.nav_vehiculo,
            R.id.nav_kilometraje_lista,
            R.id.nav_perfil,
            R.id.nav_revision_lista,
            R.id.nav_servicio_lista
        ).setOpenableLayout(binding.drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        // Configuracion del comportamiento del menu lateral
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();

            // Pantallas con menu hamburguesa
            if (id == R.id.nav_vehiculo || id == R.id.nav_kilometraje_lista || id == R.id.nav_revision_lista || id == R.id.nav_servicio_lista) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
            // Pantalla sin menu hamburguesa y flecha atras
            else if (id == R.id.nav_vehiculo_lista || id == R.id.nav_logout) {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().hide();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                }
            }
            // Pantallas con flecha atras
            else {
                binding.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                if (getSupportActionBar() != null) {
                    getSupportActionBar().show();
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();

        // Maneja la accion del menu hamburguesa y/o flecha atras
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}