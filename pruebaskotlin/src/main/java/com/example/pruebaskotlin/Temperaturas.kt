package com.example.pruebaskotlin

//PROGRMACIÓN FUNCIONAL --> FUNCIONES COMO PARÁMETROS (ENTRADA A LAS FUNCIONES)
/*
27.0 degrees Celsius is 80.60 degrees Fahrenheit.
350.0 degrees Kelvin is 76.85 degrees Celsius.
10.0 degrees Fahrenheit is 260.93 degrees Kelvin.
 */

fun convertirTemperaturaDeCelsiusAFarenheit (temperaturaInicial: Double) : Double
{
    var temperaturaFinal: Double = 0.0

        temperaturaFinal = (temperaturaInicial *9)/5+32

    return  temperaturaFinal
}

var funcionDeCelisiusAFaranheitLambda = {temperaturaInicial: Double -> (temperaturaInicial *9)/5+32}

var funcionDeCelisiusAFaranheitAnonima = fun (temperaturaInicial: Double):Double {
    var temperaturaFinal: Double = 0.0

    temperaturaFinal = (temperaturaInicial *9)/5+32

    return  temperaturaFinal
}

fun main() {
    // Fill in the code.

    //como función variable anónima
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", funcionDeCelisiusAFaranheitAnonima )
    //como función variable lambda
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", funcionDeCelisiusAFaranheitLambda )
    //Referencia
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", ::convertirTemperaturaDeCelsiusAFarenheit )
    //función lambda
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", {temperaturaInicial -> (temperaturaInicial *9)/5+32} )

    //función anónima
    printFinalTemperature(27.0, "Celsius", "Fahrenheit", fun (temperaturaInicial: Double):Double {
        var temperaturaFinal: Double = 0.0

            temperaturaFinal = (temperaturaInicial *9)/5+32

        return  temperaturaFinal
    } )
    printFinalTemperature(350.0, "Kelvin", "Celsius", fun (temperaturaInicial: Double):Double {
        var temperaturaFinal: Double = 0.0

        temperaturaFinal = temperaturaInicial-273.15

        return  temperaturaFinal
    } )
    printFinalTemperature(10.0, "Fahrenheit", "Kelvin", fun (temperaturaInicial: Double):Double {
        var temperaturaFinal: Double = 0.0

            temperaturaFinal = (((temperaturaInicial-32)*5)/9)+273.15

        return  temperaturaFinal
    } )
}


fun printFinalTemperature(
    initialMeasurement: Double,
    initialUnit: String,
    finalUnit: String,
    conversionFormula: (Double) -> Double
) {
    val finalMeasurement = String.format("%.2f", conversionFormula(initialMeasurement)) // two decimal places
    println("$initialMeasurement degrees $initialUnit is $finalMeasurement degrees $finalUnit.")
}