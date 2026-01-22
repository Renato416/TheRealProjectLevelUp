package com.example.therealprijectlevelup.repositories

import com.example.therealprijectlevelup.data.api.ProductApi
import com.example.therealprijectlevelup.models.domain.ProductDomain
import com.example.therealprijectlevelup.utils.ProductMapper.toDomain
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val api: ProductApi
) {

    suspend fun getProducts(): List<ProductDomain> {
        return api.getProducts().map { it.toDomain() }
    }
}
