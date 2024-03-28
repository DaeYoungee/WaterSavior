package com.example.watersavior.component.vico

import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.viewmodel.StatisticesViewModel
import com.patrykandpatrick.vico.compose.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.legendItem
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.component.shape.Shapes


private const val MAIN_COLOR = 0xFF007AFF

private val color1 = Color(MAIN_COLOR)
private val chartColors = listOf(color1)
private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)

@Composable
fun rememberLegend(viewModel: StatisticesViewModel) =
    verticalLegend(
        items =
        chartColors.mapIndexed { _, chartColor ->
            legendItem(
                icon = rememberShapeComponent(Shapes.pillShape, chartColor),
                label =
                rememberTextComponent(
                    color = currentChartStyle.axis.axisLabelColor,
                    textSize = legendItemLabelTextSize,
                    typeface = Typeface.MONOSPACE,
                ),
                labelText = "${viewModel.legendMonth()} 월"
            )
        },
        iconSize = legendItemIconSize,
        iconPadding = legendItemIconPaddingValue,
        spacing = legendItemSpacing,
        padding = legendPadding,
    )
