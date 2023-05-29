package com.assignment.todolist.adapter

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.assignment.todolist.R
import com.assignment.todolist.database.Todo_model
import java.text.SimpleDateFormat
import java.util.*


class ShowToDoNotesAdapter(private val mList: List<Todo_model>, context: Context) :
    RecyclerView.Adapter<ShowToDoNotesAdapter.ViewHolder>() {
    private var customClickListener: OnItemClickListener? = null
    var context: Context
    init {
         this.context = context
    }

    interface OnItemClickListener {
        fun onItemClicked(Id: Int, title: String, time: String, date: String, status: String)
    }

    fun setOnItemClickListener(mItemClick: OnItemClickListener) {
        this.customClickListener = mItemClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_layout, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]
        holder.txvTitle.text = ItemsViewModel.title
        holder.txvTime.text = ItemsViewModel.time



        holder.img_delete.setOnClickListener {
            customClickListener?.onItemClicked(
                ItemsViewModel.id,
                ItemsViewModel.title,
                ItemsViewModel.time,
                ItemsViewModel.date,
                "delete"
            )

        }
        if (ItemsViewModel.status.equals("New")){
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("HH:mm aa")
            val getCurrentDateTime: String = sdf.format(c.time)


            val int = getCurrentDateTime.compareTo(ItemsViewModel.time)
            if (int == 0) {
             } else if (int > 0) {

                holder.txvTitle.setTextColor(ContextCompat.getColor(context, R.color.redColor))
                holder.txvg_status.setVisibility(View.VISIBLE)

            } else if (int < 0) {
                holder.txvg_status.setVisibility(View.GONE)


            } else {
                holder.txvg_status.setVisibility(View.GONE)

            }
         }
      else  if (ItemsViewModel.status.equals("complete")) {

            holder.txvTitle.setPaintFlags(holder.txvTitle.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            holder.chk_mark.setChecked(true)
            holder.txvg_status.setVisibility(View.GONE)
            holder.txvTitle.setTextColor(ContextCompat.getColor(context, R.color.txt_color))

            holder.chk_mark.setClickable(false)
        } else {

        }
        holder.chk_mark.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked) {
                customClickListener?.onItemClicked(
                    ItemsViewModel.id,
                    ItemsViewModel.title,
                    ItemsViewModel.time,
                    ItemsViewModel.date,
                    "complete"
                )

            }
        }




    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val txvTitle: TextView = itemView.findViewById(R.id.txvTitle)
        val txvg_status: TextView = itemView.findViewById(R.id.txvg_status)
        val txvTime: TextView = itemView.findViewById(R.id.txvTime)
        val img_delete: ImageView = itemView.findViewById(R.id.img_delete)
        val chk_mark: CheckBox = itemView.findViewById(R.id.chk_mark)
    }
}
