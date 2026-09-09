package com.liceo.mysocial.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.liceo.mysocial.data.Post
import com.liceo.mysocial.data.PostRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostsViewModel(private val repo: PostRepository) : ViewModel() {
    // GIVEN (read it, do not change it): the list the screen watches
    val posts: StateFlow<List<Post>> = repo.observePosts()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    // TODO 9a: write a new post
    fun addPost(content: String) {
        viewModelScope.launch { repo.addPost(content) }
    }

    // TODO 9b: save changes to an existing post
    fun editPost(post: Post, newContent: String) {
        viewModelScope.launch { repo.editPost(post, newContent) }
    }

    // TODO 9c: delete a post
    fun deletePost(post: Post) {
        viewModelScope.launch { repo.deletePost(post) }
    }
}
