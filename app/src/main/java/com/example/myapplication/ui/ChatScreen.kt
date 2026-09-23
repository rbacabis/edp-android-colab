package com.example.myapplication.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.domain.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(factory = ChatViewModel.Factory)
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("LiceoChat") },
                actions = {
                    IconButton(onClick = { viewModel.load() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            Box(Modifier.weight(1f).fillMaxWidth()) {
                // TODO 12: draw the four states here.
                when (val state = viewModel.uiState) {
                    ChatUiState.Loading -> {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    }
                    ChatUiState.Empty -> {
                        Text(
                            text = "No messages yet. Say hello!",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    is ChatUiState.Ready -> {
                        LazyColumn(Modifier.fillMaxSize()) {
                            items(state.messages, key = { it.id }) { message ->
                                MessageRow(message)
                            }
                        }
                    }
                    is ChatUiState.Error -> {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(state.message)
                            Spacer(Modifier.height(8.dp))
                            Button(onClick = { viewModel.load() }) {
                                Text("Retry")
                            }
                        }
                    }
                }
            }

            MessageInput(
                name = viewModel.myName,
                draft = viewModel.draft,
                onNameChange = viewModel::onNameChange,
                onDraftChange = viewModel::onDraftChange,
                onSend = viewModel::send
            )
        }
    }
}

@Composable
fun MessageRow(message: Message) {
    Column(Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)) {
        Text(message.sender, style = MaterialTheme.typography.labelLarge)
        Text(message.text, style = MaterialTheme.typography.bodyMedium)
    }
    HorizontalDivider()
}

@Composable
fun MessageInput(
    name: String,
    draft: String,
    onNameChange: (String) -> Unit,
    onDraftChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Column(Modifier.fillMaxWidth().padding(12.dp)) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text("Your full name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = draft,
                onValueChange = onDraftChange,
                label = { Text("Message") },
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.width(8.dp))
            Button(onClick = onSend) {
                Text("Send")
            }
        }
    }
}
