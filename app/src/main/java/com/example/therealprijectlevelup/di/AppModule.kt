package com.example.therealprijectlevelup.di

import com.example.therealprijectlevelup.data.ApiLevelUp
import com.example.therealprijectlevelup.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton



@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // 🟢 OkHttp Client
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    // 🟢 Retrofit
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)   // Asegúrate de que termina en "/"
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // 🟢 ApiLevelUp
    @Provides
    @Singleton
    fun provideApiLevelUp(retrofit: Retrofit): ApiLevelUp {
        return retrofit.create(ApiLevelUp::class.java)
    }
}
