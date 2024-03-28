package com.example.watersavior.component.statistics

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.watersavior.R
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InputDayTextField(
    value: String,
    onValueChange: (String) -> Unit,
    selection: LocalDate?,
    textFormat: (LocalDate) -> String,
    title: String = "Start Day",
    modifier: Modifier = Modifier
) {
    LaunchedEffect(key1 = selection) {
        selection?.let {
            onValueChange(textFormat(it))
        } ?: onValueChange("")
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .aspectRatio(1.1f),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(id = R.color.main_blue),
                unfocusedBorderColor = colorResource(id = R.color.border_grey),
                unfocusedContainerColor = colorResource(id = R.color.textfield_container),
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            readOnly = selection != null
        )
    }
}
