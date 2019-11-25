package com.example.amsatodo


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amsatodo.adapter.TaskAdapter
import com.example.amsatodo.db.DbManager
import com.example.amsatodo.model.Task
import kotlinx.android.synthetic.main.fragment_task_list.*

/**
 * A simple [Fragment] subclass.
 */
class TaskListFragment : Fragment() {
    private lateinit var taskList: MutableList<Task>
    lateinit var adapter: TaskAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_task_list, container, false)
    }

    override fun onResume() {
        super.onResume()
        val db = DbManager(context!!)
        taskList.clear()
        taskList.addAll(db.getTasks())
        adapter.notifyDataSetChanged()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = DbManager(view.context)
        taskList = db.getTasks()
        linearLayoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        adapter = TaskAdapter(taskList, context!!)
        taskRecyclerView.layoutManager = linearLayoutManager
        taskRecyclerView.adapter = adapter



    }


}
