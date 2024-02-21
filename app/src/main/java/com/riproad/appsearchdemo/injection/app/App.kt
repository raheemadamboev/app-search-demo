package com.riproad.appsearchdemo.injection.app

import android.app.Application
import com.riproad.appsearchdemo.data.local.todo.database.TodoDatabase
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var database: TodoDatabase

    override fun onCreate() {
        super.onCreate()

        database.initialize(this)
    }
}