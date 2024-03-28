package com.example.watersavior.screen.statistices

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watersavior.component.calendar.Calendar
import com.example.watersavior.component.calendar.clickable
import com.example.watersavior.component.common.BlueButton
import com.example.watersavior.component.home.MiddleSizeText
import com.example.watersavior.component.home.ResultCard
import com.example.watersavior.component.statistics.InputDayTextField
import com.example.watersavior.component.vico.LineChart
import com.example.watersavior.viewmodel.StatisticesViewModel
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatisticesScreen(
    statisticViewModel: StatisticesViewModel = viewModel()
) {
    val coroutineScope = rememberCoroutineScope()
    val scroll = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scroll)
            .padding(16.dp)
    ) {
        Text(
            text = "${statisticViewModel.formatMonth(statisticViewModel.currentMonth)} Month",
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.clickable { statisticViewModel.reset() })
        Spacer(modifier = Modifier.height(16.dp))

        Calendar(viewModel = statisticViewModel)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            InputDayTextField(
                value = statisticViewModel.day.value.startDay,
                onValueChange = statisticViewModel.setStartDay,
                selection = statisticViewModel.selection.startDate,
                textFormat = statisticViewModel.formatDate,
                modifier = Modifier.width(80.dp)
            )
            Box(modifier = Modifier.fillMaxHeight()) {
                Text(
                    text = "~",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth(0.1f)
                        .padding(bottom = 12.dp)
                        .align(Alignment.BottomCenter),
                )
            }
            InputDayTextField(
                value = statisticViewModel.day.value.endDay,
                onValueChange = statisticViewModel.setEndDay,
                selection = statisticViewModel.selection.endDate,
                textFormat = statisticViewModel.formatDate,
                title = "End Day",
                modifier = Modifier.width(80.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        BlueButton(text = "check", enabled = statisticViewModel.checkEnabled()) {
            coroutineScope.launch { statisticViewModel.getDatesWaterBill() }
        }
        Spacer(modifier = Modifier.height(32.dp))


        ResultCard(
            timeText = statisticViewModel.monthWater.value.totalTime.toString(),
            waterText = statisticViewModel.monthWater.value.totalAmount.toString(),
            billText = statisticViewModel.monthWater.value.totalTax.toString()
        )

        Spacer(modifier = Modifier.height(32.dp))
        if (statisticViewModel.dayWater.value.size > 1) {  //  데이터가 1개이면 오류 남
            Text(text = "WaterBill", fontWeight = FontWeight.Bold, fontSize = 22.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(5.dp),
                border = BorderStroke(width = 1.dp, color = Color(0xFFE1E2E9))
            ) {
                Box(Modifier.padding(8.dp)) {
                    LineChart(viewModel = statisticViewModel)
                }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Test() {
    StatisticesScreen()
}