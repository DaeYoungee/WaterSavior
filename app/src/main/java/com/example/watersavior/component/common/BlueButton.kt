package com.example.watersavior.component.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.R

@Composable
fun BlueButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        onClick = {
            onClick()
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.main_blue),
            contentColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(5.dp)
    ) {
        Text(text = text, fontSize = 15.sp)
    }
}