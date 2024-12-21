package com.example.scheduleapp.ui.screens


import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.remember
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.BarcodeView
import com.journeyapps.barcodescanner.DecoderFactory
import com.journeyapps.barcodescanner.DefaultDecoderFactory


@Composable
fun QRCodeScreen(onQRCodeScanned: (String) -> Unit) {
    val context = LocalContext.current

    var scanning by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Сканировать QR код", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // QR сканер
        AndroidView(
            factory = { ctx ->
                BarcodeView(ctx).apply {
                    // Настройка для сканера
                    val formats = listOf(BarcodeFormat.QR_CODE) // Поддержка только QR-кодов
                    decoderFactory = DefaultDecoderFactory(formats) // Указание форматов

                    // Обработка результатов сканирования
                    decodeContinuous(object : com.journeyapps.barcodescanner.BarcodeCallback {
                        override fun barcodeResult(result: BarcodeResult?) {
                            result?.let {
                                // Если QR код найден, передаем его в функцию onQRCodeScanned
                                onQRCodeScanned(it.text)
                            }
                        }

                        override fun possibleResultPoints(resultPoints: List<com.google.zxing.ResultPoint>?) {}
                    })
                    resume()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для завершения сканирования
        Button(onClick = {
            scanning = false
            Toast.makeText(context, "Завершить сканирование", Toast.LENGTH_SHORT).show()
        }) {
            Text("Завершить сканирование")
        }
    }
}