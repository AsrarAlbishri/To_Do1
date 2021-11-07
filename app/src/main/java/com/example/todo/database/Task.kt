package com.example.todo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(
    @PrimaryKey val id: UUID = UUID.randomUUID(),
    var title : String = "",
    var detail : String = "",
    var date : Date = Date(),
    var isCompleted : Boolean = false)
