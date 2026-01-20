package com.example.therealprijectlevelup.data.api

import com.example.therealprijectlevelup.models.User
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("auth/register")
    suspend fun register(@Body user: User): User


}