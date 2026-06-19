package com.supra.miflota.ui.kilometrajeLista;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.github.AAChartModel.AAChartCore.AATools.AAColor.AARgba;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.AAChartModel.AAChartCore.AAChartCreator.AAChartModel;
import com.github.AAChartModel.AAChartCore.AAChartCreator.AAChartView;
import com.github.AAChartModel.AAChartCore.AAChartCreator.AASeriesElement;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartAnimationType;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartSymbolStyleType;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartSymbolType;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartType;
import com.github.AAChartModel.AAChartCore.AAOptionsModel.AAHover;
import com.github.AAChartModel.AAChartCore.AAOptionsModel.AAMarker;
import com.github.AAChartModel.AAChartCore.AAOptionsModel.AAMarkerHover;
import com.github.AAChartModel.AAChartCore.AAOptionsModel.AAMarkerStates;
import com.github.AAChartModel.AAChartCore.AAOptionsModel.AAOptions;
import com.github.AAChartModel.AAChartCore.AAOptionsModel.AAStates;
import com.github.AAChartModel.AAChartCore.AATools.AAColor;
import com.github.AAChartModel.AAChartCore.AATools.AAGradientColor;
import com.github.AAChartModel.AAChartCore.AATools.AALinearGradientDirection;
import com.supra.miflota.R;
import com.supra.miflota.data.models.RegistroKilometraje;
import com.supra.miflota.databinding.FragmentKilometrajeListaBinding;
import com.supra.miflota.ui.kilometraje.kilometrajeFragment;

import java.util.List;
import java.util.Map;

public class KilometrajeListaFragment extends Fragment {

    private KilometrajeListaViewModel mViewModel;
    private FragmentKilometrajeListaBinding binding;
    public static KilometrajeListaFragment newInstance() {
        return new KilometrajeListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(KilometrajeListaViewModel.class);
        binding = FragmentKilometrajeListaBinding.inflate(inflater, container, false);
        binding.rvRegistrosDekilometraje.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getErrorMessage().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnTodosYPropios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setTodosOPropios();
            }
        });
        mViewModel.getTodosOPropios().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    binding.btnTodosYPropios.setText("Propios");
                }else{
                    binding.btnTodosYPropios.setText("Todos");
                }
                mViewModel.cargarLista();
            }
        });
        mViewModel.getActivoYInactivo().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    binding.btnActivos.setText("Activos");
                else
                    binding.btnActivos.setText("Inactivos");
                mViewModel.cargarLista();
            }
        });
        binding.btnActivos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setActivoYInactivo();
            }
        });
        mViewModel.getListaRegistroKilometraje().observe(getViewLifecycleOwner(), new Observer<List<RegistroKilometraje>>() {
            @Override
            public void onChanged(List<RegistroKilometraje> registroKilometrajes) {
                KilometroListarAdapter adapter = new KilometroListarAdapter(registroKilometrajes,
                        getLayoutInflater(), new KilometroListarAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(RegistroKilometraje registroKilometraje) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("registroKilometraje", registroKilometraje);
                        bundle.putBoolean("esEdicion", registroKilometraje.isCurrentUser());
                        Navigation.findNavController(getView()).navigate(R.id.nav_kilometraje
                                ,bundle);
                    }
                });
                binding.rvRegistrosDekilometraje.setAdapter(adapter);
                KilometrajeListaViewModel.DataGraficos dataGraficos = mViewModel.generarDatosParaGraficos();
                if(dataGraficos != null){
                    initGrafico(dataGraficos.getFechas(), dataGraficos.getValores());
                }
            }
        });
        binding.btnflotanteKilometraje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.nav_kilometraje);
            }
        });
        mViewModel.cargarLista();
        return binding.getRoot();
    }

    public void initGrafico(String[] fechas, Object[] valores) {
        Map<String, Object> linearGradientColor = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                "rgba(0, 134, 255, 1f)",
                "rgba(0, 194, 255, 1f)"
        );
        AAChartView aaChartView = binding.grafico;

        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Spline)
                .categories(fechas)
                .markerRadius(4.0)
                .yAxisLineWidth(0)
                .backgroundColor("#101114")
                .yAxisGridLineWidth(0)
                .legendEnabled(false)
                .markerSymbolStyle(AAChartSymbolStyleType.Normal)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Registros")
                                .lineWidth(3.0)
                                .color(AAColor.Red)
                                .states(new AAStates()
                                        .hover(new AAHover()
                                                .enabled(true)
                                                .lineWidthPlus(0)))
                                .marker(new AAMarker()
                                        .states(new AAMarkerStates()
                                                .hover(new AAMarkerHover()
                                                        .fillColor(AAColor.Red)
                                                        .radius(10))))
                                .data(valores),
                });
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }

}