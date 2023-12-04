package com.dicoding.todoapp.ui.detail

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.dicoding.todoapp.R
import com.dicoding.todoapp.data.TaskRepository
import com.dicoding.todoapp.ui.ViewModelFactory
import com.dicoding.todoapp.utils.DateConverter
import com.dicoding.todoapp.utils.TASK_ID

class DetailTaskActivity : AppCompatActivity() {
    private lateinit var detailTaskViewModel: DetailTaskViewModel
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var dueDate: TextView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        //TODO 11 : Show detail task and implement delete action
        val data = intent.getIntExtra(TASK_ID, 0)
        val pref = TaskRepository.getInstance(applicationContext)

        detailTaskViewModel =
            ViewModelProvider(this, ViewModelFactory(pref)).get(DetailTaskViewModel::class.java)
        detailTaskViewModel.setTaskId(data)

        detailTaskViewModel.task.observe(this) {
            title = findViewById(R.id.detail_ed_title)
            description = findViewById(R.id.detail_ed_description)
            dueDate = findViewById(R.id.detail_ed_due_date)

            if (it != null) {
                title.text = it.title
                description.text = it.title
                dueDate.text = DateConverter.convertMillisToString(it.dueDateMillis)
            }
        }
        btn = findViewById(R.id.btn_delete_task)
        btn.setOnClickListener {
            detailTaskViewModel.deleteTask()
            finish()
        }
    }
}