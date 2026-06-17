package com.supra.miflota.ui.vehiculo;

import android.content.Context; // Importante
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.supra.miflota.R;

public class MatafuegoDialog {
    /**
     * Muestra un diálogo con información del Matafuego.
     * @param context Contexto de la aplicación.
     * @param serie Serie del Matafuego.
     * @param proveedor Proveedor del Matafuego.
     * @param carga Fecha de carga del Matafuego.
     * @param vencimiento Fecha de vencimiento del Matafuego.
    */
    public static void dialog(Context context, String serie, String proveedor, String carga, String vencimiento) {
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_matafuego, null);

        TextView tvSerie = dialogView.findViewById(R.id.tvDialogSerie);
        TextView tvProveedor = dialogView.findViewById(R.id.tvDialogProveedor);
        TextView tvCarga = dialogView.findViewById(R.id.tvDialogCarga);
        TextView tvVencimiento = dialogView.findViewById(R.id.tvDialogVencimiento);
        Button btnCerrar = dialogView.findViewById(R.id.btnCerrarDialog);

        tvSerie.setText(serie != null ? serie : "-");
        tvProveedor.setText(proveedor != null ? proveedor : "-");
        tvCarga.setText(carga != null ? carga : "-");
        tvVencimiento.setText(vencimiento != null ? vencimiento : "-");

        AlertDialog dialog = new AlertDialog.Builder(context)
                .setView(dialogView)
                .setCancelable(true)
                .create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        btnCerrar.setOnClickListener(v -> dialog.dismiss());
        dialog.show();
    }
}
