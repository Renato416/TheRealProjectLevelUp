package com.example.therealprijectlevelup.models

data class User(
    val id: Long? = null,
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val address: String = "",
    val phone: String = "",
    val birthDate: String = "" // ISO-8601: "1995-05-10"
)
