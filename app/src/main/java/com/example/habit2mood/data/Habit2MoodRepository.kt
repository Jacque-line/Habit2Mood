package com.example.habit2mood.data

import kotlinx.coroutines.flow.Flow

class Habit2MoodRepository(
    private val habitDao: HabitDao,
    private val habitLogDao: HabitLogDao,
    private val dailyJournalDao: DailyJournalDao
) {
    // Habit Operations
    val allHabits: Flow<List<Habit>> = habitDao.getAllHabits()

    suspend fun insertHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit)
    }

    suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit)
    }

    suspend fun deleteHabit(habit: Habit) {
        habitDao.deleteHabit(habit)
    }

    // HabitLog Operations
    fun getLogsForDate(tanggal: String): Flow<List<HabitLog>> {
        return habitLogDao.getLogsForDate(tanggal)
    }

    val allHabitLogs: Flow<List<HabitLog>> = habitLogDao.getAllLogs()

    suspend fun toggleHabitLog(habitId: Int, tanggal: String): Boolean {
        val existingLog = habitLogDao.getLogForHabitAndDate(habitId, tanggal)
        return if (existingLog != null) {
            habitLogDao.deleteLog(existingLog)
            false
        } else {
            habitLogDao.insertLog(HabitLog(habitId = habitId, tanggalEksekusi = tanggal))
            true
        }
    }

    // DailyJournal Operations
    fun getJournalForDate(tanggal: String): Flow<DailyJournal?> {
        return dailyJournalDao.getJournalForDate(tanggal)
    }

    val allJournals: Flow<List<DailyJournal>> = dailyJournalDao.getAllJournals()

    suspend fun saveJournal(journal: DailyJournal) {
        val existing = dailyJournalDao.getJournalForDate(journal.tanggal)
        // Wait for flow value or simple query: if ID exists or existing for date
        if (journal.id != 0) {
            dailyJournalDao.updateJournal(journal)
        } else {
            dailyJournalDao.insertJournal(journal)
        }
    }

    suspend fun saveOrUpdateJournalForDate(tanggal: String, skalaMood: Int, uraianHari: String) {
        val existing = dailyJournalDao.getJournalForDate(tanggal)
        // Note: For direct sync saving:
        dailyJournalDao.insertJournal(
            DailyJournal(
                tanggal = tanggal,
                skalaMood = skalaMood,
                uraianHari = uraianHari
            )
        )
    }

    suspend fun deleteJournal(journal: DailyJournal) {
        dailyJournalDao.deleteJournal(journal)
    }
}
