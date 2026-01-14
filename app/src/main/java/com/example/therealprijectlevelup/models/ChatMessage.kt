package com.example.therealprijectlevelup.models

enum class Sender {
    USER,
    BOT
}

data class ChatMessage(
    val message: String,
    val sender: Sender
)
