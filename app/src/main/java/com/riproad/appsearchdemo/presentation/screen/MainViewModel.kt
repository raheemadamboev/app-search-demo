package com.riproad.appsearchdemo.presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riproad.appsearchdemo.data.local.todo.constant.TodoDatabaseConst
import com.riproad.appsearchdemo.data.model.TodoModel
import com.riproad.appsearchdemo.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: MainRepository
) : ViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    var todos: ImmutableList<TodoModel> by mutableStateOf(persistentListOf())
        private set

    init {
        observe()
    }

    private fun observe() {
        observeQuery()
    }

    private fun observeQuery() {
        viewModelScope.launch {
            query.debounce(0.5.seconds).collectLatest { query ->
                getTodos(query)
            }
        }
    }

    private suspend fun getTodos(query: String) {
        todos = repository.getTodos(query).toImmutableList()
    }

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun onQueryChange(value: String) {
        viewModelScope.launch {
            _query.emit(value)
        }
    }

    fun onInsertRandomTodo() {
        viewModelScope.launch {
            repository.insertTodo(
                TodoModel(
                    id = UUID.randomUUID().toString(),
                    namespace = TodoDatabaseConst.NAMESPACE,
                    score = 1,
                    title = "Todo ${Random.nextInt()}",
                    text = "Description  ${Random.nextInt()}",
                    done = Random.nextBoolean()
                )
            )
            getTodos(query.value)
        }
    }
}