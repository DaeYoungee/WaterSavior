package com.example.watersavior.component.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.watersavior.viewmodel.RecordViewModel

@Composable
fun WaterCanvas(viewModel: RecordViewModel) {
    val animatedValue = remember { Animatable(0f) }

    // 특정 값으로 색을 채우는 Animation
    LaunchedEffect(viewModel.waterPercent.value) {
        animatedValue.animateTo(
            targetValue = viewModel.waterPercent.value.toFloat(),
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .aspectRatio(1.2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(modifier = Modifier.wrapContentHeight().padding(bottom = 80.dp), text = "Water", fontSize = 25.sp, fontWeight = FontWeight.Medium, color = Color.Black)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Text(modifier = Modifier
                .align(Alignment.Center)
                .zIndex(2f), text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp)) {
                    append(viewModel.waterPercent.value.toString())
                }
                append("%")
            })
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val sizeArc = (size.width / 2) / 1.1F    // 전체의 절반 * 0.9, 즉 반지름의 길이를 뜻함
                val temp = animatedValue.value/100f * 180f
                val startAngle = if(animatedValue.value <= 100f) {
                    90f + temp
                } else {
                    270f
                }
                val sweepAngle = (temp * 2f) * -1f
//                Log.d("daeYoung", "animatedValue: ${animatedValue.value}, startAngle: $startAngle, sweepAngle: $sweepAngle")
                drawCircle(
                    color = Color(0xFFE1E2E9),
                    center = center,
                    radius = sizeArc
                )
                drawArc(
                    color = Color(0xff007AFF),
                    startAngle = startAngle,   // 90f~270f(180 크기)
                    sweepAngle = sweepAngle,  // 0f ~ -360f(starAngle의 2배만큼 움직임)
                    useCenter = false,
                    topLeft = Offset(size.width / 2.0F - sizeArc, size.height / 2.0F - sizeArc),
                    size = size / 1.1f
                )
            }
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
                    .align(Alignment.BottomEnd)
            )
        }
    }
}
