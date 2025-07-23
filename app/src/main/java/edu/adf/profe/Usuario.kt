package edu.adf.profe

data class Usuario(var nombre:String, var edad:Int, var sexo: Char, var esMayorEdad: Boolean, var colorFavorito: Int = 0)
{
    init {
        //se puede definir una lógica para cuando se crear el objeto
    }
}
