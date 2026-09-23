package com.example.myapplication.domain

data class Message(
    // TODO 2a: add id as a String (NOT nullable)
    val id: String,

    // TODO 2b: add sender as a String (NOT nullable)
    val sender: String,

    // TODO 2c: add text as a String (NOT nullable)
    val text: String,

    // TODO 2d: add createdAt as a Long (NOT nullable)
    val createdAt: Long
)
