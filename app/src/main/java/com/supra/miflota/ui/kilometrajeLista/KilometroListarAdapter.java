package com.supra.miflota.ui.kilometrajeLista;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.supra.miflota.R;
import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.databinding.ItemRegistroKilometrajeBinding;

import java.util.ArrayList;
import java.util.List;

public class KilometroListarAdapter extends RecyclerView.Adapter<KilometroListarAdapter.ViewHolderKilometro> {

    private List<RegistroKilometraje> registroKilometrajeList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private OnItemClickListener listener;

    public KilometroListarAdapter(List<RegistroKilometraje> registroKilometrajeList, LayoutInflater layoutInflater, OnItemClickListener listener) {
        this.registroKilometrajeList = registroKilometrajeList;
        this.layoutInflater = layoutInflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderKilometro onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.item_registro_kilometraje, parent, false);
        return new ViewHolderKilometro(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderKilometro holder, int position) {
        RegistroKilometraje registroKilometraje = registroKilometrajeList.get(position);

        holder.tvPatente.setText(registroKilometraje.getVehiculo().getPatente());
        holder.fecha.setText(registroKilometraje.getFechaRegistro());
        holder.kilometraje.setText(String.valueOf(registroKilometraje.getKilometraje()));
        if(position > 0){
            RegistroKilometraje registroKilometrajeAnterior = registroKilometrajeList.get(position - 1);
            int diferencia = registroKilometraje.getKilometraje() - registroKilometrajeAnterior.getKilometraje();
            holder.resumenKilometraje.setText("+"+ diferencia +" km desde el último registro");
        }else{
            holder.resumenKilometraje.setText("Registro inicial");
        }


    }

    @Override
    public int getItemCount() {
        return registroKilometrajeList.size();
    }

    public interface  OnItemClickListener{
        void onItemClick(RegistroKilometraje registroKilometraje);
    }


    public class ViewHolderKilometro extends RecyclerView.ViewHolder {
        private ItemRegistroKilometrajeBinding binding;
        TextView tvPatente;

        TextView fecha;
        TextView kilometraje;
        TextView resumenKilometraje;
        ConstraintLayout contenedor;

        public ViewHolderKilometro(@NonNull View itemView) {
            super(itemView);
            binding = ItemRegistroKilometrajeBinding.bind(itemView);
            tvPatente = binding.tvIdRevision;
            fecha = binding.tvFecha;
            kilometraje = binding.tvUnidadKm;
            resumenKilometraje = binding.tvResumenKilometraje;
            contenedor = binding.contenedor;
        }
    }
}
