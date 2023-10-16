package com.example.lifegoals

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    private val taskDao: TaskDao

    init {
        taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
    }

    suspend fun insertTask(task: Task) {
        Log.d("Debug 2: ","insertTask in TaskViewModel")
        repository.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        repository.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        repository.deleteTask(task)
    }

    fun getTaskById(id: Long): LiveData<Task?> {
        return liveData {
            val task = withContext(Dispatchers.IO) {
                repository.getTaskById(id)
            }
            emit(task)
        }
    }

    suspend fun getTasks(): Flow<List<Task>> {
        return repository.getTasks()
    }
}