package com.example.therealprijectlevelup.data.dtos

data class ProductResponse(
    val _embedded: EmbeddedProducts
)

data class EmbeddedProducts(
    val productoDTOList: List<ProductDto>
)
