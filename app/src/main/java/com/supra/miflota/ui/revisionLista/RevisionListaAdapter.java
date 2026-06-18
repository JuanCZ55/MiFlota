package com.supra.miflota.ui.revisionLista;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.supra.miflota.R;
import com.supra.miflota.data.models.ChecklistDiario;
import com.supra.miflota.databinding.ItemRevisionBinding;

import java.util.List;

public class RevisionListaAdapter extends RecyclerView.Adapter<RevisionListaAdapter.ViewHolderRevision> {

    private List<ChecklistDiario> revisionList;
    private LayoutInflater inflater;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ChecklistDiario revision);
    }

    public RevisionListaAdapter(List<ChecklistDiario> revisionList, LayoutInflater inflater, OnItemClickListener listener) {
        this.revisionList = revisionList;
        this.inflater = inflater;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolderRevision onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.item_revision, parent, false);
        return new ViewHolderRevision(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolderRevision holder, int position) {
        ChecklistDiario revision = revisionList.get(position);

        holder.code.setText("Cod: " + revision.getIdChecklistDiario());

        // Formatear fecha (opcional: quitar la 'T' del ISO string que viene en el JSON)
        String fechaLimpia = revision.getFecha().replace("T", " ").substring(0, 16);
        holder.fecha.setText(fechaLimpia);

        // Colores
        int colorError = ContextCompat.getColor(holder.itemView.getContext(), R.color.error);
        int colorWarning = ContextCompat.getColor(holder.itemView.getContext(), R.color.warning);


        // Iluminacion
        if(!revision.isFaroDelanteroIzquierdo() && !revision.isFaroDelanteroDerecho()
            && !revision.isFaroTraseroIzquierdo() && !revision.isFaroTraseroDerecho()) {
            holder.ilumniacion.setTextColor(colorError);
            holder.ilumniacion.setCompoundDrawableTintList(ColorStateList.valueOf(colorError));
        } else if (!revision.isFaroDelanteroIzquierdo() || !revision.isFaroDelanteroDerecho()
                || !revision.isFaroTraseroIzquierdo() || !revision.isFaroTraseroDerecho()){
            holder.ilumniacion.setTextColor(colorWarning);
            holder.ilumniacion.setCompoundDrawableTintList(ColorStateList.valueOf(colorWarning));
        }

        // Liquidos
        if(!revision.isLiquidoFrenos() &&
                !revision.isNivelAceite() &&
                !revision.isNivelRefrigerante() &&
                !revision.isNivelAguaParabrisas()){
            holder.liquidos.setTextColor(colorError);
            holder.liquidos.setCompoundDrawableTintList(ColorStateList.valueOf(colorError));

        }else if (!revision.isLiquidoFrenos() ||
                !revision.isNivelAceite() ||
                !revision.isNivelRefrigerante() ||
                !revision.isNivelAguaParabrisas()){
            holder.liquidos.setTextColor(colorWarning);
            holder.liquidos.setCompoundDrawableTintList(ColorStateList.valueOf(colorWarning));

        }


        // Seguridad
        if (!revision.isPresionNeumaticos() &&
                !revision.isNivelFrenos() &&
                !revision.isMatafuegoVigente()){
            holder.seguridad.setTextColor(colorError);
            holder.seguridad.setCompoundDrawableTintList(ColorStateList.valueOf(colorError));

        } else if (!revision.isPresionNeumaticos() ||
                !revision.isNivelFrenos() ||
                !revision.isMatafuegoVigente()) {
            holder.seguridad.setTextColor(colorWarning);
            holder.seguridad.setCompoundDrawableTintList(ColorStateList.valueOf(colorWarning));

        }


        if (!revision.isEstado()) {
            holder.estado.setText("INACTIVO");
            holder.estado.setTextColor(colorError);
        }

        holder.contenedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(revision);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return revisionList != null ? revisionList.size() : 0;
    }

    public void updateList(List<ChecklistDiario> nuevaLista) {
        this.revisionList = nuevaLista;
        notifyDataSetChanged();
    }

    public static class ViewHolderRevision extends RecyclerView.ViewHolder{
        private ItemRevisionBinding binding;
        private TextView code, fecha, ilumniacion, liquidos, seguridad, estado;
        private ConstraintLayout contenedor;

        public ViewHolderRevision(@NonNull View itemView){
            super(itemView);
            binding = ItemRevisionBinding.bind(itemView);
            code = binding.tvIdRevision;
            fecha = binding.tvFecha;
            ilumniacion = binding.tvIluminacion;
            liquidos = binding.tvLiquidos;
            seguridad = binding.tvSeguridad;
            estado = binding.tvEstado;
            contenedor = binding.contenedor;
        }
    }
}
