package com.example.therealprijectlevelup.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.therealprijectlevelup.models.Product
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    val products = mutableStateOf<List<Product>>(emptyList())
    var isLoading = mutableStateOf(false)
    var searchText = mutableStateOf("")
    var focusSearchOnEntry = mutableStateOf(false)

    init {
        loadProducts()
    }

    val filteredProducts: List<Product>
        get() {
            val query = searchText.value
            return if (query.isEmpty()) {
                products.value
            } else {
                products.value.filter {
                    it.name.contains(query, ignoreCase = true)
                }
            }
        }

    private fun loadProducts() {
        viewModelScope.launch {
            isLoading.value = true
            delay(1000)

            // DATOS ACTUALIZADOS AL NUEVO MODELO (Long, String URL, Double)
            products.value = listOf(
                Product(
                    id = 1L,
                    name = "Silla Gamer",
                    price = 250990L, // Ahora es Long (sin comillas)
                    imageName = "https://resource.logitechg.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/embody/embody-product-gallery-cyan-front.png?v=1", // URL de Internet
                    description = "Ergonomía superior para largas sesiones de juego. Soporte lumbar ajustable y material transpirable.",
                    rating = 4.8
                ),
                Product(
                    id = 2L,
                    name = "Mesa Gamer",
                    price = 110990L,
                    imageName = "https://media.spdigital.cl/file_upload/Mobile_Unit_File/a590c67e-2f3b-4886-9769-95246755497b.png",
                    description = "Superficie amplia con textura de fibra de carbono. Incluye soporte para auriculares y vasos.",
                    rating = 4.5
                ),
                Product(
                    id = 3L,
                    name = "Microfono Pro",
                    price = 80000L,
                    imageName = "https://resource.logitech.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/yeti-x/yeti-x-gallery-1.png?v=1",
                    description = "Captura de audio nítida con cancelación de ruido. Ideal para streaming y podcasting.",
                    rating = 4.7
                ),
                Product(
                    id = 4L,
                    name = "Audifonos RGB",
                    price = 20990L,
                    imageName = "https://resource.logitech.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/g733/gallery/g733-lilac-gallery-1.png?v=1",
                    description = "Sonido envolvente 7.1 y almohadillas de espuma viscoelástica para máxima comodidad.",
                    rating = 4.2
                ),
                Product(
                    id = 5L,
                    name = "Cooler Pad",
                    price = 15000L,
                    imageName = "https://m.media-amazon.com/images/I/71ib3+U5V8L._AC_SL1500_.jpg",
                    description = "Base refrigerante con 4 ventiladores silenciosos e iluminación LED azul.",
                    rating = 4.0
                ),
                Product(
                    id = 6L,
                    name = "Mouse Gamer",
                    price = 12000L,
                    imageName = "https://resource.logitech.com/w_692,c_lpad,ar_4:3,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/g502-lightspeed-gaming-mouse/g502-lightspeed-gallery-1.png?v=1",
                    description = "Sensor óptico de alta precisión hasta 16000 DPI. Botones programables.",
                    rating = 4.9
                )
            )
            isLoading.value = false
        }
    }

    // Adaptamos la función para buscar Longs aunque recibamos un Int desde la navegación
    fun getProductById(id: Int): Product? {
        return products.value.find { it.id == id.toLong() }
    }
}