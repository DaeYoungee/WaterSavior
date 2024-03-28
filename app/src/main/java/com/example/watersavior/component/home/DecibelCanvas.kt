package com.example.watersavior.component.home

import android.graphics.Paint
import android.util.Log
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.watersavior.viewmodel.RecordViewModel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun DecibelCanvas(viewModel: RecordViewModel) { // 최대 140데시벨
    val animatedValue = remember { Animatable(0f) }

    // 특정 값으로 색을 채우는 Animation
    LaunchedEffect(viewModel.db.value) {
        animatedValue.animateTo(
            targetValue = (viewModel.db.value.toFloat() * (2.25).toFloat()),
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.2f),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight()
                .padding(bottom = 80.dp),
            text = "Decibel",
            fontSize = 25.sp,
            fontWeight = FontWeight.Medium,
            color = Color.Black
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)

        ) {
            Text(modifier = Modifier.align(Alignment.Center), text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp)) {
                    append(viewModel.db.value)
                }
                append("db")
            })
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val sizeArc = size / 1.25F
                val x = (size.width - sizeArc.width) / 2f
                val y = (size.height - sizeArc.height) / 2f
//                Log.d("daeYoung", "size: $size, sizeArc: $sizeArc")
//                Log.d("daeYoung", "x: $x, y: $y")
                drawArc(
                    color = Color(0xFFE1E2E9),
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    topLeft = Offset(x, y),
                    size = sizeArc,
                    style = Stroke(width = 70f, cap = StrokeCap.Round)
                )

                drawArc(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xff007AFF), Color(0xff00438c)
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite,
                    ),
                    startAngle = 135f,
                    sweepAngle = animatedValue.value,
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - sizeArc.width) / 2f,
                        (size.height - sizeArc.height) / 2f
                    ),
                    size = sizeArc,
                    style = Stroke(width = 70f, cap = StrokeCap.Round)
                )

//            repeat(8) { index ->
//                val angleInDegree = (index * 360 / 8 + 135).toDouble()
//                val angleInRadian = Math.toRadians(angleInDegree)
//
//                // 1~12시 텍스트 그리기
//                drawContext.canvas.nativeCanvas.apply {
//                    if (index % 5 == 0) {
//                        val decibelText = index * 20
//                        val textSize = 10.dp.toPx()
//                        val textRadius = sizeArc.minDimension
//                        val x = textRadius * cos(angleInRadian).toFloat() + center.x
//                        val y = textRadius * sin(angleInRadian).toFloat() + center.y + textSize / 2f
//                        drawText(
//                            "$decibelText",
//                            x.toFloat(),
//                            y.toFloat(),
//                            Paint().apply {
//                                this.color = android.graphics.Color.parseColor("#000000")
//                                this.textSize = textSize
//                                this.textAlign = Paint.Align.CENTER
//                            }
//                        )
//                    }
//                }
//            }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Test() {
    Row(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(0.5f)) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)

        ) {
            Text(modifier = Modifier.align(Alignment.Center), text = buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp)) {
                    append("00")
                }
                append("db")
            })
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val sizeArc = size / 1.25F
                val x = (size.width - sizeArc.width) / 2f
                val y = (size.height - sizeArc.height) / 2f
                Log.d("daeYoung", "size: $size, sizeArc: $sizeArc, size_width: ${size.width}")
                Log.d("daeYoung", "x: $x, y: $y")
                drawArc(
                    color = Color(0xFFE1E2E9),
                    startAngle = 135f,
                    sweepAngle = 270f,
                    useCenter = false,
                    topLeft = Offset(x, y),
                    size = sizeArc,
                    style = Stroke(width = 70f, cap = StrokeCap.Round)
                )

                drawArc(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xff007AFF), Color(0xff00438c)
                        ),
                        start = Offset.Zero,
                        end = Offset.Infinite,
                    ),
                    startAngle = 135f,
                    sweepAngle = 200f,
                    useCenter = false,
                    topLeft = Offset(
                        (size.width - sizeArc.width) / 2f,
                        (size.height - sizeArc.height) / 2f
                    ),
                    size = sizeArc,
                    style = Stroke(width = 70f, cap = StrokeCap.Round)
                )

                repeat(8) { index ->
                    val angleInDegree = (index * 360 / 8 + 135).toDouble()
                    val angleInRadian = Math.toRadians(angleInDegree)

                    // 1~12시 텍스트 그리기
                    drawContext.canvas.nativeCanvas.apply {
                        if (index % 5 == 0) {
                            val decibelText = index * 20
                            val textSize = 10.dp.toPx()
                            val textRadius = sizeArc.minDimension
                            val x = textRadius * cos(angleInRadian).toFloat() + center.x
                            val y =
                                textRadius * sin(angleInRadian).toFloat() + center.y + textSize / 2f
                            drawText(
                                "$decibelText",
                                x.toFloat(),
                                y.toFloat(),
                                Paint().apply {
                                    this.color = android.graphics.Color.parseColor("#000000")
                                    this.textSize = textSize
                                    this.textAlign = Paint.Align.CENTER
                                }
                            )
                        }
                    }
                }
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center)
                    .zIndex(2f),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, fontSize = 40.sp)) {
                        append("0")
                    }
                    append("%")
                })
            Canvas(
                modifier = Modifier.fillMaxSize()
            ) {
                val sizeArc = (size.width / 2) / 1.1F
                Log.d("daeYoung", "size2: $size")
                drawCircle(
                    color = Color(0xFFE1E2E9),
                    center = center,
                    radius = (size.width / 2) / 1.1F
                )
                drawArc(
                    color = Color(0xff007AFF),
                    startAngle = 260f,   // 90f~270f
                    sweepAngle = -340f,  // 0f ~ -360f(starAngle의 2배만큼 움직임)
                    useCenter = false,
                    topLeft = Offset(size.width / 2.0F - sizeArc, size.height / 2.0F - sizeArc),
                    size = size / 1.1f
                )
            }
        }
    }
}