package com.supra.miflota.ui.kilometrajeLista;

import static com.github.AAChartModel.AAChartCore.AATools.AAColor.AARgba;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.AAChartModel.AAChartCore.AAChartCreator.AAChartModel;
import com.github.AAChartModel.AAChartCore.AAChartCreator.AAChartView;
import com.github.AAChartModel.AAChartCore.AAChartCreator.AASeriesElement;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartSymbolStyleType;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartSymbolType;
import com.github.AAChartModel.AAChartCore.AAChartEnum.AAChartType;
import com.github.AAChartModel.AAChartCore.AATools.AAGradientColor;
import com.github.AAChartModel.AAChartCore.AATools.AALinearGradientDirection;
import com.supra.miflota.R;
import com.supra.miflota.databinding.FragmentKilometrajeListaBinding;

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
        initGrafico(new String[]{}, new Object[]{});

        return binding.getRoot();
    }

    public void initGrafico(String[] fechas, Object[] valores) {
        Map<String, Object> linearGradientColor = AAGradientColor.linearGradient(
                AALinearGradientDirection.ToBottom,
                "rgba(19,0,202,1)",//
                "rgba(7,0,70,1)"//
        );
        AAChartView aaChartView = binding.grafico;
        AAChartModel aaChartModel = new AAChartModel()
                .chartType(AAChartType.Areaspline)
                .backgroundColor("#101114")
                .markerSymbolStyle(AAChartSymbolStyleType.InnerBlank)
                .markerSymbol(AAChartSymbolType.Circle)
                .categories(fechas)
                .yAxisLineWidth(0)

                .yAxisGridLineWidth(0)
                .legendEnabled(false)
                .series(new AASeriesElement[]{
                        new AASeriesElement()
                                .name("Registro Km")
                                .lineWidth(5.0)
                                .color(AARgba(30,12,201,1.0f))
                                .fillColor(linearGradientColor)
                                .data(valores)

                });
        aaChartView.aa_drawChartWithChartModel(aaChartModel);
    }

}