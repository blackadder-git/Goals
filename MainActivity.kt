package com.example.lifegoals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.launch
import androidx.lifecycle.lifecycleScope


class MainActivity : AppCompatActivity(), TaskAdapter.OnItemClickListener {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addTaskButton = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        addTaskButton.setOnClickListener {
            Toast.makeText(this, "add Task", Toast.LENGTH_LONG).show()
            /*
            * Log.d - debug
            * Log.e - error
            * Log.i - information
            * Log.w - weird
            * Log.v - all
            * */
            Log.d("Debug", "add a new task")

            // open add task activity
            val intent = Intent(this, AddTask::class.java)
            // intent.putExtra("key", value)
            startActivity(intent)
        }


        // edit task
        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        taskAdapter = TaskAdapter(emptyList())
        recyclerView.adapter = taskAdapter

        taskAdapter.setOnItemClickListener(this)

        // load all tasks
        lifecycleScope.launch {
            showAllTasks()
        }
    }

    // cRud
    private suspend fun showAllTasks() {
        Toast.makeText(this@MainActivity, "show all tasks", Toast.LENGTH_LONG).show()

        lifecycleScope.launch {
            taskViewModel.getTasks().collect { tasks ->
                taskAdapter.updateTasks(tasks)
                taskAdapter.notifyDataSetChanged()
            }
        }

        taskAdapter.onDeleteClickListener = { deletedTask ->
            deleteTask(deletedTask)
        }
    }

    // delete
    private fun deleteTask(task: Task) {
        lifecycleScope.launch {
            taskViewModel.deleteTask(task)
            Toast.makeText(this@MainActivity, "Task deleted", Toast.LENGTH_SHORT).show()
        }
    }

    // edit
    override fun onItemClick(task: Task) {
        val intent = Intent(this, EditTask::class.java)
        intent.putExtra("taskId", task.id)
        startActivity(intent)
    }
}
