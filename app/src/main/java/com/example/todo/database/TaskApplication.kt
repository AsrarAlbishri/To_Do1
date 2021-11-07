package com.example.todo.database

import android.app.Application

class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        TaskRepository.initialize(this)
    }

}