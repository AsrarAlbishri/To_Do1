package com.example.todo.database

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*


@Dao
interface TaskDao {

    @Insert
   fun addTask( task:Task )

    @Query("SELECT * FROM Task")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE id = (:id) ")
    fun getTask(id: UUID):LiveData<Task?>

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

}