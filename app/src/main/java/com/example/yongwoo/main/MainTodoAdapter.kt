package com.example.yongwoo.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.yongwoo.R
import com.example.yongwoo.add_edit.AddEditTodoActivity
import com.example.yongwoo.room.database.MyDatabase
import com.example.yongwoo.room.entitiy.TodoItem
import kotlinx.android.synthetic.main.item_todo.view.*
import java.util.*

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
    fun deleteItem(item : TodoItem){
        itemList.remove(item)
        myDatabase?.todoDao()?.deleteTodo(item)
        notifyDataSetChanged()
    }

    fun refresh(){
        myDatabase?.todoDao()?.getTodos()?.also {
            itemList.clear()
            itemList.addAll(it)
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainTodoViewHolder {
        var viewHolder = MainTodoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false))

        viewHolder.itemView.setOnClickListener {
            itemList[viewHolder.adapterPosition].checked = !itemList[viewHolder.adapterPosition].checked
            myDatabase?.todoDao()?.updateTodo(itemList[viewHolder.adapterPosition])
            notifyDataSetChanged()
        }
        viewHolder.itemView.setOnLongClickListener  {
            val builder = AlertDialog.Builder(parent.context)
            val menu :Array<String> = arrayOf("삭제","수정","취소")
            builder.setTitle(itemList[viewHolder.adapterPosition].name)
            builder.setItems(menu) { dialog, which ->
                when(menu[which]){
                    "삭제"->deleteItem(itemList[viewHolder.adapterPosition])
                    "수정"->{
                        val editIntent = Intent(context,AddEditTodoActivity::class.java)
                        editIntent.putExtra("mode",AddEditTodoActivity.MODE_EDIT)
                        editIntent.putExtra("item_id",itemList[viewHolder.adapterPosition].id)
                        context.startActivity(editIntent)



                    }
                    "취소"->{}
                    else->{
                        Log.d("SetOnLongClickListener","item position error!")}

                } }
            builder.show()
            false
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