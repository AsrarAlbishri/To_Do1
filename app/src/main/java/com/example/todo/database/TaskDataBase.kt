package com.example.todo.database

import androidx.room.Database


@Database(entities = [Task::class] , version = 1)
abstract class TaskDataBase {


}