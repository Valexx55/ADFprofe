package com.example.pruebaskotlin


interface Logger {
    fun log(message: String)
}

class ConsoleLogger : Logger {
    override fun log(message: String) {
        println("Logging: $message")
    }
}

class AppLogger(logger: Logger) : Logger by logger //delegación de implementación la clase implementa esa interfaz por el parámetros de ejemplo

class UserData {
    var name: String
    var age: Int

    constructor() {
        this.name = "Unknown"
        this.age = 0
    }

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

}


fun main() {
    val logger = ConsoleLogger()
    val appLogger = AppLogger(logger)

    appLogger.log("Hello World") // Delegado al ConsoleLogger

    val user: UserData by lazy { //delegación de propiedades : la propiedad user "te inicializas con esto" Delego la lógica de acceso (get y/o set) de esta propiedad a un objeto delegado
        UserData() // La inicialización se retrasa hasta que se acceda a la propiedad.
    }

    val user1:UserData = UserData("VAL", 41)
    println("USUARIO  nombre " +user.name)
    println("USUARIO 1 nombre " +user1.name)
}

