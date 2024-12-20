package com.example.scheduleapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.scheduleapp.data.Lesson
import com.example.scheduleapp.data.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ScheduleViewModel(private val repository: ScheduleRepository) : ViewModel() {

    private val _schedule = MutableStateFlow<List<Lesson>>(emptyList())
    val schedule: StateFlow<List<Lesson>> = _schedule

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _currentCategory = MutableStateFlow("Group") // Категория по умолчанию — "Group"
    val currentCategory: StateFlow<String> = _currentCategory

    /**
     * Переключение между категориями: "Group", "Teacher", "Room".
     */
    fun switchCategory(category: String) {
        _currentCategory.value = category
    }

    /**
     * Загрузка расписания для текущей категории на заданную дату.
     */
    fun loadSchedule(date: String) {
        viewModelScope.launch {
            when (_currentCategory.value) {
                "Group" -> {
                    repository.getScheduleForDay(
                        date = date,
                        onResult = { lessons ->
                            _schedule.value = lessons
                            _errorMessage.value = null
                        },
                        onError = { throwable ->
                            _errorMessage.value = throwable.localizedMessage
                        }
                    )
                }
                "Teacher" -> {
                    repository.getScheduleForDay(
                        date = date,
                        onResult = { lessons ->
                            _schedule.value = lessons
                            _errorMessage.value = null
                        },
                        onError = { throwable ->
                            _errorMessage.value = throwable.localizedMessage
                        }
                    )
                }
                "Room" -> {
                    repository.getScheduleForDay(
                        date = date,
                        onResult = { lessons ->
                            _schedule.value = lessons
                            _errorMessage.value = null
                        },
                        onError = { throwable ->
                            _errorMessage.value = throwable.localizedMessage
                        }
                    )
                }
                else -> {
                    _errorMessage.value = "Неверная категория"
                }
            }
        }
    }
}
