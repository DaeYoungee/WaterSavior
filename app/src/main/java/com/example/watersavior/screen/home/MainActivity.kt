package com.example.watersavior.screen.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.watersavior.component.navigation.BottomNavigation
import com.example.watersavior.component.navigation.NavigationGraph
import com.example.watersavior.viewmodel.RecordViewModel
import com.example.watersavior.ui.theme.WaterSaviorTheme
import com.example.watersavior.viewmodel.StatisticesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val recordViewModel by viewModels<RecordViewModel>()
    private val statisticViewModel by viewModels<StatisticesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            WaterSaviorTheme {
                Scaffold(bottomBar = { BottomNavigation(navController = navController, viewModel = recordViewModel) }) {
                    Box(modifier = Modifier.padding(it)) {
                        NavigationGraph(recordViewModel = recordViewModel, statisticViewModel = statisticViewModel, navController = navController)
                    }
                }
            }
        }
    }
}