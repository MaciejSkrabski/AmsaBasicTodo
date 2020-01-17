package com.example.amsatodo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.amsatodo.MainActivity
import com.example.amsatodo.R
import com.example.amsatodo.model.Task
import com.example.amsatodo.db.DbManager
import kotlinx.android.synthetic.main.list_row.view.*

class TaskAdapter(private var taskList: MutableList<Task>, // TaskAdapter tworzy obiekty o polach zdefiniowanych w TaskViewHolder
                  private val context: Context) :
        RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_row, parent, false)
        return TaskViewHolder(view)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) { //sklejenie pól z ich danymi
        holder.titleContent.text = taskList[position].title
        holder.statusContent.text = taskList[position].status
        when (taskList[position].priority) {
            3 -> holder.priorityContent.text = "High"
            2 -> holder.priorityContent.text = "Medium"
            else -> holder.priorityContent.text = "Low"
        }
        holder.dateContent.text = taskList[position].date
        holder.deleteBtn.setOnClickListener {
            val db = DbManager(context)
            db.deleteTask(taskList[position].id)

            taskList.clear()
            taskList.addAll(db.getTasks())
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, taskList.size)
        }
        holder.view.setOnClickListener {
            val activity = context as MainActivity
            activity.replaceFragment("edit",taskList[position].id)

        }


    }

    class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view) { // takie pola mają obiekty w RecyclerView

        val titleContent: TextView = view.titleContent
        val statusContent: TextView = view.statusContent
        val dateContent: TextView = view.dateContent
        val priorityContent: TextView = view.priorityContent
        val deleteBtn: Button

        init {
            deleteBtn = view.removeTaskBtn
        }
    }
}
