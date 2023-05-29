package com.assignment.todolist.database

import androidx.room.*

@Dao
interface To_do_Dao {

    @Insert
    suspend fun insertdata(todo: Todo_model)

    @Query("SELECT * FROM to_do_list")
    fun getAlldata(): List<Todo_model>

    @Update
    suspend fun updatedata(todo: Todo_model)

    @Delete
    suspend fun deletedata(todo: Todo_model)



}