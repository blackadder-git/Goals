package com.example.lifegoals

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(var tasks: List<Task>) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    // DELETE
    var onDeleteClickListener: ((Task) -> Unit)? = null

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        private val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
        val trashIcon = itemView.findViewById<ImageView>(R.id.trashIcon)

        fun bind(task: Task) {
            typeTextView.text = task.type
            taskTextView.text = task.task

            // modify text depending on whether isComplete equals true or false
            taskTextView.paintFlags = if (task.isComplete) {
                taskTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                taskTextView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val task = tasks[position]
                    // Invoke the click listener callback
                    itemClickListener?.onItemClick(task)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = tasks[position]
        holder.bind(currentTask)

        holder.trashIcon.setOnClickListener {
            val deletedTask = tasks[position]
            onDeleteClickListener?.invoke(deletedTask)
        }
    }

    // EDIT
    interface OnItemClickListener {
        fun onItemClick(task: Task)
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun updateTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }
}
