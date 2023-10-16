package com.example.lifegoals

import androidx.room.*
import kotlinx.coroutines.flow.Flow

// https://developer.android.com/training/data-storage/room/accessing-data
@Dao
interface TaskDao {

    // https://developer.android.com/reference/androidx/room/Insert
    // e.g. INSERT INTO tasks (task, type, isComplete) VALUES (task, type, false)
    @Insert
    suspend fun insertTask(task: Task): Long?

    // https://developer.android.com/reference/androidx/room/Update
    // e.g. UPDATE tasks SET task = task, type = type, isComplete = isComplete WHERE id = id
    @Update
    suspend fun updateTask(task: Task)

    // https://developer.android.com/reference/androidx/room/Delete
    // e.g. DELETE FROM tasks WHERE id = id
    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * FROM tasks WHERE id = :id")
    suspend fun getTaskById(id: Long): Task?

    @Query("SELECT * FROM tasks")
    fun getTasks(): Flow<List<Task>>

    @Query("SELECT COUNT(*) FROM tasks")
    suspend fun getTaskCount(): Int
}