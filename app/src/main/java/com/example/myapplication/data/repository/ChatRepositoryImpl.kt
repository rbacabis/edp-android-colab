package com.example.myapplication.data.repository

import com.example.myapplication.core.AppResult
import com.example.myapplication.data.network.ChatApiService
import com.example.myapplication.data.network.dto.NewMessageDto
import com.example.myapplication.data.network.dto.toDomain
import com.example.myapplication.domain.ChatRepository
import com.example.myapplication.domain.Message
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChatRepositoryImpl(
    private val api: ChatApiService
) : ChatRepository {

    // TODO 7: get the messages.
    // - call api.getMessages()
    // - turn the list of MessageDto into a list of Message with .toDomain()
    // - wrap the whole thing in safeCall { ... }
    override suspend fun getMessages(): AppResult<List<Message>> = safeCall {
        api.getMessages().toDomain()
    }

    // TODO 8: send a message.
    // - build a NewMessageDto with sender, text and System.currentTimeMillis()
    // - call api.sendMessage(...) with it
    // - the result of the block should be Unit
    // - wrap it in safeCall { ... }
    override suspend fun sendMessage(sender: String, text: String): AppResult<Unit> = safeCall {
        val dto = NewMessageDto(sender, text, System.currentTimeMillis())
        api.sendMessage(dto)
        Unit
    }

    private inline fun <T> safeCall(block: () -> T): AppResult<T> =
        try {
            AppResult.Success(block())
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        } catch (e: Exception) {
            AppResult.Failure.Unknown(e.message)
        }
}
