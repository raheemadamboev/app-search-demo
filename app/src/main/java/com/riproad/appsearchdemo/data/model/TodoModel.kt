package com.riproad.appsearchdemo.data.model

data class TodoModel(
    val id: String,
    val namespace: String,
    val score: Int,
    val title: String,
    val text: String,
    val done: Boolean
)
