package com.example.scheduleapp.ui.screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.scheduleapp.viewmodel.MainScheduleViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScheduleScreen(
    viewModel: MainScheduleViewModel,
    onDateChange: (String) -> Unit,
    onCategorySelect: (String) -> Unit,
    onEntitySelect: (String) -> Unit,
    onFetchSchedule: () -> Unit
) {
    val groups by viewModel.groups.collectAsState().also {
        Log.d("MainScheduleScreen", "Observed Groups: $it")
    }
    val teachers by viewModel.teachers.collectAsState().also {
        Log.d("MainScheduleScreen", "Observed Teachers: $it")
    }
    val rooms by viewModel.rooms.collectAsState().also {
        Log.d("MainScheduleScreen", "Observed Rooms: $it")
    }


    // Логи для проверки данных
    Log.d("MainScheduleScreen", "Groups: ${groups.map { it.group_name }}")
    Log.d("MainScheduleScreen", "Teachers: ${teachers.map { it.Name }}")
    Log.d("MainScheduleScreen", "Rooms: ${rooms.map { it.RoomNumber }}")

    // Состояния для UI
    var selectedDate by remember { mutableStateOf(LocalDate.now().toString()) }
    var selectedCategory by remember { mutableStateOf("Groups") }
    var selectedEntity by remember { mutableStateOf("") }
    var isCategoryMenuExpanded by remember { mutableStateOf(false) }
    var isEntityMenuExpanded by remember { mutableStateOf(false) }

    // Динамический список для выбора в зависимости от категории
    val entities = when (selectedCategory) {
        "Groups" -> groups.map { it.group_name }
        "Teachers" -> teachers.map { it.Name }
        "Rooms" -> rooms.map { it.RoomNumber }
        else -> emptyList()
    }

    // Логируем выбранную категорию и связанные данные
    Log.d("MainScheduleScreen", "SelectedCategory: $selectedCategory")
    Log.d("MainScheduleScreen", "Entities: $entities")

    // Загружаем данные при старте экрана
    LaunchedEffect(Unit) {
        viewModel.groups.collect { newGroups ->
            Log.d("MainScheduleScreen", "Updated Groups: $newGroups")
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Выбор даты
        Text("Выберите дату", style = MaterialTheme.typography.titleMedium)
        TextField(
            value = selectedDate,
            onValueChange = {
                selectedDate = it
                onDateChange(it)
            },
            label = { Text("Дата") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Комбобокс для выбора категории
        Text("Выберите категорию", style = MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(
            expanded = isCategoryMenuExpanded,
            onExpandedChange = { isCategoryMenuExpanded = !isCategoryMenuExpanded }
        ) {
            TextField(
                value = selectedCategory,
                onValueChange = {},
                readOnly = true,
                label = { Text("Категория") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isCategoryMenuExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isCategoryMenuExpanded,
                onDismissRequest = { isCategoryMenuExpanded = false }
            ) {
                listOf("Groups", "Teachers", "Rooms").forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category) },
                        onClick = {
                            selectedCategory = category
                            onCategorySelect(category)
                            isCategoryMenuExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Комбобокс для выбора элемента
        Text("Выберите элемент", style = MaterialTheme.typography.titleMedium)
        ExposedDropdownMenuBox(
            expanded = isEntityMenuExpanded,
            onExpandedChange = { isEntityMenuExpanded = !isEntityMenuExpanded }
        ) {
            TextField(
                value = selectedEntity.ifEmpty { "Выберите элемент" },
                onValueChange = {},
                readOnly = true,
                label = { Text("Элемент") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isEntityMenuExpanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = isEntityMenuExpanded,
                onDismissRequest = { isEntityMenuExpanded = false }
            ) {
                entities.forEach { entity ->
                    DropdownMenuItem(
                        text = { Text(entity) },
                        onClick = {
                            selectedEntity = entity
                            onEntitySelect(entity)
                            isEntityMenuExpanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка для получения расписания
        Button(
            onClick = { onFetchSchedule() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Получить расписание")
        }
    }
}
