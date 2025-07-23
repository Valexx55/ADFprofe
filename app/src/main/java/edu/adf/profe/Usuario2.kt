package edu.adf.profe

class Usuario2 {

    var nombre: String
    var edad: Int

    constructor(nombre: String, edad: Int)
    {
        this.nombre = nombre
        this.edad = edad
    }

    fun mayorEdad (): Boolean
    {
        return (this.edad>=18)
    }
}