package edu.adf.profe

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Parcelize
data class Usuario(var nombre:String, var edad:Int, var sexo: Char, var esMayorEdad: Boolean, var colorFavorito: Int = 0):Parcelable
{
    init {
        //se puede definir una lógica para cuando se crear el objeto
    }
}
