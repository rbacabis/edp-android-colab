package com.example.myapplication.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    // TODO 1a: add id. It arrives as a string in quotes.
    // Make it nullable with a default of null.
    val id: String? = null,

    // TODO 1b: add sender. Same idea - a nullable String with a default of null.
    val sender: String? = null,

    // TODO 1c: add text. A nullable String with a default of null.
    val text: String? = null,

    // TODO 1d: add createdAt. It is a big number of milliseconds,
    // so use Long, nullable, with a default of null.
    val createdAt: Long? = null
)

@Serializable
data class NewMessageDto(
    val sender: String,
    val text: String,
    val createdAt: Long
)
