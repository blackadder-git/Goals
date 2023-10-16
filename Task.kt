package com.example.lifegoals

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// contains all table related data
@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "task") val task: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "isComplete") val isComplete: Boolean = false
)
