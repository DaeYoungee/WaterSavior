import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos

@Composable
@Preview(showBackground = true)
fun Text() {
    val textMeasurer = rememberTextMeasurer()
    val text = "0"
    val style = TextStyle(
        fontSize = 15.sp,
        color = Color.Black,
        background = Color.Red.copy(alpha = 0.2f)
    )
    val textLayoutResult = remember(text) {
        textMeasurer.measure(text, style)
    }


    Canvas(modifier = Modifier.size(300.dp)) {
        val radius = size.width * 0.6f / 2
        drawArc(
            color = Color.LightGray,
            startAngle = 120f,
            sweepAngle = 300f,
            useCenter = false,
            topLeft = Offset(size.width * 0.2f, size.height * 0.2f),
            size = Size(size.width * 0.6f, size.height * 0.6f),
            style = Stroke(width = 70f, cap = StrokeCap.Round)
        )
        drawArc(
            color = Color.Blue,
            startAngle = 120f,
            sweepAngle = 120f,
            useCenter = false,
            alpha = 0.3f,
            topLeft = Offset(size.width * 0.2f, size.height * 0.2f),
            size = Size(size.width * 0.6f, size.height * 0.6f),
            style = Stroke(width = 70f, cap = StrokeCap.Round)
        )
        repeat(5) {index ->
            val angleInDegree = (index * 360 / 5).toDouble()
            val angleInRadian = Math.toRadians(angleInDegree)
            drawText(
                textLayoutResult = textLayoutResult,
//            topLeft = Offset(
//                x = center.x - textLayoutResult.size.width / 2,
//                y = center.y - textLayoutResult.size.height / 2,
//            )
                topLeft = Offset(center.x + radius * cos(angleInRadian).toFloat(), center.y  - radius * cos(angleInRadian).toFloat())
            )
        }

    }
}
//Offset(150.dp.toPx(),150.dp.toPx())