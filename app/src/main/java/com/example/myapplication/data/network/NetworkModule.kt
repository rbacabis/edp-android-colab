package com.example.myapplication.data.network

import com.example.myapplication.data.network.dto.MessageDto
import com.example.myapplication.data.network.dto.NewMessageDto
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkModule {

    // TODO 6: paste the class API address your instructor wrote on the board.
    // It MUST end with a slash /
    private const val BASE_URL = "https://67bc8200ed4861e07b3a985e.mockapi.io/api/v1/"

    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    private val mockMessages = mutableListOf(
        MessageDto("14", "Ana Reyes", "Good morning everyone!", 1757001234567L),
        MessageDto("13", "Jun Dela Cruz", "My Retrofit finally works", 1757001200000L)
    )

    private val mockFallbackInterceptor = Interceptor { chain ->
        val request = chain.request()
        var response: Response? = null
        try {
            response = chain.proceed(request)
        } catch (_: Exception) {
            // network error or offline
        }

        if (response == null || !response.isSuccessful) {
            val url = request.url.toString()
            if (url.contains("messages")) {
                response?.close()
                if (request.method == "GET") {
                    val bodyString = json.encodeToString(
                        ListSerializer(MessageDto.serializer()),
                        mockMessages.sortedByDescending { it.createdAt ?: 0L }
                    )
                    return@Interceptor Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(200)
                        .message("OK")
                        .body(bodyString.toResponseBody("application/json".toMediaType()))
                        .build()
                } else if (request.method == "POST") {
                    val buffer = okio.Buffer()
                    request.body?.writeTo(buffer)
                    val reqJson = buffer.readUtf8()
                    val newMsg = json.decodeFromString(NewMessageDto.serializer(), reqJson)
                    val created = MessageDto(
                        id = System.currentTimeMillis().toString(),
                        sender = newMsg.sender,
                        text = newMsg.text,
                        createdAt = newMsg.createdAt
                    )
                    mockMessages.add(0, created)
                    val respJson = json.encodeToString(MessageDto.serializer(), created)
                    return@Interceptor Response.Builder()
                        .request(request)
                        .protocol(Protocol.HTTP_1_1)
                        .code(201)
                        .message("Created")
                        .body(respJson.toResponseBody("application/json".toMediaType()))
                        .build()
                }
            }
        }
        response ?: throw IOException("Network request failed")
    }

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(mockFallbackInterceptor)
        .addInterceptor(logging)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .build()

    val chatApi: ChatApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ChatApiService::class.java)
}
