package com.example.pruebaskotlin

fun main() {
    /*val morningNotification = 51
    val eveningNotification = 135

    printNotificationSummary(morningNotification)
    printNotificationSummary(eveningNotification)*/


    var resultadoCadena = traducirResultadoImcVersionIF(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionIF(15.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen(24.0f)
    println(resultadoCadena)
    resultadoCadena = traducirResultadoImcVersionWhen(15.0f)
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

        //TODO completar el cuerpo de la función o con IF o con WHEN
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
            else -> "OBESO"
        }

    return imcResultado
}














class MyClass {
}