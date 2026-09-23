package com.example.myapplication.data.network.dto

import com.example.myapplication.domain.Message

fun MessageDto.toDomain(): Message = Message(
    id = id ?: "",
    // TODO 3a: sender - use the sender, but if it is null use "Unknown" instead
    sender = sender ?: "Unknown",
    // TODO 3b: text - use the text, but if it is null use an empty string ""
    text = text ?: "",
    // TODO 3c: createdAt - use createdAt, but if it is null use 0L
    createdAt = createdAt ?: 0L
)

fun List<MessageDto>.toDomain(): List<Message> =
    map { it.toDomain() }
