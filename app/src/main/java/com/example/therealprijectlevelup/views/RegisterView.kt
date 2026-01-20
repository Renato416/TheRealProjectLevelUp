package com.example.therealprijectlevelup.views

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.therealprijectlevelup.data.ChileData
import com.example.therealprijectlevelup.viewModels.RegisterViewModel
import java.util.Calendar

@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    viewModel: RegisterViewModel = viewModel()
) {
    val user = viewModel.user.value
    val confirmPassword = viewModel.confirmPassword.value
    val blueColor = Color(0xFF5877FF)
    val roundedShape = RoundedCornerShape(12.dp)
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // --- VARIABLES DE ESTADO PARA DIRECCIÓN ---
    var selectedRegion by remember { mutableStateOf("") }
    var selectedCommune by remember { mutableStateOf("") }

    var regionExpanded by remember { mutableStateOf(false) }
    var communeExpanded by remember { mutableStateOf(false) }

    // Función para actualizar la dirección completa en el ViewModel
    fun updateFullAddress() {
        val fullAddress = if (selectedRegion.isNotEmpty() && selectedCommune.isNotEmpty()) {
            "$selectedCommune, $selectedRegion"
        } else {
            ""
        }
        viewModel.user.value = user.copy(address = fullAddress)
    }

    // Lógica del Calendario
    val calendar = Calendar.getInstance()
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, y: Int, m: Int, d: Int ->
            val formattedDate = "%02d - %02d - %d".format(d, m + 1, y)
            viewModel.user.value = user.copy(birthDate = formattedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // SE ELIMINÓ EL ROW CON EL BOTÓN DE FLECHA ATRÁS

        Spacer(modifier = Modifier.height(40.dp)) // Espacio superior original

        SimpleHeader(title = "Registrarse")

        Spacer(modifier = Modifier.height(32.dp))

        // CORREO
        OutlinedTextField(
            value = user.email,
            onValueChange = { viewModel.user.value = user.copy(email = it) },
            label = { Text("Ingresar correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // USUARIO
        OutlinedTextField(
            value = user.username,
            onValueChange = { viewModel.user.value = user.copy(username = it) },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        // ============================================================
        // --- SECCIÓN DE DIRECCIÓN CHILENA (SOLO REGIÓN Y COMUNA) ---
        // ============================================================

        // 1. SELECTOR DE REGIÓN
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedRegion,
                onValueChange = {},
                label = { Text("Región") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
                shape = roundedShape,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) }
            )
            Box(modifier = Modifier.matchParentSize().clickable { regionExpanded = true })

            DropdownMenu(
                expanded = regionExpanded,
                onDismissRequest = { regionExpanded = false },
                modifier = Modifier.fillMaxWidth(0.8f).heightIn(max = 250.dp)
            ) {
                ChileData.regionesComunas.keys.forEach { region ->
                    DropdownMenuItem(
                        text = { Text(text = region) },
                        onClick = {
                            selectedRegion = region
                            selectedCommune = ""
                            regionExpanded = false
                            updateFullAddress()
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 2. SELECTOR DE COMUNA
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = selectedCommune,
                onValueChange = {},
                label = { Text("Comuna") },
                placeholder = { if(selectedRegion.isEmpty()) Text("Selecciona Región primero") else Text("") },
                readOnly = true,
                enabled = selectedRegion.isNotEmpty(),
                modifier = Modifier.fillMaxWidth(),
                shape = roundedShape,
                trailingIcon = { Icon(Icons.Default.ArrowDropDown, null) }
            )
            if (selectedRegion.isNotEmpty()) {
                Box(modifier = Modifier.matchParentSize().clickable { communeExpanded = true })
            }

            DropdownMenu(
                expanded = communeExpanded,
                onDismissRequest = { communeExpanded = false },
                modifier = Modifier.fillMaxWidth(0.8f).heightIn(max = 250.dp)
            ) {
                ChileData.regionesComunas[selectedRegion]?.forEach { comuna ->
                    DropdownMenuItem(
                        text = { Text(text = comuna) },
                        onClick = {
                            selectedCommune = comuna
                            communeExpanded = false
                            updateFullAddress()
                        }
                    )
                }
            }
        }

        // ============================================================

        Spacer(modifier = Modifier.height(16.dp))

        // FECHA (CALENDARIO)
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = user.birthDate,
                onValueChange = { },
                label = { Text("Ingresar fecha de nacimiento") },
                placeholder = { Text("dd - mm - aaaa") },
                modifier = Modifier.fillMaxWidth(),
                shape = roundedShape,
                readOnly = true,
                enabled = false,
                trailingIcon = {
                    Icon(
                        Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    disabledBorderColor = MaterialTheme.colorScheme.outline,
                    disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Box(modifier = Modifier.matchParentSize().clickable { datePickerDialog.show() })
        }

        Spacer(modifier = Modifier.height(16.dp))

        // TELÉFONO
        OutlinedTextField(
            value = user.phone,
            onValueChange = { viewModel.user.value = user.copy(phone = it) },
            label = { Text("Ingresar numero de telefono") },
            modifier = Modifier.fillMaxWidth(),
            shape = roundedShape,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Spacer(modifier = Modifier.height(16.dp))

        // CONTRASEÑA
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

        // CONFIRMAR CONTRASEÑA
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

        // REGISTRAR
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