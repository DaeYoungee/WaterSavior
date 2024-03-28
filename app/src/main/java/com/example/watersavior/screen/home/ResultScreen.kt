package com.example.watersavior.screen.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.watersavior.component.common.BlueButton
import com.example.watersavior.component.home.MiddleSizeText
import com.example.watersavior.component.home.ResultCard
import com.example.watersavior.viewmodel.RecordViewModel
import kotlinx.coroutines.launch


@Composable
fun ResultScreen(viewModel: RecordViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        MiddleSizeText("Result")
        ResultCard(
            modifier = Modifier.height(300.dp),
            timeText = (viewModel.milliseconds / 1000).toString(),
            waterText = viewModel.formatWaterQuantity(),
            billText = viewModel.formatWaterBill()
        )
        BlueButton(text = "go home", onClick = {
            coroutineScope.launch {
                viewModel.storeResultWater()
                viewModel.changeIsResult()
            }
        })
    }
}