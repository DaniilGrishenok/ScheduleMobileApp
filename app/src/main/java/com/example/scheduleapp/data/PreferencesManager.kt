package com.example.scheduleapp.data

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import com.example.scheduleapp.ui.screens.QRScanScreen

class PreferencesManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveQRContent(content: String) {
        sharedPreferences.edit()
            .putString("qr_content", content)
            .apply()
    }

    fun getQRContent(): String? {
        return sharedPreferences.getString("qr_content", null)
    }
    @Composable
    fun QRScanScreenWithStorage(context: Context) {
        val preferencesManager = PreferencesManager(context)

        QRScanScreen(onSuccess = { qrContent ->
            preferencesManager.saveQRContent(qrContent)
            Log.d("QRScan", "Сохранено: $qrContent")
        })
    }

}
