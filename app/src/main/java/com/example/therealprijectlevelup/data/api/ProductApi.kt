package com.example.therealprijectlevelup.data.api

import com.example.therealprijectlevelup.data.dtos.ProductDto
import retrofit2.http.GET
import com.example.therealprijectlevelup.utils.Constants

interface ProductApi {

    @GET(Constants.PRODUCTS_ENDPOINT)
    suspend fun getProducts(): List<ProductDto>
}