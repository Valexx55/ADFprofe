package com.example.pruebaskotlin
//val child = 5//ámbito global
fun main() {
    /*val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)*/

    val child = 5
    val adult = 28
    val senior = 87

    val isMonday = false


    println("The movie ticket price for a person aged $child is \$${ticketPrice(child, isMonday)}.")
    println("The movie ticket price for a person aged $adult is \$${ticketPrice(adult, isMonday)}.")
    println("The movie ticket price for a person aged $senior is \$${ticketPrice(senior, isMonday)}.")

    println(" CON 18 eres ${mayordeEdad(17)}")
    println(" CON 36 eres ${mayordeEdad(36)}")



    var resultadoCadena = traducirResultadoImcVersionIF(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionIF(15.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen(15.0f)
    println(resultadoCadena)

    resultadoCadena = traducirResultadoImcVersionWhen2(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen2(15.0f)
    println(resultadoCadena)

}


fun printNotificationSummary(numberOfMessages: Int) {
    if (numberOfMessages < 100)
    {
        println("You have $numberOfMessages notifications")
    }
    else
    {
        println("Your phone is blowing up! You have 99+ notifications.")
    }
}


/**
 * SI TU IMC ESTÁ POR DEBAJO DE 16 , TU IMC ES DESNUTRIDO
 * SI TU IMC ES MAYOR O IGUAL A 16 Y MENOR QUE 18 --> DELGADO
 * SI TU IMC ES MAYOR O IGUAL A 18 Y MENOR QUE 25 --> IDEAL
 * SI TU IMC ES MAYOR O IGUAL A 25 Y MENOR QUE 31 --> SOBREPESO
 * SI TU IMC ES MAYOR O IGUAL que 31 --> OBESO
 */

fun traducirResultadoImcVersionIF(resultado:Float):String
{
    var imcResultado:String = ""

        if (resultado<16) {
            imcResultado = "DESNUTRIDO"
        } else if (resultado>=16 && resultado<18)
        {
            imcResultado = "DELGADO"
        } else if (resultado>=18 && resultado <25)
        {
            imcResultado = "IDEAL"
        } else if (resultado>=25 && resultado<31)
        {
            imcResultado = "SOBREPESO"
        } else if (resultado>=31)
        {
            imcResultado = "OBESO"
        }

    return imcResultado
}

fun traducirResultadoImcVersionWhen (resultado:Float):String
{
    var imcResultado:String = ""

        imcResultado = when {
            resultado < 16  -> "DESNUTRIDO"
            resultado >= 16 && resultado < 18 -> "DELGADO"
            resultado >= 18 && resultado < 25 -> "IDEAL"
            resultado >= 25 && resultado < 31 -> "SOBREPESO"
            else -> "OBESO" // mayor o igual q 31
        }

    return imcResultado
}


fun traducirResultadoImcVersionWhen2 (resultado:Float):String
{
    var imcResultado:String = ""

    val resultadoInt = resultado.toInt()

    imcResultado = when (resultadoInt) {
        in 1 until 16  -> "DESNUTRIDO"
        in 16 until  18 -> "DELGADO"
        in 18 until 25-> "IDEAL"
        in 25 until 31 -> "SOBREPESO"
        else -> "OBESO" // mayor o igual q 31
    }

    return imcResultado
}


//EJERICICIOS BÁSICOS
/*
1) HACED UNA FUNCIÓN QUE RECIBA UNA EDAD Y DIGA SI ES MAYOR DE EDAD O NO
2) HACED UNA FUNCIÓN, QUE RECIBA UNA NOTA NUMÉRICA Y DIGA LA NOMINAL CORRESPONDIENTE,
BASÁNDOSE EN ESTE RANGO

0-4 SUSPENSO
5- APROBADO
6 - BIEN
7, 8 - NOTABLE
9, 10 - SOBRESALIENTE
*/

fun notaCorrespondiente(resultado:Int):String {
    var notaResoltado: String = ""

    if (resultado <= 4){
        notaResoltado = "SUSPENSO"
    }

    else if (resultado == 5)
    {
        notaResoltado = "APROBADO"
    }


    else if (resultado == 6)

        notaResoltado = "BIEN"
    else if (resultado == 7 || resultado == 8)

        notaResoltado = "NOTABLE"
    else if (resultado == 9 || resultado == 10)

        notaResoltado = "SOBRESALIENTE"

    return notaResoltado
}
/*3) HACED UNA FUNCIÓN QUE RECIBA 3 NÚMEROS Y DIGA CUÁL ES EL MAYOR
*/

/*4) ENUNCIADO DE GOOGLE : PRECIO DE LAS ENTRADAS DE CINE
https://developer.android.com/codelabs/basic-android-kotlin-compose-kotlin-fundamentals-practice-problems?hl=es-419#2
*/

/*
Un precio de entrada infantil de USD 15 para personas de 12 años o menos.
Un precio de entrada estándar de USD 30 para personas de entre 13 y 60 años. Los lunes, un precio estándar con descuento de USD 25 para el mismo grupo etario
Un precio para adultos mayores de USD 20 para personas de 61 años o más (asumimos que la edad máxima de un espectador es de 100 años)
Un valor de -1 para indicar que el precio no es válido cuando un usuario ingresa una edad fuera de las especificaciones
 */

/**
 * Calcula el precio de la entrada al cine según la edad
 * y el día de la semana
 * @param age la edad de la persona
 * @param isMonday indica si es lunes o no
 * @return el precio de la entrada calculado
 * @author Val
 * @since version 1 (9/7/2025)
 */
fun ticketPrice(age: Int, isMonday: Boolean): Int {
    var precioEntrada:Int = -1
    when (age) {
        in 0..12 -> precioEntrada = 15
        in 13..60 -> {
            if (isMonday)
            {
                precioEntrada = 25
            } else
                precioEntrada = 30
        }
        in 61..100 -> precioEntrada = 20
    }
    return precioEntrada
}


//1) HACED UNA FUNCIÓN QUE RECIBA UNA EDAD Y DIGA SI ES MAYOR DE EDAD O NO
fun mayordeEdad(edad:Int):String
{
    var resultado:String=""

    if (edad<18){
        resultado= "MENOR"
    } else resultado = "MAYOR"

    return resultado
}









class MyClass {
}