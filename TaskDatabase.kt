package com.example.lifegoals

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// https://developer.android.com/codelabs/android-room-with-a-view-kotlin#7
@Database(
    entities = [Task::class],
    version = 1,
    exportSchema = false
)
abstract class TaskDatabase: RoomDatabase() {
    // abstract val doa: TaskDao
    abstract fun taskDao(): TaskDao

    companion object {
        // define singleton
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        // return singleton
        fun getDatabase(context: Context): TaskDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TaskDatabase::class.java,
                    "task_database"
                ).build()
                INSTANCE = instance
                // return
                instance
            }
        }
    }
}