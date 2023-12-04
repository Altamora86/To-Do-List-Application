package com.dicoding.todoapp.ui.add

import androidx.lifecycle.ViewModel
import com.dicoding.todoapp.data.Task
import com.dicoding.todoapp.data.TaskRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskViewModel(private val taskRepository: TaskRepository): ViewModel() {
    fun insertTask(id: Int, title: String, desc: String, dueDate: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = Task(
                id,
                title,
                desc,
                dueDate
            )
            taskRepository.insertTask(user)
        }
    }
}