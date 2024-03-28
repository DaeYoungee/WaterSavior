package com.example.watersavior.component.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.R
import com.example.watersavior.viewmodel.RecordViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlin.math.ceil

@Composable
fun ResultCard(
    modifier: Modifier = Modifier,
    timeText: String = "test",
    waterText: String = "test",
    billText: String = "test"
) {
    val processedWaterText = ceil(waterText.toDouble()).toString()
    val processedBillText = ceil(billText.toDouble()).toString()

    Card(
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.textfield_container)),
        border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.border_grey)),
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // API 통신 필요
            ResultRow(image = R.drawable.water_time, title = "usedTime", content = timeText, unit = "sec")
            Spacer(modifier = Modifier.height(40.dp))
            ResultRow(image = R.drawable.water, title = "usedWater", content = processedWaterText, unit = "m^3")
            Spacer(modifier = Modifier.height(40.dp))
            ResultRow(image = R.drawable.water_bill, title = "usedBill", content = processedBillText, unit = "won")
        }
    }
}

@Composable
fun ResultRow(image: Int, title: String, content: String, unit: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = "",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(text = title, fontSize = 18.sp)
        }
        Text(text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.main_blue),
                )
            ) {
                append(content)
            }
            append("  $unit")
        })
    }
}

@Preview(showBackground = true)
@Composable
fun Tes3t() {
    ResultCard()
}