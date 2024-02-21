package com.riproad.appsearchdemo.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.riproad.appsearchdemo.R

@Composable
fun MainScreen(
    viewmodel: MainViewModel = hiltViewModel()
) {
    val query by viewmodel.query.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = viewmodel::onInsertRandomTodo) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            OutlinedTextField(
                value = query,
                onValueChange = viewmodel::onQueryChange,
                label = {
                    Text(text = stringResource(id = R.string.search))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth()
            ) {
                items(
                    items = viewmodel.todos,
                    key = { it.id }
                ) { todo ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = todo.title
                            )
                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )
                            Text(
                                text = todo.text
                            )
                        }
                    }
                }
            }
        }
    }
}