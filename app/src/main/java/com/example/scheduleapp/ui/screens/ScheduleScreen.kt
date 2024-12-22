package com.example.scheduleapp.ui.screens

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.scheduleapp.data.Lesson
import com.example.scheduleapp.viewmodel.ScheduleViewModel
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(viewModel: ScheduleViewModel, onNavigateBack: () -> Unit) {
    val schedule by viewModel.schedule.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val currentCategory by viewModel.currentCategory.collectAsState()

    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var selectedDate by remember { mutableStateOf(sdf.format(Date())) }
    var showDatePicker by remember { mutableStateOf(false) } // Состояние для отображения DatePickerDialog

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Расписание", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF1976D2))
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Отображение DatePickerDialog при необходимости
            if (showDatePicker) {
                DatePickerDialog(
                    LocalContext.current,
                    { _, year, month, day ->
                        calendar.set(year, month, day)
                        selectedDate = sdf.format(calendar.time)
                        showDatePicker = false // Закрываем диалог после выбора даты
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }

            // Выбор даты через календарь
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Дата: $selectedDate",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = { showDatePicker = true }, // Управление состоянием для открытия диалога
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
                ) {
                    Text("Выбрать дату", color = Color.White)
                }
            }

            // Переключение категории
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryButton("Группа", currentCategory == "Group") { viewModel.switchCategory("Group") }
                CategoryButton("Преподаватель", currentCategory == "Teacher") { viewModel.switchCategory("Teacher") }
                CategoryButton("Кабинет", currentCategory == "Room") { viewModel.switchCategory("Room") }
            }

            // Загрузка расписания
            Button(
                onClick = { viewModel.loadSchedule(selectedDate) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp)
            ) {
                Text("Загрузить расписание", color = Color.White)
            }

            // Сообщение об ошибке
            errorMessage?.let {
                Text(
                    text = "Ошибка: $it",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(schedule.groupBy {
                    when (currentCategory) {
                        "Group" -> it.group_name
                        "Teacher" -> it.Name
                        "Room" -> it.RoomNumber
                        else -> it.group_name // значение по умолчанию
                    }
                }.toList()) { (key, lessons) ->
                    val header = when (currentCategory) {
                        "Group" -> "Группа: $key"
                        "Teacher" -> "Преподаватель: $key"
                        "Room" -> "Кабинет: $key"
                        else -> "Группа: $key"
                    }

                    ScheduleCard(header, lessons, currentCategory)
                }
            }

        }
    }
}

@Composable
fun ScheduleCard(header: String, lessons: List<Lesson>, currentCategory: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = header,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1976D2)
            )
            lessons.forEachIndexed { index, lesson ->
                Column(
                    modifier = Modifier.padding(vertical = 8.dp)
                ) {
                    Divider(thickness = 1.dp, color = Color(0xFFBBDEFB))
                    Text(
                        text = "Пара ${index + 1}",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF64B5F6)
                    )

                    // Меняем контент внутри карточки в зависимости от категории
                    when (currentCategory) {
                        "Group" -> {
                            Text("Кабинет: ${lesson.RoomNumber ?: "Не указано"}")
                            Text("Преподаватель: ${lesson.Name ?: "Не указано"}")
                        }
                        "Teacher" -> {
                            Text("Группа: ${lesson.group_name ?: "Не указано"}")
                            Text("Кабинет: ${lesson.RoomNumber ?: "Не указано"}")
                        }
                        "Room" -> {
                            Text("Группа: ${lesson.group_name ?: "Не указано"}")
                            Text("Преподаватель: ${lesson.Name ?: "Не указано"}")
                        }
                    }

                    Text("Тип занятия: ${lesson.lesson_type ?: "Не указано"}")
                    Text("Предмет: ${lesson.SubjectName ?: "Не указано"}")
                }
            }
        }
    }
}

@Composable
fun CategoryButton(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF1976D2) else Color(0xFFE3F2FD),
            contentColor = if (isSelected) Color.White else Color(0xFF1976D2)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(label)
    }
}
