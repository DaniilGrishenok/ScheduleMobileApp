package com.example.scheduleapp.viewmodel



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.data.Group
import com.example.scheduleapp.data.Room
import com.example.scheduleapp.data.ScheduleRepository
import com.example.scheduleapp.data.Teacher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log

class MainScheduleViewModel : ViewModel() {
    private val repository = ScheduleRepository()

    // StateFlow для отслеживания состояния
    private val _groups = MutableStateFlow<List<Group>>(emptyList())
    val groups: StateFlow<List<Group>> get() = _groups



    private val _teachers = MutableStateFlow<List<Teacher>>(emptyList())
    val teachers: StateFlow<List<Teacher>> = _teachers

    private val _rooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms: StateFlow<List<Room>> = _rooms

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // Загрузка данных
    fun loadData() {
        Log.d("MainScheduleViewModel", "loadData called")
        loadGroups()
        loadTeachers()
        loadRooms()
    }

    private fun loadGroups() {
        viewModelScope.launch {
            repository.getGroups(
                onResult = { groups ->
                    _groups.value = groups
                    Log.d("MainScheduleViewModel", "Loaded Groups: ${groups.size}")
                    // Вывод содержимого списка в лог
                    groups.forEach { group ->
                        Log.d("MainScheduleViewModel", "Group: $group")
                    }
                    // Либо одним сообщением
                    Log.d("MainScheduleViewModel", "All Groups: ${groups.joinToString(separator = ", ") { it.group_name }}")
                },
                onError = { error ->
                    _error.value = "Ошибка загрузки групп: ${error.message}"
                    Log.e("MainScheduleViewModel", "Error loading groups: ${error.message}")
                }
            )
        }
    }


    private fun loadTeachers() {
        viewModelScope.launch {
            repository.getTeachers(
                onResult = {
                    _teachers.value = it
                    Log.d("MainScheduleViewModel", "Loaded Teachers: ${it.size}") // Логирование
                },
                onError = { _error.value = "Ошибка загрузки преподавателей: ${it.message}" }
            )
        }
    }

    private fun loadRooms() {
        viewModelScope.launch {
            repository.getRooms(
                onResult = {
                    _rooms.value = it
                    Log.d("MainScheduleViewModel", "Loaded Rooms: ${it.size}") // Логирование
                },
                onError = { _error.value = "Ошибка загрузки кабинетов: ${it.message}" }
            )
        }
    }
}
