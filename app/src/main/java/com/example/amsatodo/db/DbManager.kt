package com.example.amsatodo.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.amsatodo.model.Task

class DbManager(context: Context) : SQLiteOpenHelper(context, DBNAME, null, DBVERSION) {
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLENAME")
        onCreate(db)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sql = "CREATE TABLE $TABLENAME(" +
                "$IDCOLUMN INTEGER primary key autoincrement," +
                "$TITLECOLUMN text," +
                "$DESCRIPTIONCOLUMN text," +
                "$PRIORITYCOLUMN INTEGER," +
                "$STATUSCOLUMN text default todo," +
                "$DATECOLUMN text)"

        db?.execSQL(sql)

    }

    companion object {
        val DBNAME = "todos.db"
        val DBVERSION = 2
        val TABLENAME = "tasks"
        val IDCOLUMN = "id"
        val TITLECOLUMN = "title"
        val DESCRIPTIONCOLUMN = "description"
        val PRIORITYCOLUMN = "priority"
        val STATUSCOLUMN = "status"
        val DATECOLUMN = "date"
    }

    fun getTasks(): MutableList<Task> {
        val db = readableDatabase
        val columns = arrayOf(IDCOLUMN, TITLECOLUMN, PRIORITYCOLUMN, STATUSCOLUMN, DATECOLUMN)
        val cursor = db.query(TABLENAME, columns, null, null,null, null, "$PRIORITYCOLUMN DESC",null)
        val taskList = mutableListOf<Task>()
        while (cursor.moveToNext()) {
            val title = cursor.getString(1)
            val priority = cursor.getInt(2)
            val status = cursor.getString(3)
            val date = cursor.getString(4)
            val t = Task(title, priority, status, date)
            t.id = cursor.getInt(0)
            taskList.add(t)
        }
        cursor.close()
        return taskList
    }

    fun insertTask(title:String,description: String,priority:Int,date:String) {
        val db = writableDatabase

        val contentValue = ContentValues()
        contentValue.apply {
            put(TITLECOLUMN,title)
            put(PRIORITYCOLUMN,priority)
            put(DESCRIPTIONCOLUMN,description)
            put(DATECOLUMN,date)
        }
        db.insert(TABLENAME,null,contentValue)


    }

    fun updateTask(id:Int,title:String,description: String,priority: Int) {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.apply {
            put(TITLECOLUMN,title)
            put(DESCRIPTIONCOLUMN,description)
            put(PRIORITYCOLUMN,priority)
        }
        db.update(TABLENAME,contentValues,"id=?", arrayOf(id.toString()))
    }
    fun getDetailsAboutTask(id:Int): Task {
        val db = readableDatabase
        val columns = arrayOf(IDCOLUMN, TITLECOLUMN, DESCRIPTIONCOLUMN, PRIORITYCOLUMN, STATUSCOLUMN,
                DATECOLUMN)
        val cursor = db.query(TABLENAME,columns,"$IDCOLUMN = ?", arrayOf(id.toString()),null,null,null,null)

            val id = cursor.getInt(0)
            val title = cursor.getString(1)
            val description = cursor.getString(2)
            val priority = cursor.getInt(3)
            val status = cursor.getString(4)
            val date = cursor.getString(5)
            val t = Task(title, priority, status, date)
            t.id = id
            t.description = description
            return t


    }
    fun doneTask(id: Int) {
        val db = writableDatabase
        val contentValue = ContentValues()
        contentValue.put(STATUSCOLUMN, "done")
        db.update(TABLENAME, contentValue, "id=?", arrayOf(id.toString()))
    }

    fun deleteTask(id: Int) {
        val db = writableDatabase
        db.delete(TABLENAME, "$IDCOLUMN=?", arrayOf(id.toString()))
    }

}