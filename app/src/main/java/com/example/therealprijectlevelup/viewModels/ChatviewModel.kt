package com.example.therealprijectlevelup.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.ChatMessage
import com.example.therealprijectlevelup.models.Sender
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow(
        listOf(ChatMessage("¿En qué te puedo ayudar?", Sender.BOT))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(text: String) {
        if (text.isBlank()) return

        viewModelScope.launch {
            // Mensaje del usuario
            _messages.value += ChatMessage(text, Sender.USER)

            // Simula llamada a servidor / IA
            delay(1000)

            // Respuesta del bot
            _messages.value += ChatMessage(
                "si. te sacas un 7",
                Sender.BOT
            )
        }
    }
}

