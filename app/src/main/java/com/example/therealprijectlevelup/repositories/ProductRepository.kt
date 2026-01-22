package com.example.therealprijectlevelup.repositories

import com.example.therealprijectlevelup.data.api.ProductApi
import com.example.therealprijectlevelup.models.domain.ProductDomain
import com.example.therealprijectlevelup.utils.toDomain
import javax.inject.Inject

interface ProductRepository {
    suspend fun getProducts(): List<ProductDomain>
}
