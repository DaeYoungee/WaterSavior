package com.example.watersavior.screen.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.viewmodel.RecordViewModel
import com.example.watersavior.R
import com.example.watersavior.TAG
import com.example.watersavior.component.common.BlueButton
import com.example.watersavior.component.home.CustomSnackbarHost
import com.example.watersavior.component.home.DecibelCanvas
import com.example.watersavior.component.home.TimerCard
import com.example.watersavior.component.home.WaterCanvas
import com.example.watersavior.component.home.WaterCheckButton

@Composable
fun HomeScreen(viewModel: RecordViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp)) {
                if (!viewModel.isWater.value) {
                    WaterCheckButton(viewModel = viewModel)
                } else {
                    TimerCard(viewModel = viewModel)
                }

            }

            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.4f)
            ) {
                WaterCanvas(viewModel = viewModel)
                DecibelCanvas(viewModel = viewModel)
            }
            if (viewModel.isWater.value) {
                Box(
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 32.dp,
                        bottom = 16.dp
                    )
                ) {
                    BlueButton(text = "Stop", onClick = {
                        viewModel.stopRecording()
                        viewModel.cancelDbAndWaterAndTime()
                        viewModel.changeIsResult()
                        viewModel.changeWaterCheckScreen()
                        Log.d(
                            TAG,
                            "time: ${viewModel.milliseconds}\nwater: ${viewModel.waterQuantity}\nbill: ${viewModel.waterBill}"
                        )
                    })
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.alarm),
                    contentDescription = "",
                    modifier = Modifier
                        .size(32.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Decivel이 50db 이하로 4초 지속될 경우 Auto Stop 됩니다.", fontSize = 14.sp)
            }
        }
        CustomSnackbarHost(
            snackbarHostState = viewModel.snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}