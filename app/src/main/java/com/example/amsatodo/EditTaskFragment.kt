package com.example.amsatodo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.amsatodo.db.DbManager
import com.example.amsatodo.model.Task
import kotlinx.android.synthetic.main.fragment_edit_task.*

/**
 * A simple [Fragment] subclass.
 */
class EditTaskFragment : Fragment() {
    lateinit var db: DbManager
    var taskId: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = DbManager(context!!)
        taskId = arguments!!.getInt("id")
        val task = db.getDetailsAboutTask(taskId)
        fillFields(task)
        doneTaskBtn.setOnClickListener {
            db.doneTask(taskId)
            Toast.makeText(activity, "Done", Toast.LENGTH_SHORT).show()
        }
        editTaskBtn.setOnClickListener { editTask() }
        activity?.title = "Edit task"



    }

    fun editTask() {
        val title = titleInput.text.toString()
        val description = descriptionInput.text.toString()
        val priority = when (priorityRadio.checkedRadioButtonId) {
            R.id.HighPriority -> 3
            R.id.MediumPriority -> 2
            else -> 1
        }
        db.updateTask(taskId, title, description, priority)
        Toast.makeText(activity, "Edited", Toast.LENGTH_SHORT).show()
    }

    fun fillFields(task: Task) {
        titleInput.setText(task.title)
        descriptionInput.setText(task.description)
        when (task.priority) {
            3 -> priorityRadio.check(R.id.HighPriority)
            2 -> priorityRadio.check(R.id.MediumPriority)
            else -> priorityRadio.check(R.id.LowPriority)
        }


    }



}
