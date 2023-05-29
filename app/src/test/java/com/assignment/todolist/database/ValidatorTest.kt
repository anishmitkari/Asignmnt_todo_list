package com.assignment.todolist.database

import org.junit.Assert.*

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest{

    @Test
    fun whenInputIsValid(){
        val title = "do task"
        val time = "11:00 PM"
        val result = Validator.validateInput(title, time)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun whenInputIsInvalid(){
        val title =""
        val time = ""
        val result = Validator.validateInput(title, time)
        assertThat(result).isEqualTo(false)
    }

}