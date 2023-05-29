package com.assignment.todolist.database

object Validator {
    fun validateInput(title: String, time: String) =
        !(title.isEmpty()|| time.isEmpty())



}