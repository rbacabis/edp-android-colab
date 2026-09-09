package com.liceo.mysocial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceo.mysocial.data.ThemeRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ThemeViewModel(private val repo: ThemeRepository) : ViewModel() {
    // GIVEN: the saved theme, ready for the screen to watch
    val isDarkTheme: StateFlow<Boolean> = repo.isDarkTheme
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), false)

    // TODO 10: save the user's choice
    fun onThemeChanged(dark: Boolean) {
        viewModelScope.launch { repo.setDarkTheme(dark) }
    }
}
