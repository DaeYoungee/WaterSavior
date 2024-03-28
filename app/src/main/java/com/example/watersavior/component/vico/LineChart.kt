package com.example.watersavior.component.vico

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watersavior.viewmodel.StatisticesViewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.edges.rememberFadingEdges
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layout.fullWidth
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout
import com.patrykandpatrick.vico.core.chart.values.AxisValueOverrider
import com.patrykandpatrick.vico.core.model.LineCartesianLayerModel

private const val AXIS_VALUE_OVERRIDER_Y_FRACTION = 1f

private val axisValueOverrider =
    AxisValueOverrider.adaptiveYValues<LineCartesianLayerModel>(
        yFraction = AXIS_VALUE_OVERRIDER_Y_FRACTION,
        round = false,  // 반올림
    )

@Composable
fun LineChart(viewModel: StatisticesViewModel = viewModel()) {
    val marker = rememberMarker()

    ProvideChartStyle(rememberChartStyle(chartColors = listOf(Color(0xFF007AFF)))) {
        CartesianChartHost(
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(axisValueOverrider = axisValueOverrider),
                startAxis = rememberStartAxis(guideline = null),
                bottomAxis = rememberBottomAxis(
                    guideline = null,
                    itemPlacer = AxisItemPlacer.Horizontal.default(),
                ),
                fadingEdges = rememberFadingEdges(),
                legend = rememberLegend(viewModel = viewModel)
            ),
            modelProducer = viewModel.getModelProducer(),
            marker = marker,
            isZoomEnabled = false,
            horizontalLayout = HorizontalLayout.fullWidth()
        )
    }
}