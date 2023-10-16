package com.example.lifegoals

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTask : AppCompatActivity() {
    private lateinit var taskViewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        // add select box to view
        val typeSpinner = findViewById<Spinner>(R.id.typeSpinner)
        val adapter = ArrayAdapter.createFromResource(this, R.array.task_types, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        typeSpinner.adapter = adapter

        // cancel
        val cancelButton = findViewById<Button>(R.id.cancel)
        cancelButton.setOnClickListener {
            Toast.makeText(this, "cancel Task", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // save task
        val saveButton = findViewById<Button>(R.id.save)
        saveButton.setOnClickListener {
            Toast.makeText(this, "save Task", Toast.LENGTH_LONG).show()

            var task = findViewById<EditText>(R.id.editTextTextMultiLine)
            val text = task.text.toString()
            val type = findViewById<Spinner>(R.id.typeSpinner).selectedItem.toString()
            Log.d("Debug 1: ", text)

            val newTask = Task(id = null, task = text, type = type, isComplete = false)
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    taskViewModel.insertTask(newTask)
                }
                // Do something after the task is inserted
                Log.d("Debug 4: ", "end AddTask")
                task.setText("")
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}