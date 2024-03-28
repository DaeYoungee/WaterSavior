package com.example.watersavior.component.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.viewmodel.RecordViewModel
import com.example.watersavior.R

@Composable
fun TimerCard(viewModel: RecordViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
//            .padding(16.dp)
        verticalArrangement = Arrangement.Center
    ) {
        MiddleSizeText(text = "Time")
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.border_grey)),
            elevation = CardDefaults.cardElevation(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                MiddleSizeText(text = viewModel.formattedTime.value)
            }
        }
    }
}

@Composable
fun MiddleSizeText(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    )
}