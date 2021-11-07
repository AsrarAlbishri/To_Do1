package com.example.todo.database

import androidx.room.Dao
import androidx.room.Insert


@Dao
interface TaskDao {

    @Insert
   fun addTask( task:Task )

}