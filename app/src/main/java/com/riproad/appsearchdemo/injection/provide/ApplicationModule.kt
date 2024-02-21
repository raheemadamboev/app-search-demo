package com.riproad.appsearchdemo.injection.provide

import com.riproad.appsearchdemo.data.local.todo.database.TodoDatabase
import com.riproad.appsearchdemo.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(): TodoDatabase = TodoDatabase()

    @Provides
    @Singleton
    fun provideMainRepository(todoDatabase: TodoDatabase): MainRepository = MainRepository(todoDatabase)
}