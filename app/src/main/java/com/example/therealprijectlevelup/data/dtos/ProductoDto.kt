package com.example.therealprijectlevelup.data.dtos

data class ProductDto(
    val id: Long,
    val name: String,
    val price: Long,
    val imageUrl: String,
    val description: String?,
    val rating: Double?
)
