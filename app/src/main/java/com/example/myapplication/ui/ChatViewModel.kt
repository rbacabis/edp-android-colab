package com.example.myapplication.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.myapplication.core.AppResult
import com.example.myapplication.data.network.NetworkModule
import com.example.myapplication.data.repository.ChatRepositoryImpl
import com.example.myapplication.domain.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading)
        private set

    var myName: String by mutableStateOf("")
        private set

    var draft: String by mutableStateOf("")
        private set

    fun onNameChange(value: String) { myName = value }
    fun onDraftChange(value: String) { draft = value }

    init { load() }

    fun load() {
        viewModelScope.launch {
            uiState = ChatUiState.Loading

            // TODO 9: call repository.getMessages() and put the answer in a `when`.
            // - on Success: if the list is empty use ChatUiState.Empty,
            //               otherwise use ChatUiState.Ready(the List)
            // - on NoInternet: ChatUiState.Error("No internet connection.")
            // - on Timeout: ChatUiState.Error("The server took too long.")
            // - on anything else: ChatUiState.Error("Something went wrong.")
            uiState = when (val r = repository.getMessages()) {
                is AppResult.Success -> if (r.data.isEmpty()) ChatUiState.Empty else ChatUiState.Ready(r.data)
                AppResult.Failure.NoInternet -> ChatUiState.Error("No internet connection.")
                AppResult.Failure.Timeout -> ChatUiState.Error("The server took too long.")
                is AppResult.Failure -> ChatUiState.Error("Something went wrong.")
            }
        }
    }

    fun send() {
        // TODO 10: do nothing if myName or draft is blank. Just return.
        if (myName.isBlank() || draft.isBlank()) return

        viewModelScope.launch {
            // TODO 11: call repository.sendMessage(myName, draft).
            // If it is a Success, clear the draft (draft = "") and call load().
            // If it is a Failure, set uiState to ChatUiState.Error("Could not send. Check your connection.")
            when (repository.sendMessage(myName, draft)) {
                is AppResult.Success -> {
                    draft = ""
                    load()
                }
                is AppResult.Failure -> {
                    uiState = ChatUiState.Error("Could not send. Check your connection.")
                }
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ChatViewModel(
                    ChatRepositoryImpl(NetworkModule.chatApi)
                )
            }
        }
    }
}
