package com.assignment.todolist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "to_do_list")

data class Todo_model(
    @PrimaryKey(autoGenerate = true)
    var id: Int,

    var title: String,

    @ColumnInfo(name = "time")
    var time: String,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "status")
    var status: String


)
