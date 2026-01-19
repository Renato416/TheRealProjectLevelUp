package com.example.therealprijectlevelup.models

data class Product(
    val id: Int,
    val name: String,
    val price: String,
    val imageRes: Int,
    val description: String = "Descripción no disponible.", // Valor por defecto
    val rating: Double = 4.5 // Valor por defecto
)