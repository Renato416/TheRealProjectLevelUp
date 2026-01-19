package com.example.therealprijectlevelup.data
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiLevelUp {
    @GET("endpoint")
    suspend fun getItems(): List<Any>

    @GET("endpoint/{id}")
    suspend fun getItemById(
        @Path("id") id: Int
    ): Any

    @GET("endpoint")
    suspend fun searchItems(
        @Query("q") query: String
    ): List<Any>
}