package com.example.myapplication.data.network

import com.example.myapplication.data.network.dto.MessageDto
import com.example.myapplication.data.network.dto.NewMessageDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatApiService {

    // TODO 4: get every message, newest first.
    // - annotate it with @GET and the path messages (NO slash in front)
    // - it must be a suspend function
    // - it returns List<MessageDto>
    // - add two @Query parameters so the server sorts for us:
    //     @Query("sortBy") sortBy: String = "createdAt"
    //     @Query("order") order: String = "desc"
    @GET("messages")
    suspend fun getMessages(
        @Query("sortBy") sortBy: String = "createdAt",
        @Query("order") order: String = "desc"
    ): List<MessageDto>

    // TODO 5: send one new message.
    // - annotate the function with @POST and the path messages
    // - it must be a suspend function
    // - it takes one parameter annotated with @Body of type NewMessageDto
    // - it returns MessageDto (the server echoes back what it saved, with an id)
    @POST("messages")
    suspend fun sendMessage(
        @Body message: NewMessageDto
    ): MessageDto
}
