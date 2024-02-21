package com.riproad.appsearchdemo.data.mapper

import com.riproad.appsearchdemo.data.local.todo.entity.TodoEntity
import com.riproad.appsearchdemo.data.model.TodoModel

fun TodoEntity.toModel(): TodoModel {
    return TodoModel(
        id = id,
        namespace = namespace,
        score = score,
        title = title,
        text = text,
        done = done
    )
}

fun TodoModel.toEntity(): TodoEntity {
    return TodoEntity(
        namespace = namespace,
        id = id,
        score = score,
        title = title,
        text = text,
        done = done
    )
}