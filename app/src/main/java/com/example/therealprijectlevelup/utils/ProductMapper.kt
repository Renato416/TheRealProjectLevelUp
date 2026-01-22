package com.example.therealprijectlevelup.utils

import com.example.therealprijectlevelup.data.dtos.ProductDto
import com.example.therealprijectlevelup.models.domain.ProductDomain

fun ProductDto.toDomain(): ProductDomain =
    ProductDomain(
        id = id,
        name = name,
        price = price,
        imageUrl = imageName,
        description = description ?: "Descripción no disponible",
        rating = rating ?: 4.5
    )
