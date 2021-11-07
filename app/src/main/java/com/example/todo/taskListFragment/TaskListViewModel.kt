package com.example.todo.taskListFragment

import androidx.lifecycle.ViewModel
import com.example.todo.database.Task
import com.example.todo.database.TaskRepository

class TaskListViewModel : ViewModel() {

    val taskRepository = TaskRepository.get()

    val liveDataTasks = taskRepository.getAllTasks()

    fun addTask(task: Task){
        taskRepository.addTask(task)
    }

}