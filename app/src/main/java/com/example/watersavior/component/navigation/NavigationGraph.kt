package com.example.watersavior.component.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.watersavior.screen.home.HomeScreen
import com.example.watersavior.screen.home.ResultScreen
import com.example.watersavior.screen.statistices.StatisticesScreen
import com.example.watersavior.viewmodel.RecordViewModel
import com.example.watersavior.viewmodel.StatisticesViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    recordViewModel: RecordViewModel,
    statisticViewModel: StatisticesViewModel
) {
    NavHost(navController = navController, startDestination = BottomNavItem.HomeScreen.screenRoute) {
        composable(BottomNavItem.HomeScreen.screenRoute) {
            if (!recordViewModel.isResult.value) {
                HomeScreen(viewModel = recordViewModel)
            } else {
                ResultScreen(viewModel = recordViewModel)
            }
        }
        composable(BottomNavItem.StatisticScreen.screenRoute) {
            StatisticesScreen(statisticViewModel = statisticViewModel)
        }
        composable(BottomNavItem.SettingScreen.screenRoute) {

        }
    }
}