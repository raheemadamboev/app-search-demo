package com.riproad.appsearchdemo.data.local.todo.database

import android.content.Context
import androidx.appsearch.app.AppSearchSession
import androidx.appsearch.app.PutDocumentsRequest
import androidx.appsearch.app.SearchSpec
import androidx.appsearch.app.SetSchemaRequest
import androidx.appsearch.localstorage.LocalStorage
import com.riproad.appsearchdemo.data.local.todo.constant.TodoDatabaseConst
import com.riproad.appsearchdemo.data.local.todo.entity.TodoEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoDatabase {

    private var session: AppSearchSession? = null

    ///////////////////////////////////////////////////////////////////////////
    // API
    ///////////////////////////////////////////////////////////////////////////

    fun initialize(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            session = LocalStorage.createSearchSessionAsync(
                LocalStorage.SearchContext.Builder(context, TodoDatabaseConst.NAME).build()
            ).get()
            session?.setSchemaAsync(
                SetSchemaRequest.Builder()
                    .addDocumentClasses(TodoEntity::class.java)
                    .build()
            )
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // INSERT
    ///////////////////////////////////////////////////////////////////////////

    suspend fun insertTodo(todo: TodoEntity) {
        withContext(Dispatchers.IO) {
            session?.putAsync(
                PutDocumentsRequest.Builder()
                    .addDocuments(todo)
                    .build()
            )?.get()
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // GET
    ///////////////////////////////////////////////////////////////////////////

    suspend fun getTodos(query: String): List<TodoEntity> {
        return withContext(Dispatchers.IO) {
            val spec = SearchSpec.Builder()
                .setSnippetCount(TodoDatabaseConst.PAGE_COUNT)
                .addFilterNamespaces(TodoDatabaseConst.NAMESPACE)
                .setRankingStrategy(SearchSpec.RANKING_STRATEGY_USAGE_COUNT)
                .build()
            val result = session?.search(query, spec) ?: return@withContext emptyList()
            return@withContext result.nextPageAsync.get().mapNotNull { search ->
                if (search.genericDocument.schemaType == TodoEntity::class.java.simpleName) search.getDocument(TodoEntity::class.java) else null
            }
        }
    }
}