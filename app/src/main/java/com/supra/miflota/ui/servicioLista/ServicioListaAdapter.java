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
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Service service);
    }

    public ServicioListaAdapter(List<Service> serviceList, LayoutInflater inflater, OnItemClickListener listener) {
        this.serviceList = serviceList;
        this.inflater = inflater;
        this.listener = listener;
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
        holder.km.setText(String.valueOf(service.getKmService()+" Km"));
        holder.fecha.setText(service.getFecha());
        if (service.isExcepcional()) {
            holder.imagen.setVisibility(View.VISIBLE);
        } else {
            holder.imagen.setVisibility(View.INVISIBLE);
        }
        holder.contenedor.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(service);
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList.clear();
        this.serviceList.addAll(serviceList);

        notifyDataSetChanged();
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
            contenedor = binding.contenedor;

        }
    }
}
