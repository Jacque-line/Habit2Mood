package com.example.habit2mood.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyJournalDao {
    @Query("SELECT * FROM daily_journal WHERE tanggal = :tanggal LIMIT 1")
    fun getJournalForDate(tanggal: String): Flow<DailyJournal?>

    @Query("SELECT * FROM daily_journal ORDER BY tanggal DESC")
    fun getAllJournals(): Flow<List<DailyJournal>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(journal: DailyJournal): Long

    @Update
    suspend fun updateJournal(journal: DailyJournal)

    @Delete
    suspend fun deleteJournal(journal: DailyJournal)
}
