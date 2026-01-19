package com.example.therealprijectlevelup.utils

class Constants {

    companion object {

        // 🌐 Base URL de la API
        const val BASE_URL = "https://api.example.com/"

        // ⏱ Timeouts
        const val CONNECT_TIMEOUT = 30L  // segundos
        const val READ_TIMEOUT = 30L     // segundos

        // 🔑 Keys para SharedPreferences / DataStore
        const val USER_TOKEN = "user_token"
        const val USER_ID = "user_id"

        // 🧭 Rutas de navegación
        const val HOME_ROUTE = "home"
        const val DETAIL_ROUTE = "detail"

        // 📦 Otros valores comunes
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_RETRIES = 3
    }
}
