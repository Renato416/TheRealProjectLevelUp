package com.example.therealprijectlevelup.data.api

import retrofit2.http.GET
import com.example.therealprijectlevelup.data.dtos.ProductResponse

interface ProductApi {

    @GET("api/v2/productos")
    suspend fun getProducts(): ProductResponse
}
