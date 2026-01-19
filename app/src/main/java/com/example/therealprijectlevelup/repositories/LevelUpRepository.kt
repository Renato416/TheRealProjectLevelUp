package com.example.therealprijectlevelup.repositories


import com.example.therealprijectlevelup.data.ApiLevelUp
import javax.inject.Inject
import javax.inject.Singleton
import retrofit2.HttpException

@Singleton
class LevelUpRepository @Inject constructor(
    private val api: ApiLevelUp
) {

    // Ejemplo de función para obtener items
    suspend fun getItems(): List<Any>? {
        return try {
            val response = api.getItems()
            // Aquí podrías transformar la respuesta si quieres
            response
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Ejemplo de obtener item por id
    suspend fun getItemById(id: Int): Any? {
        return try {
            api.getItemById(id)
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Ejemplo de búsqueda
    suspend fun searchItems(query: String): List<Any>? {
        return try {
            api.searchItems(query)
        } catch (e: HttpException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
