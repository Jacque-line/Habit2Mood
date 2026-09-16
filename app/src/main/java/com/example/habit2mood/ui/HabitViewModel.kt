package com.example.habit2mood.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habit2mood.data.Habit
import com.example.habit2mood.data.HabitLog
import com.example.habit2mood.data.Habit2MoodRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class HabitViewModel(private val repository: Habit2MoodRepository) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now().toString())
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    val habits: StateFlow<List<Habit>> = repository.allHabits.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val logsForSelectedDate: StateFlow<List<HabitLog>> = _selectedDate.flatMapLatest { date ->
        repository.getLogsForDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val allLogs: StateFlow<List<HabitLog>> = repository.allHabitLogs.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun addHabit(nama: String, targetFrekuensi: Int) {
        if (nama.isBlank() || targetFrekuensi <= 0) return
        viewModelScope.launch {
            repository.insertHabit(
                Habit(
                    namaKebiasaan = nama.trim(),
                    targetFrekuensi = targetFrekuensi
                )
            )
        }
    }

    fun deleteHabit(habit: Habit) {
        viewModelScope.launch {
            repository.deleteHabit(habit)
        }
    }

    fun toggleHabitLog(habitId: Int) {
        viewModelScope.launch {
            repository.toggleHabitLog(habitId, _selectedDate.value)
        }
    }
}

class HabitViewModelFactory(private val repository: Habit2MoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
