package com.example.therealprijectlevelup.utils


import com.example.therealprijectlevelup.R
import com.example.therealprijectlevelup.data.dtos.ProductDto
import com.example.therealprijectlevelup.models.Product
import com.example.therealprijectlevelup.models.domain.ProductDomain

object ProductMapper {


    fun ProductDto.toDomain(): Product {
        return Product(
            id = id,
            name = name,
            price = price,
            imageName = imageName,
            description = description ?: "Descripción no disponible",
            rating = rating ?: 4.5
        )
    }

}
