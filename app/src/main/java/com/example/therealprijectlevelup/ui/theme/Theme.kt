package com.example.therealprijectlevelup.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color // IMPORTANTE PARA USAR COLORES PERSONALIZADOS
import androidx.compose.ui.platform.LocalContext

// 1. ESQUEMA DE COLORES PARA MODO OSCURO
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,

    // DEFINICIÓN DE FONDOS PARA MODO OSCURO
    background = Color(0xFF121212), // NEGRO CASI TOTAL
    surface = Color(0xFF1E1E1E),    // GRIS OSCURO PARA CARDS Y CONTENEDORES
    onBackground = Color.White,     // TEXTO SOBRE EL FONDO
    onSurface = Color.White         // TEXTO SOBRE LOS CARDS
)

// 2. ESQUEMA DE COLORES PARA MODO CLARO
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,

    // DEFINICIÓN DE FONDOS PARA MODO CLARO
    background = Color.White,
    surface = Color(0xFFF5F5F5),    // GRIS MUY CLARO PARA CARDS
    onBackground = Color.Black,     // TEXTO SOBRE EL FONDO
    onSurface = Color.Black         // TEXTO SOBRE LOS CARDS
)

@Composable
fun TheRealPrijectLevelUpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // CAMBIAMOS DYNAMICCOLOR A FALSE PARA PRIORIZAR TUS COLORES DE NIVEL UP
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}