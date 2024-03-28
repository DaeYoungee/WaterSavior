package com.example.watersavior.component.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Equalizer
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

const val HOME = "homeScreen"
const val STATISTIC = "statisticScreen"
const val SETTING = "settingScreen"


sealed class BottomNavItem(
    val screenRoute: String,
    var bottomIcon: ImageVector,
    val bottomTitle: String,
) {
    data object HomeScreen : BottomNavItem(
        screenRoute = HOME,
        bottomIcon = Icons.Outlined.Home,
        bottomTitle = "Home",
    )
    data object StatisticScreen : BottomNavItem(
        screenRoute = STATISTIC,
        bottomIcon = Icons.Outlined.Equalizer,
        bottomTitle = "Statistics",
    )
    data object SettingScreen : BottomNavItem(
        screenRoute = SETTING,
        bottomIcon = Icons.Outlined.Person,
        bottomTitle = "Setting",
    )
}
