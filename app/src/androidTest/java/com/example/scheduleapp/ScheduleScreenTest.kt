package com.example.scheduleapp

import androidx.activity.compose.setContent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.scheduleapp.data.Lesson
import com.example.scheduleapp.ui.screens.ScheduleScreen
import com.example.scheduleapp.viewmodel.ScheduleViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScheduleScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    private val viewModel = mockk<ScheduleViewModel>(relaxed = true)

    @Test
    fun testScheduleScreenDisplaysCorrectly() {
        val mockSchedule = MutableStateFlow<List<Lesson>>(emptyList())
        val mockErrorMessage = MutableStateFlow<String?>(null)
        val mockCurrentCategory = MutableStateFlow("Group")

        every { viewModel.schedule } returns mockSchedule
        every { viewModel.errorMessage } returns mockErrorMessage
        every { viewModel.currentCategory } returns mockCurrentCategory

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                ScheduleScreen(viewModel = viewModel, onNavigateBack = {})
            }
        }

        composeTestRule.onNodeWithText("Расписание").assertIsDisplayed()
        composeTestRule.onNodeWithText("Выбрать дату").assertIsDisplayed()
        composeTestRule.onNodeWithText("Загрузить расписание").assertIsDisplayed()
    }
    @Test
    fun testCategoryButtonsAreDisplayed() {
        val mockSchedule = MutableStateFlow<List<Lesson>>(emptyList())
        val mockErrorMessage = MutableStateFlow<String?>(null)
        val mockCurrentCategory = MutableStateFlow("Group")

        every { viewModel.schedule } returns mockSchedule
        every { viewModel.errorMessage } returns mockErrorMessage
        every { viewModel.currentCategory } returns mockCurrentCategory

        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                ScheduleScreen(viewModel = viewModel, onNavigateBack = {})
            }
        }

        composeTestRule.onNodeWithText("Группа").assertIsDisplayed()
        composeTestRule.onNodeWithText("Преподаватель").assertIsDisplayed()
        composeTestRule.onNodeWithText("Кабинет").assertIsDisplayed()

        composeTestRule.onNodeWithText("Группа").performClick()
        composeTestRule.onNodeWithText("Преподаватель").performClick()
        composeTestRule.onNodeWithText("Кабинет").performClick()
    }

    @Test
    fun testCategorySelectionChangesCorrectly() {
        // Мокаем возвращаемые данные как StateFlow
        val mockSchedule = MutableStateFlow<List<Lesson>>(emptyList())
        val mockErrorMessage = MutableStateFlow<String?>(null)
        val mockCurrentCategory = MutableStateFlow("Group")

        // Устанавливаем мокированные данные в viewModel
        every { viewModel.schedule } returns mockSchedule
        every { viewModel.errorMessage } returns mockErrorMessage
        every { viewModel.currentCategory } returns mockCurrentCategory

        // Set content directly on the activity, ensuring it runs before assertions
        composeTestRule.activity.runOnUiThread {
            composeTestRule.activity.setContent {
                ScheduleScreen(viewModel = viewModel, onNavigateBack = {})
            }
        }

        composeTestRule.waitForIdle()
       composeTestRule.onNodeWithText("Группа")
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithText("Преподаватель").performClick()

         mockCurrentCategory.value = "Teacher"
        composeTestRule.onNodeWithText("Преподаватель")
            .assertIsDisplayed()
            .assertExists()

        composeTestRule.onNodeWithText("Кабинет").performClick()

        mockCurrentCategory.value = "Room"

        composeTestRule.onNodeWithText("Кабинет")
            .assertIsDisplayed()
            .assertExists()
    }

}
