package com.example.therealprijectlevelup.models

data class Product(
    val id: Long? = null,
    val name: String = "",
    val price: Long = 0L,
    val imageName: String = "", // URL de imagen
    val description: String = "",
    val rating: Double = 0.0
)
