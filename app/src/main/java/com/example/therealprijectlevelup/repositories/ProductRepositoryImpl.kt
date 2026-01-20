package com.example.therealprijectlevelup.repositories

import com.example.therealprijectlevelup.data.api.ProductApi
import com.example.therealprijectlevelup.models.Product
import com.example.therealprijectlevelup.models.domain.ProductDomain
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): List<ProductDomain> {
        return api.getProducts().map {
            ProductDomain(
                id = it.id,
                name = it.name,
                price = it.price,
                imageUrl = it.imageName,
                description = it.description ?: "Descripción no disponible.",
                rating = it.rating ?: 4.5
            )
        }
    }
}