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
        int colorOk = ContextCompat.getColor(holder.itemView.getContext(), R.color.success);

        // Iluminacion
        actualizarEstadoComponente(
                holder.ilumniacion,
                colorError,
                colorWarning,
                colorOk,
                (revision.isFaroDelanteroIzquierdo() ? 1 : 0 ) +
                        (revision.isFaroDelanteroDerecho() ? 1 : 0 ) +
                        (revision.isFaroTraseroIzquierdo() ? 1 : 0 ) +
                        (revision.isFaroTraseroDerecho()? 1 : 0 ),
                4
        );


        // Liquidos
        actualizarEstadoComponente(
                holder.liquidos,
                colorError,
                colorWarning,
                colorOk,
                (revision.isLiquidoFrenos() ? 1 : 0 ) +
                        (revision.isNivelAceite() ? 1 : 0 ) +
                        (revision.isNivelRefrigerante() ? 1 : 0 ) +
                        (revision.isNivelAguaParabrisas()? 1 : 0 ),
                4
        );


        // Seguridad
        actualizarEstadoComponente(
                holder.seguridad,
                colorError,
                colorWarning,
                colorOk,
                (revision.isPresionNeumaticos() ? 1 : 0 ) +
                        (revision.isNivelFrenos() ? 1 : 0 ) +
                        (revision.isMatafuegoVigente() ? 1 : 0 ),
                3
        );

        if (!revision.isEstado()) {
            holder.estado.setText("INACTIVO");
            holder.estado.setTextColor(colorError);
        } else {
            holder.estado.setText("ACTIVO");
            holder.estado.setTextColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.success));
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

    /**
     * Actualiza el estado de un componente de la vista, cambiando tanto el color del texto como el color del tinte de los iconos
     * @param view         El TextView al que se le aplicara el color (debe contener el texto e iconos).
     * @param colorError   Valor del color para estado critico (0 ítems correctos).
     * @param colorWarning Valor del color para estado incompleto (algunos ítems correctos).
     * @param colorOk  Valor del color para estado optimo (todos los ítems correctos).
     * @param aux          Cantidad de items que pasaron la validacion en la revision.
     * @param total        Cantidad total de ítems evaluados en esa categoroa.
     */
    private void actualizarEstadoComponente(TextView view, int colorError, int colorWarning, int colorOk, int aux , int total){
        int colorFinal;
        if(aux == 0){
            colorFinal = colorError;
        } else if (aux<total){
            colorFinal = colorWarning;
        } else {
            colorFinal = colorOk;
        }

        view.setTextColor(colorFinal);
        view.setCompoundDrawableTintList(ColorStateList.valueOf(colorFinal));

    }

    @Override
    public int getItemCount() {
        return revisionList != null ? revisionList.size() : 0;
    }

    public void updateList(List<ChecklistDiario> nuevaLista) {
        this.revisionList.clear();
        this.revisionList.addAll(nuevaLista);
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
