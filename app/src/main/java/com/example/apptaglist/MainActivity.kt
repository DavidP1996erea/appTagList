package com.example.apptaglist

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.ViewUtils.hideKeyboard
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {


    lateinit var recyclerView: RecyclerView
    lateinit var tasks: MutableList<TasksEntity>
    lateinit var adapter: TasksAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var  btnAddTask = findViewById<Button>(R.id.btnAddTask)
        var  etTask = findViewById<TextView>(R.id.etTask)

        btnAddTask.setOnClickListener {
            addTask(TasksEntity(name = etTask.text.toString()))}
        tasks = ArrayList()
        getTasks()
    }

    fun clearFocus(){
        var  etTask = findViewById<TextView>(R.id.etTask)
        etTask.setText("")
    }
    fun Context.hideKeyboard() {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }


    fun getTasks() = runBlocking {
        launch {
            tasks = MisNotasApp.database.taskDao().getAllTasks()
            setUpRecyclerView(tasks)
        }
    }


    fun setUpRecyclerView(tasks: List<TasksEntity>) {
        adapter = TasksAdapter(tasks, { updateTask(it) }, { deleteTask(it) })
        recyclerView = findViewById(R.id.rvTask)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun updateTask(task: TasksEntity) = runBlocking {
        launch {
            task.isDone = !task.isDone
            MisNotasApp.database.taskDao().updateTask(task)
        }
    }

    fun deleteTask(task: TasksEntity) = runBlocking {
        launch {
            val position =
                tasks.indexOf(task)
                MisNotasApp.database.taskDao().deleteTask(task)
               tasks.remove(task)
               adapter.notifyItemRemoved(position)
        }
    }

    fun addTask(task:TasksEntity)= runBlocking{
        launch {
            val id = MisNotasApp.database.taskDao().addTask(task)
            val recoveryTask = MisNotasApp.database.taskDao().getTaskById(id)
            tasks.add(recoveryTask)
            adapter.notifyItemInserted(tasks.size)
            clearFocus()
            hideKeyboard()
        }
    }
}