package com.example.habit2mood.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_journal")
data class DailyJournal(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "tanggal")
    val tanggal: String, // YYYY-MM-DD

    @ColumnInfo(name = "skala_mood")
    val skalaMood: Int, // 1 to 5

    @ColumnInfo(name = "uraian_hari")
    val uraianHari: String
)
