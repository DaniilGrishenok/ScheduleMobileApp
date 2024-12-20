package com.example.scheduleapp.navigation

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scheduleapp.data.ScheduleRepository
import com.example.scheduleapp.ui.screens.QRScanScreenWithStorage
import com.example.scheduleapp.ui.screens.MainScheduleScreen
import com.example.scheduleapp.ui.screens.ScheduleScreen
import com.example.scheduleapp.utils.ScheduleViewModelFactory
import com.example.scheduleapp.viewmodel.ScheduleViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val repository = ScheduleRepository()

    // Создание ViewModel с помощью фабрики
    val scheduleViewModel: ScheduleViewModel = viewModel(
        factory = ScheduleViewModelFactory(repository)
    )

    NavHost(navController = navController, startDestination = "qr_scan") {
        composable("qr_scan") {
            QRScanScreenWithStorage(
                context = context,
                onNavigateToSchedule = { navController.navigate("schedule") }
            )
        }
        composable("schedule") {
            ScheduleScreen(
                viewModel = scheduleViewModel, // Передача ViewModel
                onNavigateBack = { navController.popBackStack() } // Реализация возврата назад
            )
        }
    }
}
