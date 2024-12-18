package com.example.scheduleapp.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.scheduleapp.data.PreferencesManager

@Composable
fun QRScanScreen(
    onSuccess: (String) -> Unit // Функция обратного вызова при успешном сканировании
) {
    var qrContent by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Сканируйте QR-код", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Имитируем сканирование QR-кода (в будущем подключим библиотеку)
            val scannedQR = "ukit12345" // Замени на фактический результат сканирования
            if (scannedQR.contains("ukit")) {
                qrContent = scannedQR
                errorMessage = ""
                onSuccess(qrContent)
            } else {
                errorMessage = "Некорректный QR-код"
            }
        }) {
            Text(text = "Сканировать")
        }

        if (qrContent.isNotEmpty()) {
            Text(text = "QR-код успешно отсканирован: $qrContent", color = Color.Green)
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red)
        }
    }
}
@Composable
fun QRScanScreenWithStorage(context: Context, onNavigateToSchedule: () -> Unit) {
    val preferencesManager = PreferencesManager(context)

    QRScanScreen(onSuccess = { qrContent ->
        if (qrContent.contains("ukit")) {
            preferencesManager.saveQRContent(qrContent)
            Log.d("QRScan", "QR-код сохранён: $qrContent")
            onNavigateToSchedule() // Переход на экран расписания
        } else {
            Log.d("QRScan", "QR-код не содержит ключевое слово")
        }
    })
}
