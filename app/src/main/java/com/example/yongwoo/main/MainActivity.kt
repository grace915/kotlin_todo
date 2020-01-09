package com.example.yongwoo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yongwoo.R
import com.example.yongwoo.room.entitiy.TodoItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private var adapter: MainTodoAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainTodoAdapter(this)
        main_rcv_item.adapter = adapter
        main_rcv_item.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        main_fab_add.setOnClickListener {
            val newTodo = TodoItem(0, System.currentTimeMillis().toString(), "asd", "qwe")
            adapter?.addItem(newTodo)
        }
    }
}
