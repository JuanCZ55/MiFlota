package com.supra.miflota.ui.vehiculoLista;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.supra.miflota.R;
import com.supra.miflota.data.models.Vehiculo;
import com.supra.miflota.databinding.ItemListadoVehiculosBinding;

import java.util.List;

public class VehiculoListarAdapter extends RecyclerView.Adapter<VehiculoListarAdapter. ViewHolderVehiculo>{

    private List<Vehiculo> listaVehiculos;
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public interface OnItemClickListener{
        void onItemClick(Vehiculo vehiculo);
    }
    public VehiculoListarAdapter(List<Vehiculo> listadoVehiculos, LayoutInflater inflater, OnItemClickListener listener){
        this.listaVehiculos = listadoVehiculos;
        this.layoutInflater = inflater;
        this.listener = listener;
    }
    @NonNull
    @Override
    public ViewHolderVehiculo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_listado_vehiculos, parent, false);
        return new ViewHolderVehiculo(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderVehiculo holder, int position) {
        Vehiculo vehiculo = listaVehiculos.get(position);
        holder.patente.setText(vehiculo.getPatente());
        holder.datosVehiculo.setText(vehiculo.getMarca() + " " + vehiculo.getModelo() + " - " + vehiculo.getAnio());
        holder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null){
                    listener.onItemClick(vehiculo);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderVehiculo extends RecyclerView.ViewHolder{
        private ItemListadoVehiculosBinding binding;
        private TextView patente;
        private TextView datosVehiculo;
        private ConstraintLayout contenedor;
        public ViewHolderVehiculo(@NonNull View itemView){
            super(itemView);
            binding = ItemListadoVehiculosBinding.bind(itemView);
            patente = binding.tvItemPatente;
            datosVehiculo = binding.tvItemDatosVehiculo;
            contenedor = binding.contenedorListadoVehiculos;
        }
    }
}
