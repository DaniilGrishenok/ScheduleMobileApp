package com.example.scheduleapp

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scheduleapp.ui.screens.QRCodeScreen
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
@RunWith(AndroidJUnit4::class)
class ScheduleViewModelInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testQRCodeScreenDisplaysCorrectly() {
        val mockQRCodeScanned: (String) -> Unit = mockk(relaxed = true)

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                QRCodeScreen(onQRCodeScanned = mockQRCodeScanned)
            }
        }
        composeTestRule.onNodeWithText("Сканировать QR код").assertIsDisplayed()
        composeTestRule.onNodeWithText("Завершить сканирование").assertIsDisplayed()
    }

    @Test
    fun testQRCodeScreenOnScanComplete() {
        val mockQRCodeScanned: (String) -> Unit = mockk(relaxed = true)

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                QRCodeScreen(onQRCodeScanned = mockQRCodeScanned)
            }
        }

        composeTestRule.onNodeWithText("Завершить сканирование").performClick()

        verify(exactly = 0) { mockQRCodeScanned(any()) }
    }
}
