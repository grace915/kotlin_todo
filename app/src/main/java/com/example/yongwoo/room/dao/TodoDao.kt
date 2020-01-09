package com.example.yongwoo.room.dao

import androidx.room.*
import com.example.yongwoo.room.entitiy.TodoItem

@Dao
interface TodoDao {
    @Insert
    fun insertTodo(item: TodoItem)

    @Delete
    fun deleteTodo(item: TodoItem)

    @Update
    fun updateTodo(item: TodoItem)

    @Query("SELECT * FROM todo")
    fun getTodos(): List<TodoItem>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id: Int): TodoItem
}