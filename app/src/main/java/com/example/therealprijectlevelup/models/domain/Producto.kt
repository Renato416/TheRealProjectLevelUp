package com.example.therealprijectlevelup.models.domain

data class ProductDomain(
    val id: Long,
    val name: String,
    val price: Long,
    val imageUrl: String,
    val description: String,
    val rating: Double
)
