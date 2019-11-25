package com.example.amsatodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment("show")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this)
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.addTask -> {
                val intent = Intent(this@MainActivity, AddTaskActivity::class.java)
                startActivity(intent)
            }
        }
            return true

    }

    fun replaceFragment(fragment: String, id:Int=0) {


        val transaction = supportFragmentManager.beginTransaction()
        when (fragment) {
            "edit" ->
                if(id>0){
                    val editFragment = EditTaskFragment()
                    val bundle = Bundle()
                    bundle.putInt("id",id)
                    editFragment.arguments = bundle
                    transaction.replace(R.id.fragment_container, editFragment)
                }

            "show" -> transaction.replace(R.id.fragment_container, TaskListFragment())
        }
        transaction.commit()
    }

}
