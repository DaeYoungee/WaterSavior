package com.example.watersavior.component.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.viewmodel.RecordViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.watersavior.R


@Composable
fun WaterCheckButton(viewModel: RecordViewModel) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
//            .padding(16.dp)
        verticalArrangement = Arrangement.Center
    ) {
        Text( text = "", fontWeight = FontWeight.Bold, fontSize = 30.sp ) // 투명 글자, 공간 채우기용
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!viewModel.recoding.value) {
                    coroutineScope.launch(Dispatchers.IO) {
                        viewModel.checkRecoding()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues(),
            shape = RoundedCornerShape(20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(shape = RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                colorResource(id = R.color.main_blue),
                                colorResource(id = R.color.opaque_blue)
                            )
                        )
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.VolumeUp,
                    contentDescription = "Water Sound Check",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .size(30.dp)
                )
                Text(
                    text = "Water Sound Check",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
        }

    }
}
