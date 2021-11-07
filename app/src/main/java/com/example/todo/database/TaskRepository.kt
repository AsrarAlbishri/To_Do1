package com.example.todo.database

import android.content.Context
import androidx.room.Room


private const val DATABASE_NAME = "task-database"
class TaskRepository private constructor(context: Context){

    private val database:TaskDataBase = Room.databaseBuilder(
        context.applicationContext,
        TaskDataBase::class.java,
        DATABASE_NAME
    ).build()
}