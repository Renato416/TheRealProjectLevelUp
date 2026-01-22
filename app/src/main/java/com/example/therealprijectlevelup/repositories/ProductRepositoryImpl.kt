package com.example.therealprijectlevelup.repositories

import com.example.therealprijectlevelup.data.api.ProductApi
import com.example.therealprijectlevelup.models.domain.ProductDomain
import com.example.therealprijectlevelup.utils.toDomain
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): List<ProductDomain> {
        return api.getProducts()
            ._embedded
            .productoDTOList
            .map { it.toDomain() }
    }
}
