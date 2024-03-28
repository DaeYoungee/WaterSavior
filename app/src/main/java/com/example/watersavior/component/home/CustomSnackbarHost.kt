package com.example.watersavior.component.home

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomSnackbarHost(snackbarHostState: SnackbarHostState, modifier: Modifier) {
    // Modifier 값을 Modifier.align(Alignment.BottomCenter) 이것으로 default 설정할 것
    SnackbarHost(
        hostState = snackbarHostState, modifier = modifier
    ) {
        Snackbar(
            containerColor = Color(0xFF55B938),
            contentColor = Color.White,
            modifier = Modifier.padding(12.dp),
            action = {
                TextButton(
                    colors = ButtonDefaults.textButtonColors(contentColor = Color.White),
                    onClick = { it.performAction() },
                    content = { Text(text = "Check") }
                )
            }
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.TaskAlt, contentDescription = "")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Water Sound Ok")
            }
        }
    }
}