package com.example.scheduleapp.ui.screens

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scheduleapp.data.Lesson
import com.example.scheduleapp.viewmodel.ScheduleViewModel
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel, onNavigateBack: () -> Unit) {
    val schedule by viewModel.schedule.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val currentCategory by remember { mutableStateOf(viewModel.currentCategory) }

    var selectedDate by remember { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(
        Date()
    )) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Расписание") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Выбор даты
            TextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                label = { Text("Дата (YYYY-MM-DD)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            // Переключение категории
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { viewModel.switchCategory("Group") }) { Text("Группа") }
                Button(onClick = { viewModel.switchCategory("Teacher") }) { Text("Преподаватель") }
                Button(onClick = { viewModel.switchCategory("Room") }) { Text("Кабинет") }
            }

            // Загрузка расписания
            Button(
                onClick = { viewModel.loadSchedule(selectedDate) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("Загрузить расписание")
            }

            // Сообщение об ошибке
            errorMessage?.let {
                Text(
                    text = "Ошибка: $it",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            // Список расписания
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(schedule) { lesson ->
                    LessonItem(lesson, currentCategory.toString())
                }
            }
        }
    }
}

@Composable
fun LessonItem(lesson: Lesson, category: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            when (category) {
                "Group" -> {
                    Text(text = "Группа: ${lesson.group_name}")
                    Text(text = "Преподаватель: ${lesson.Name}")
                }
                "Teacher" -> {
                    Text(text = "Преподаватель: ${lesson.Name}")
                    Text(text = "Группа: ${lesson.group_name}")
                }
                "Room" -> {
                    Text(text = "Кабинет: ${lesson.RoomNumber}")
                    Text(text = "Группа: ${lesson.group_name}")
                }
            }
            Text(text = "Предмет: ${lesson.SubjectName}")
            Text(text = "Тип занятия: ${lesson.lesson_type}")
        }
    }
}
