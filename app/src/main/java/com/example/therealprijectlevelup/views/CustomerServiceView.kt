package com.example.therealprijectlevelup.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.therealprijectlevelup.viewModels.SettingsViewModel

// MODELO DE DATOS PARA LOS MENSAJES
data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

@Composable
fun CustomerServiceView(onNavigate: (String) -> Unit, viewModel: SettingsViewModel) {
    val messages = listOf(
        ChatMessage("en que te puedo ayudar?", false),
        ChatMessage("que medios de pago poseen?", true),
        ChatMessage("unicamente online en esta app", false),
        ChatMessage("para pagos en efectivo recomiendo ir a la sucursal", false)
    )

    Scaffold(
        topBar = { LevelUpHeader(title = "Level UP", viewModel = viewModel) },
        bottomBar = { LevelUpBottomNavigation(selectedTab = "messages", onTabSelected = onNavigate) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // SUB-HEADER: SERVICIO AL CLIENTE
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF3F51B5)) // AZUL OSCURO SEGÚN REFERENCIA
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SERVICIO AL CLIENTE",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            // LISTA DE MENSAJES
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messages) { message ->
                    MessageBubble(message)
                }

                // INDICADOR DE ESCRITURA (LOS TRES PUNTITOS)
                item {
                    Row(
                        modifier = Modifier.padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        repeat(3) {
                            Surface(
                                modifier = Modifier.size(12.dp),
                                shape = androidx.compose.foundation.shape.CircleShape,
                                color = Color.LightGray,
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.Gray)
                            ) {}
                        }
                    }
                }
            }

            // AREA DE ENTRADA DE TEXTO
            ChatInputArea()
        }
    }
}

@Composable
fun MessageBubble(message: ChatMessage) {
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    val backgroundColor = if (message.isUser) Color(0xFF0097A7) else Color(0xFF5877FF)
    val shape = if (message.isUser) {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 0.dp)
    } else {
        RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp)
    }

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Surface(
            color = backgroundColor,
            shape = shape,
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                color = Color.White,
                modifier = Modifier.padding(12.dp),
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun ChatInputArea() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = "que tarjetas reciben?",
            onValueChange = {},
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = { /* ACCIÓN VISUAL */ }) {
            Icon(
                imageVector = Icons.Default.Send,
                contentDescription = "Enviar",
                modifier = Modifier.size(32.dp),
                tint = Color.Black
            )
        }
    }
}