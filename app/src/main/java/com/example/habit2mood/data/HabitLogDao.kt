package com.example.habit2mood.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitLogDao {
    @Query("SELECT * FROM habit_log WHERE tanggal_eksekusi = :tanggal")
    fun getLogsForDate(tanggal: String): Flow<List<HabitLog>>

    @Query("SELECT * FROM habit_log WHERE habit_id = :habitId")
    fun getLogsForHabit(habitId: Int): Flow<List<HabitLog>>

    @Query("SELECT * FROM habit_log ORDER BY tanggal_eksekusi DESC")
    fun getAllLogs(): Flow<List<HabitLog>>

    @Query("SELECT * FROM habit_log WHERE habit_id = :habitId AND tanggal_eksekusi = :tanggal LIMIT 1")
    suspend fun getLogForHabitAndDate(habitId: Int, tanggal: String): HabitLog?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: HabitLog): Long

    @Delete
    suspend fun deleteLog(log: HabitLog)

    @Query("DELETE FROM habit_log WHERE habit_id = :habitId AND tanggal_eksekusi = :tanggal")
    suspend fun deleteLogByHabitAndDate(habitId: Int, tanggal: String)
}
