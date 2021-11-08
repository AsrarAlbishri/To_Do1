package com.example.todo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Task::class] , version = 2)
@TypeConverters(TaskTypeConverters::class)
abstract class TaskDataBase : RoomDatabase() {

    abstract fun taskDao():TaskDao


}
