package com.example.therealprijectlevelup.repositories

import com.example.therealprijectlevelup.models.Product
import com.example.therealprijectlevelup.models.domain.ProductDomain

interface ProductRepository {
    suspend fun getProducts(): List<ProductDomain>
}