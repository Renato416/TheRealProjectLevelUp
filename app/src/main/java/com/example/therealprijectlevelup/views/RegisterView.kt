package com.example.therealprijectlevelup.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.therealprijectlevelup.viewModels.RegisterViewModel

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val user = viewModel.user.value
    // OBTENEMOS EL VALOR DE LA CONFIRMACIÓN DEL VIEWMODEL
    val confirmPassword = viewModel.confirmPassword.value

    val blueColor = Color(0xFF5877FF)
    val roundedShape = RoundedCornerShape(12.dp)
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        SimpleHeader(title = "Registrarse")

        Spacer(modifier = Modifier.height(32.dp))

        // CAMPO: CORREO
        OutlinedTextField(
            value = user.email,
            onValueChange = { viewModel.user.value = user.copy(email = it) },
            label = { Text("Ingresar correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO: NOMBRE DE USUARIO
        OutlinedTextField(
            value = user.username,
            onValueChange = { viewModel.user.value = user.copy(username = it) },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO: DIRECCIÓN
        OutlinedTextField(
            value = user.address,
            onValueChange = { viewModel.user.value = user.copy(address = it) },
            label = { Text("Ingresa tu dirección") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            trailingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO: FECHA
        OutlinedTextField(
            value = user.birthDate,
            onValueChange = { viewModel.user.value = user.copy(birthDate = it) },
            label = { Text("Ingresar fecha de nacimiento") },
            placeholder = { Text("dd - mm - aaaa") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            trailingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO: TELÉFONO
        OutlinedTextField(
            value = user.phone,
            onValueChange = { viewModel.user.value = user.copy(phone = it) },
            label = { Text("Ingresar numero de telefono") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO: CONTRASEÑA
        OutlinedTextField(
            value = user.password,
            onValueChange = { viewModel.user.value = user.copy(password = it) },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // CAMPO: CONFIRMAR CONTRASEÑA (NUEVO)
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { viewModel.confirmPassword.value = it },
            label = { Text("Confirmar Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )

        Spacer(modifier = Modifier.height(32.dp))

        // BOTÓN: REGISTRAR
        Button(
            onClick = { viewModel.register() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blueColor,
                contentColor = Color.White
            ),
            shape = roundedShape
        ) {
            Text("Registrar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }

        // MENSAJE DE ERROR
        if (viewModel.registerResult.value.isNotEmpty() && viewModel.registerResult.value != "SUCCESS") {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = viewModel.registerResult.value,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp
            )
        }

        if (viewModel.registerResult.value == "SUCCESS") {
            LaunchedEffect(Unit) {
                onBack()
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}