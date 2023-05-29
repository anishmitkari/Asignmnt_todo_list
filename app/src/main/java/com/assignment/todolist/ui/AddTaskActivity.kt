package com.assignment.todolist.ui

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.assignment.todolist.R
import com.assignment.todolist.database.To_do_Dao
import com.assignment.todolist.database.TodoDatabase
import com.assignment.todolist.database.Todo_model
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class AddTaskActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var bookDao: To_do_Dao
    private lateinit var value: String
    private lateinit var selectedTime: String
    lateinit var Date: String
    var arr_of_mode = arrayOf("AM", "PM")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
        val db = Room.databaseBuilder(
            applicationContext, TodoDatabase::class.java, "todo_database"
        ).build()

        bookDao = db.AddtodoDao()

        val timesppinerMode = findViewById<Spinner>(R.id.sppinerTimeMode)
        val txvAddTask = findViewById<TextView>(R.id.txvAddTask)
        val txvCancle = findViewById<TextView>(R.id.txvCancle)
        val edtEnterTitle = findViewById<TextInputEditText>(R.id.edtEnterTitle)
        val edtEnterTitleTime = findViewById<TextInputEditText>(R.id.edtEnterTitleTime)

        timesppinerMode.setOnItemSelectedListener(this)
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, arr_of_mode)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timesppinerMode!!.setAdapter(aa)



        edtEnterTitleTime.setOnClickListener {
            val mcurrentTime = Calendar.getInstance()
            val hour = mcurrentTime[Calendar.HOUR_OF_DAY]
            val minute = mcurrentTime[Calendar.MINUTE]
            val mTimePicker: TimePickerDialog
            mTimePicker = TimePickerDialog(
                this@AddTaskActivity,
                { timePicker, selectedHour, selectedMinute -> edtEnterTitleTime.setText("$selectedHour:$selectedMinute") },
                hour,
                minute,
                true
            ) //Yes 24 hour time

            mTimePicker.setTitle("Select Time")
            mTimePicker.show()
        }




        txvCancle.setOnClickListener {
            val addTaskIntent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(addTaskIntent)
            finish()
        }
        txvAddTask.setOnClickListener {

            if (edtEnterTitle.text.toString().isEmpty()) {
                edtEnterTitle.setError("Please enter title")
            } else if (edtEnterTitleTime.text.toString().isEmpty()) {
                edtEnterTitleTime.setError("Please enter time")

            } else if (selectedTime.isEmpty()) {
                val toast =
                    Toast.makeText(applicationContext, "Select time AM/PM", Toast.LENGTH_SHORT)
                toast.show()
            } else {
                add_todo_data(edtEnterTitle.text.toString(), edtEnterTitleTime.text.toString())
            }


        }


        get_Currentdate();

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedTime = arr_of_mode[position]

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun add_todo_data(title: String, time: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            bookDao.insertdata(Todo_model(0,title,time+" "+selectedTime,Date,"New"))
            val addTaskIntent = Intent(this@AddTaskActivity, MainActivity::class.java)
            startActivity(addTaskIntent)
            finish()
        }

    }

    fun get_Currentdate() {
        val c = Calendar.getInstance().time
        val mdformat = SimpleDateFormat("hh:mm aaa")
        val strDate = "Current Time : " + mdformat.format(c.time)
        println("Current time =>    $strDate")

        val df = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        Date = df.format(c)
    }

}