package com.assignment.todolist.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.assignment.todolist.R
import com.assignment.todolist.adapter.ShowToDoNotesAdapter
import com.assignment.todolist.database.To_do_Dao
import com.assignment.todolist.database.TodoDatabase
import com.assignment.todolist.database.Todo_model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    private lateinit var todoDao: To_do_Dao
    private lateinit var adapter: ShowToDoNotesAdapter
    private lateinit var toDoRecyclerView: RecyclerView
    private lateinit var ivFilterBtn: ImageView
    private var myArrayList = ArrayList<Todo_model>()
    private lateinit var ivAddToDo: ImageView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toDoRecyclerView = findViewById(R.id.toDoRecyclerView)
        ivFilterBtn = findViewById(R.id.ivFilterBtn)

        ivAddToDo = findViewById(R.id.ivAddToDo)
        ivAddToDo.setOnClickListener {
            val addTaskIntent = Intent(this@MainActivity, AddTaskActivity::class.java)
            startActivity(addTaskIntent)
            finish()
        }
        val db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo_database"
        ).build()

        todoDao = db.AddtodoDao()
        showToDoNotes()
        adapter = ShowToDoNotesAdapter(myArrayList, application)
        toDoRecyclerView.adapter = adapter
        toDoRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

        adapter.setOnItemClickListener(object : ShowToDoNotesAdapter.OnItemClickListener {
            override fun onItemClicked(
                id: Int, title: String, time: String, date: String, status: String
            ) {
                if (status.equals("delete"))
                    dialog_Delete_todo(id, title, time, date)
                else
                    complete_Task(id, title, time, date)
            }
        })

        ivFilterBtn.setOnClickListener {

            dialog_sortby()
        }
    }

//funtion to show toda list

    private fun showToDoNotes() {
        lifecycleScope.launch(Dispatchers.IO) {
            val books = todoDao.getAlldata()
            myArrayList.addAll(books)
            Log.e("TAG", "showToDoNotes:>>>>>   " + myArrayList)
        }
    }

    //    dialog for delete warning
    fun dialog_Delete_todo(Id: Int, Title: String, time: String, date: String) {

        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.dialog_delete)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnCancel: TextView = dialog.findViewById(R.id.btnCancel)
        val btnOk: TextView = dialog.findViewById(R.id.btnOk)
        val txttitle: TextView = dialog.findViewById(R.id.txttitle)

        txttitle.text = "Do you want to delete $Title " + "this action can't be undone"

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnOk.setOnClickListener {
            delete_Task(Id, Title, time, date, "delete")
            dialog.dismiss();
        }
        dialog.setCancelable(false)
        dialog.show(); }

    //function to delete task

    fun delete_Task(Id: Int, title: String, time: String, date: String, status: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            todoDao.deletedata(
                Todo_model(
                    Id, title, time, date, status
                )
            )
        }

    }
//function to mark complete task

    fun complete_Task(Id: Int, Title: String, time: String, date: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            todoDao.updatedata(
                Todo_model(Id, Title, time, date, "complete")
            )
        }
        val addTaskIntent = Intent(this@MainActivity, MainActivity::class.java)
        startActivity(addTaskIntent)
        finish()
    }

//Filter dialog

    fun dialog_sortby() {

        val dialog = Dialog(this@MainActivity)
        dialog.setContentView(R.layout.filter_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnCancel: TextView = dialog.findViewById(R.id.btnCancel)
        val txttitle: TextView = dialog.findViewById(R.id.txttitle)

        txttitle.setOnClickListener {

            /* val c = Calendar.getInstance()
             val sdf = SimpleDateFormat("dd-MM-yyyy")
              var decendingOrder = true
             for (index in 0 until myArrayList.size() - 1) {
                 if (sdf.parse(myArrayList.get(index)).time < sdf.parse(
                         myArrayList.get(
                             index + 1
                         )
                     ).time
                 ) {
                     decendingOrder = false
                     break
                 }
             }
             if(decendingOrder) {
                 System.out.println("Date are in Decending Order");
             }else {
                 System.out.println("Date not in Decending Order");
             }*/
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialog.setCancelable(false)
        dialog.show(); }

}