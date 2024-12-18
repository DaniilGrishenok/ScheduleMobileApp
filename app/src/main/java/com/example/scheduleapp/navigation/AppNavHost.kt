package com.example.scheduleapp.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.scheduleapp.ui.screens.QRScanScreenWithStorage
import com.example.scheduleapp.ui.screens.ScheduleScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scheduleapp.ui.screens.QRScanScreenWithStorage
import com.example.scheduleapp.ui.screens.ScheduleScreen

@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current // Получение контекста из Compose

    NavHost(navController = navController, startDestination = "qr_scan") {
        composable("qr_scan") {
            QRScanScreenWithStorage(
                context = context,
                onNavigateToSchedule = { navController.navigate("schedule") }
            )
        }
        composable("schedule") {
            ScheduleScreen()
        }
    }
}

