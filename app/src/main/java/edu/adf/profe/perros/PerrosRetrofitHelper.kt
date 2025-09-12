package edu.adf.profe.perros


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PerrosRetrofitHelper {

    private const val URL_BASE_PERROS = "https://dog.ceo"

    private val retrofit = Retrofit.Builder()
        .baseUrl(URL_BASE_PERROS)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}