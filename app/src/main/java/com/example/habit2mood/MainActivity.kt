package com.example.habit2mood

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.habit2mood.data.AppDatabase
import com.example.habit2mood.data.Habit
import com.example.habit2mood.data.Habit2MoodRepository
import com.example.habit2mood.ui.HabitViewModel
import com.example.habit2mood.ui.HabitViewModelFactory
import com.example.habit2mood.ui.JournalViewModel
import com.example.habit2mood.ui.JournalViewModelFactory
import com.example.habit2mood.ui.MainScreen
import com.example.habit2mood.ui.theme.Habit2MoodTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi Database & Repository
        val database = AppDatabase.getDatabase(applicationContext)
        val repository = Habit2MoodRepository(
            habitDao = database.habitDao(),
            habitLogDao = database.habitLogDao(),
            dailyJournalDao = database.dailyJournalDao()
        )

        // Inisialisasi ViewModel
        val habitViewModel = ViewModelProvider(
            this,
            HabitViewModelFactory(repository)
        )[HabitViewModel::class.java]

        val journalViewModel = ViewModelProvider(
            this,
            JournalViewModelFactory(repository)
        )[JournalViewModel::class.java]

        setContent {
            Habit2MoodTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        habitViewModel = habitViewModel,
                        journalViewModel = journalViewModel
                    )
                }
            }
        }
    }
}