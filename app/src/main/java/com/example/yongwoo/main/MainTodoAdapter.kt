package com.example.yongwoo.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yongwoo.R
import com.example.yongwoo.room.database.MyDatabase
import com.example.yongwoo.room.entitiy.TodoItem
import kotlinx.android.synthetic.main.item_todo.view.*

class MainTodoAdapter(private val context: Context) : RecyclerView.Adapter<MainTodoViewHolder>() {
    private val myDatabase: MyDatabase? = MyDatabase.getInstance(context)
    var itemList: MutableList<TodoItem> = mutableListOf()

    init {
        val items = myDatabase?.todoDao()?.getTodos()?.also {
            itemList.addAll(it)
        }
        notifyDataSetChanged()
    }

    fun addItem(item: TodoItem) {
        itemList.add(item)
        myDatabase?.todoDao()?.insertTodo(item)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTodoViewHolder {
        var viewHolder = MainTodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false))

        viewHolder.itemView.setOnClickListener {
            itemList[viewHolder.adapterPosition].checked = !itemList[viewHolder.adapterPosition].checked
            myDatabase?.todoDao()?.updateTodo(itemList[viewHolder.adapterPosition])
            notifyDataSetChanged()
        }

        return viewHolder
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: MainTodoViewHolder, position: Int) {
        holder.onbind(itemList[position])
    }

}