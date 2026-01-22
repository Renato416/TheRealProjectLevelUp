package com.example.therealprijectlevelup.repositories

import android.util.Log
import com.example.therealprijectlevelup.data.api.ProductApi
import com.example.therealprijectlevelup.models.domain.ProductDomain
import com.example.therealprijectlevelup.utils.toDomain
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val api: ProductApi
) : ProductRepository {

    override suspend fun getProducts(): List<ProductDomain> {

        Log.e("BACKEND_TEST", "➡️ LLAMANDO AL BACKEND")

        val response = api.getProducts()

        Log.e(
            "BACKEND_TEST",
            "✅ RESPUESTA RECIBIDA. Productos: ${response._embedded.productoDTOList.size}"
        )

        return response._embedded.productoDTOList.map {
            Log.d("BACKEND_TEST", "🧩 Producto: ${it.name}")
            it.toDomain()
        }
    }
}
