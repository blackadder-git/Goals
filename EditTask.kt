package com.example.lifegoals

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditTask : AppCompatActivity() {

    private lateinit var taskViewModel: TaskViewModel
    private lateinit var editTextTask: EditText
    private lateinit var spinnerType: Spinner
    private lateinit var checkBoxComplete: CheckBox
    private lateinit var saveButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        taskViewModel = ViewModelProvider(this).get(TaskViewModel::class.java)

        editTextTask = findViewById(R.id.editTextTextMultiLine)
        spinnerType = findViewById(R.id.typeSpinner)
        checkBoxComplete = findViewById(R.id.completed)
        saveButton = findViewById(R.id.save)

        // Retrieve the task ID from the intent extras
        val taskId = intent.getLongExtra("taskId", -1)

        // Fetch the task details from the database
        lifecycleScope.launch {
            val task = withContext(Dispatchers.IO) {
                taskViewModel.getTaskById(taskId)
            }
            task.observe(this@EditTask, Observer { task ->
                if (task != null) {
                    // Populate the UI with the task details
                    editTextTask.setText(task.task)
                    task.type?.let { getSpinnerIndex(it) }?.let { spinnerType.setSelection(it) }
                    checkBoxComplete.isChecked = task.isComplete
                }
            })
        }

        // cancel
        val cancelButton = findViewById<Button>(R.id.cancel)
        cancelButton.setOnClickListener {
            Toast.makeText(this, "cancel Task", Toast.LENGTH_LONG).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // save
        saveButton.setOnClickListener {
            // Retrieve the updated values from the UI
            val updatedTask = Task(
                id = taskId,
                task = editTextTask.text.toString(),
                type = spinnerType.selectedItem.toString(),
                isComplete = checkBoxComplete.isChecked
            )

            // Update the task in the database
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    taskViewModel.updateTask(updatedTask)
                }
                // Finish the activity
                finish()
            }
        }
    }

    private fun getSpinnerIndex(type: String): Int {
        val adapter = spinnerType.adapter
        for (index in 0 until adapter.count) {
            if (type == adapter.getItem(index).toString()) {
                return index
            }
        }
        return 0
    }
}
