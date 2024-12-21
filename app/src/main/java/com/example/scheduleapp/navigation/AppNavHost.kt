package com.example.scheduleapp.navigation

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.scheduleapp.data.ScheduleRepository
import com.example.scheduleapp.ui.screens.QRCodeScreen
import com.example.scheduleapp.ui.screens.ScheduleScreen
import com.example.scheduleapp.utils.ScheduleViewModelFactory
import com.example.scheduleapp.viewmodel.ScheduleViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val repository = ScheduleRepository()

    val scheduleViewModel: ScheduleViewModel = viewModel(
        factory = ScheduleViewModelFactory(repository)
    )

    NavHost(navController = navController, startDestination = "qr_scan") {
        composable("qr_scan") {
            QRCodeScreen(
                onQRCodeScanned = { qrCode ->
                    // Проверка QR-кода на наличие "ukit"
                    if (qrCode.contains("ukit", ignoreCase = true)) {
                        navController.navigate("schedule")
                    } else {
                        Toast.makeText(context, "QR код не верен. Попробуйте еще раз.", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
        composable("schedule") {
            ScheduleScreen(
                viewModel = scheduleViewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
