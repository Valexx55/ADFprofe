package edu.adf.profe.productos

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ProductosRetrofitHelper {

    private const val URL_BASE = "https://my-json-server.typicode.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getProductoServiceInstance ():ProductoService
    {
        val productoService =  retrofit.create(ProductoService::class.java)
        return productoService
    }
}