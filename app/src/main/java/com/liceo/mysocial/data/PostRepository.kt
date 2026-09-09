package com.liceo.mysocial.data

import kotlinx.coroutines.flow.Flow

class PostRepository(private val dao: PostDao) {
    fun observePosts(): Flow<List<Post>> = dao.getAllPosts()

    suspend fun addPost(content: String) {
        dao.insert(Post(content = content))
    }

    suspend fun editPost(post: Post, newContent: String) {
        dao.update(post.copy(content = newContent))
    }

    suspend fun deletePost(post: Post) {
        dao.delete(post)
    }
}
