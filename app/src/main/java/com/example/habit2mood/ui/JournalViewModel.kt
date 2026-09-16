package com.example.habit2mood.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.habit2mood.data.DailyJournal
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

class JournalViewModel(private val repository: Habit2MoodRepository) : ViewModel() {

    private val _selectedDate = MutableStateFlow(LocalDate.now().toString())
    val selectedDate: StateFlow<String> = _selectedDate.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val journalForSelectedDate: StateFlow<DailyJournal?> = _selectedDate.flatMapLatest { date ->
        repository.getJournalForDate(date)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    val allJournals: StateFlow<List<DailyJournal>> = repository.allJournals.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _skalaMoodInput = MutableStateFlow(3)
    val skalaMoodInput: StateFlow<Int> = _skalaMoodInput.asStateFlow()

    private val _uraianHariInput = MutableStateFlow("")
    val uraianHariInput: StateFlow<String> = _uraianHariInput.asStateFlow()

    fun selectDate(date: String) {
        _selectedDate.value = date
    }

    fun setMoodInput(skala: Int) {
        _skalaMoodInput.value = skala
    }

    fun setUraianInput(text: String) {
        _uraianHariInput.value = text
    }

    fun loadJournalData(journal: DailyJournal?) {
        if (journal != null) {
            _skalaMoodInput.value = journal.skalaMood
            _uraianHariInput.value = journal.uraianHari
        } else {
            _skalaMoodInput.value = 3
            _uraianHariInput.value = ""
        }
    }

    fun saveJournal(existingId: Int = 0) {
        val date = _selectedDate.value
        val skala = _skalaMoodInput.value
        val uraian = _uraianHariInput.value.trim()

        if (uraian.isBlank()) return

        viewModelScope.launch {
            repository.saveJournal(
                DailyJournal(
                    id = existingId,
                    tanggal = date,
                    skalaMood = skala,
                    uraianHari = uraian
                )
            )
        }
    }

    fun deleteJournal(journal: DailyJournal) {
        viewModelScope.launch {
            repository.deleteJournal(journal)
        }
    }
}

class JournalViewModelFactory(private val repository: Habit2MoodRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(JournalViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return JournalViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
