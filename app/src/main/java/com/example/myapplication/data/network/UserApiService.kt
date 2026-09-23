package com.example.myapplication.data.network

import com.example.myapplication.data.network.dto.NewUserDto
import com.example.myapplication.data.network.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApiService {
    @GET("users")
    suspend fun findByEmail(@Query("email") email: String): List<UserDto>

    @POST("users")
    suspend fun createUser(@Body user: NewUserDto): UserDto
}
