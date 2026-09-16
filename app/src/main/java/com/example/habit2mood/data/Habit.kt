package com.example.habit2mood.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "habit")
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "nama_kebiasaan")
    val namaKebiasaan: String,

    @ColumnInfo(name = "target_frekuensi")
    val targetFrekuensi: Int
)
