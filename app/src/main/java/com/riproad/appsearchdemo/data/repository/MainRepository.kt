package com.riproad.appsearchdemo.data.repository

import com.riproad.appsearchdemo.data.local.todo.database.TodoDatabase
import com.riproad.appsearchdemo.data.mapper.toEntity
import com.riproad.appsearchdemo.data.mapper.toModel
import com.riproad.appsearchdemo.data.model.TodoModel

class MainRepository(
    private val database: TodoDatabase
) {

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTodo(todo: TodoModel) {
        database.insertTodo(todo.toEntity())
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    suspend fun getTodos(query: String): List<TodoModel> {
        return database.getTodos(query).map { entity ->
            entity.toModel()
        }
    }
}