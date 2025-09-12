package edu.adf.profe.perros

import edu.adf.profe.productos.Producto
import retrofit2.http.GET
import retrofit2.http.Path

interface PerrosService {

    @GET("api/breed/{raza}/imagess")
    suspend fun obtenerFotosRaza(@Path("raza") raza:String): FotosRazaPerros
}