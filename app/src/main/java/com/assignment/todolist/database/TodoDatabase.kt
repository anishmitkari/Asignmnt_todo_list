package com.assignment.todolist.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo_model::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun AddtodoDao(): To_do_Dao
}