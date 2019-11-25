package com.example.amsatodo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.amsatodo.db.DbManager
import kotlinx.android.synthetic.main.activity_add_task.*
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)
        addTaskBtn.setOnClickListener { addTask() }
        title = "Add task"
    }
    fun addTask(){
        val title = titleInput.text.toString()
        val description = descriptionInput.text.toString()
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val currentDate: String = sdf.format(Date())
        val priority = when(priorityRadio.checkedRadioButtonId){
            R.id.HighPriority -> 3
            R.id.MediumPriority -> 2
            else -> 1
        }
        val db = DbManager(this)
        db.insertTask(title,description,priority,currentDate)
        Toast.makeText(this,"Added",Toast.LENGTH_SHORT).show()
        clearFields()
    }
    fun clearFields(){
        titleInput.setText("")
        descriptionInput.setText("")
        priorityRadio.clearCheck()
    }
}
