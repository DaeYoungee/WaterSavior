package com.example.watersavior.screen.onboarding

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.watersavior.R
import com.example.watersavior.component.common.BlueButton
import com.example.watersavior.component.common.WhiteButton
import com.example.watersavior.component.onboard.OnBoardViewPager

@RequiresApi(Build.VERSION_CODES.S)
@Preview(showBackground = true)
@Composable
fun OnBoardScreen(onClick:() -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.background_grey))
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Introducing the various features of our application!",
            modifier = Modifier.padding(horizontal = 70.dp),
            fontSize = 23.sp,
            fontWeight = FontWeight.Bold
        )
        OnBoardViewPager(modifier = Modifier)
        Spacer(modifier = Modifier.height(40.dp))
        Column(modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(120.dp), verticalArrangement = Arrangement.SpaceBetween) {
            BlueButton(text = "Sign up") {onClick()}
            WhiteButton(text = "Sign in") {}
        }

    }
}

