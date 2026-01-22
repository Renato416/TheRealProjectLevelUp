package com.example.therealprijectlevelup.utils

import com.example.therealprijectlevelup.data.dtos.ProductDto
import com.example.therealprijectlevelup.models.domain.ProductDomain

object ProductMapper {

    fun ProductDto.toDomain(): ProductDomain {
        return ProductDomain(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
            description = description ?: "Descripción no disponible",
            rating = rating ?: 4.5
        )
    }
}
