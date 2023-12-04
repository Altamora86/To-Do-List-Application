package com.dicoding.todoapp.ui.add

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.TaskRepository
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.ui.list.TaskActivity
import com.dicoding.todoapp.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {
    private var dueDateMillis: Long = System.currentTimeMillis()
    private lateinit var addTaskViewModel: AddTaskViewModel
    private lateinit var addEdTitle: EditText
    private lateinit var addEdDescription: EditText
    private lateinit var addTvDueDate: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        supportActionBar?.title = getString(R.string.add_task)

        val pref = TaskRepository.getInstance(applicationContext)
        addTaskViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(AddTaskViewModel::class.java)

        addEdTitle = findViewById(R.id.add_ed_title)
        addEdDescription = findViewById(R.id.add_ed_description)
        addTvDueDate = findViewById(R.id.add_tv_due_date)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_save -> {
                //TODO 12 : Create AddTaskViewModel and insert new task to database
                val title = addEdTitle.text
                val desc = addEdDescription.text
                val time = addTvDueDate

                when {
                    title!!.isEmpty() -> {
                        Toast.makeText(this, "please fill in your title", Toast.LENGTH_SHORT).show()
                    }
                    desc!!.isEmpty() -> {
                        Toast.makeText(this, "please fill in your description", Toast.LENGTH_SHORT).show()
                    }
                    false -> {
                        Toast.makeText(this, "please fill in your due date", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        addTask()
                        startActivity(Intent(this, TaskActivity::class.java))
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun addTask() {
        val title = addEdTitle.text.toString()
        val desc = addEdDescription.text.toString()
        val dueDate = addTvDueDate.text.toString()

        val format = SimpleDateFormat("dd/MM/yyyy")
        val d = format.parse(dueDate)
        val milliseconds = d!!.time

        addTaskViewModel.insertTask(0, title, desc, milliseconds)
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.add_tv_due_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }
}