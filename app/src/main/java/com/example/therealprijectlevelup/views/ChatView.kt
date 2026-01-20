package com.example.therealprijectlevelup.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.therealprijectlevelup.models.Sender
import com.example.therealprijectlevelup.viewModels.ChatViewModel
import com.example.therealprijectlevelup.viewModels.SettingsViewModel
import androidx.compose.animation.*
import androidx.compose.animation.core.tween

@Composable
fun ChatView(
    onNavigate: (String) -> Unit,
    settingsViewModel: SettingsViewModel,
    chatViewModel: ChatViewModel = viewModel()
) {
    val messages by chatViewModel.messages.collectAsState()
    var input by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            LevelUpHeader(
                title = "Soporte",
                viewModel = settingsViewModel,
                onSearchClick = { onNavigate("search") } // REDIRECCIÓN A BÚSQUEDA
            )
        },
        bottomBar = {
            LevelUpBottomNavigation(
                selectedTab = "messages",
                onTabSelected = onNavigate
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // TÍTULO DEL CHAT
            Text(
                text = "SERVICIO AL CLIENTE",
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3F51B5))
                    .padding(12.dp),
                color = Color.White,
                fontSize = 16.sp
            )

            // MENSAJES
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(messages) { msg ->
                    ChatBubble(
                        text = msg.message,
                        isUser = msg.sender == Sender.USER
                    )
                }
            }

            // INPUT
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Escribe tu mensaje...") },
                    shape = RoundedCornerShape(16.dp)
                )

                IconButton(
                    onClick = {
                        chatViewModel.sendMessage(input)
                        input = ""
                    }
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar")
                }
            }
        }
    }
}


@Composable
fun ChatBubble(
    text: String,
    isUser: Boolean
) {
    AnimatedVisibility(
        visible = true,
        enter = slideInVertically(
            initialOffsetY = { it / 2 },
            animationSpec = tween(300)
        ) + fadeIn(animationSpec = tween(300))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = if (isUser) Color(0xFF0097A7) else Color(0xFF5877FF),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
                    .widthIn(max = 260.dp)
            ) {
                Text(text = text, color = Color.White)
            }
        }
    }
}