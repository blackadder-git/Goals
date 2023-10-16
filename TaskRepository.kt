package com.example.lifegoals

import android.util.Log
import kotlinx.coroutines.flow.Flow

// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#8
class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) {
        Log.d("Debug 3: ","insertTask in TaskRepositoryImp")
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.delete(task)
    }

    suspend fun getTaskById(id: Long): Task? {
        return taskDao.getTaskById(id)
    }

    fun getTasks(): Flow<List<Task>> {
        return taskDao.getTasks()
    }
}