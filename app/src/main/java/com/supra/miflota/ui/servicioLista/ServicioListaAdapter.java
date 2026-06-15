package com.supra.miflota.ui.servicioLista;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.supra.miflota.R;
import com.supra.miflota.data.models.Service;
import com.supra.miflota.databinding.ItemServicioBinding;

import java.util.List;

public class ServicioListaAdapter extends RecyclerView.Adapter<ServicioListaAdapter.ViewHolerServicios> {
    private List<Service> serviceList;
    private LayoutInflater inflater;
    public ServicioListaAdapter(List<Service> serviceList, LayoutInflater inflater) {
        this.serviceList = serviceList;
        this.inflater = inflater;
    }
    @NonNull
    @Override
    public ViewHolerServicios onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_servicio, parent, false);
        return new ViewHolerServicios(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolerServicios holder, int position) {
        Service service = serviceList.get(position);
        holder.km.setText(String.valueOf(service.getKmService()));
        holder.fecha.setText(service.getFecha());
        holder.proveedor.setText(service.getProveedor());
        if (service.isExcepcional()) {
            holder.imagen.setVisibility(View.VISIBLE);
        } else {
            holder.imagen.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ViewHolerServicios extends RecyclerView.ViewHolder {
        private ItemServicioBinding binding;
        ImageView imagen;
        TextView km, fecha, proveedor;
        ConstraintLayout contenedor;

        public ViewHolerServicios(@NonNull View itemView) {
            super(itemView);
            binding = ItemServicioBinding.bind(itemView);
            imagen = binding.ivStar;
            km = binding.tvKilometraje;
            fecha = binding.tvFecha;
            proveedor = binding.tvProveedor;
            contenedor = binding.contenedor;

        }
    }
}
