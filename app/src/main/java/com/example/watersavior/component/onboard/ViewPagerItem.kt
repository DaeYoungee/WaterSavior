package com.example.watersavior.component.onboard

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.example.watersavior.R

sealed class ViewPagerItem(val image: Int, val title: String, val explain: String) {
    object Item1 : ViewPagerItem(
        image = R.drawable.onboard_water,
        title = "Water flow measurement",
        explain = "You can determine the water flow by listening to the sound of water."
    )

    object Item2 : ViewPagerItem(
        image = R.drawable.onboard_enviroment,
        title = "Environmental conservation",
        explain = "Conserving water can contribute to environmental preservation."
    )

    object Item3 : ViewPagerItem(
        image = R.drawable.onboard_bill,
        title = "Water tax calculation",
        explain = "You can save costs by checking and verifying your water tax."
    )

    object Item4 : ViewPagerItem(
        image = R.drawable.onboard_statistic,
        title = "Statistical reporting",
        explain = "You can check the previous water tax through statistics."
    )
}

