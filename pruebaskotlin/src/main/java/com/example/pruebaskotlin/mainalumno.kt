package com.example.pruebaskotlin

fun main() {
    //declaro un alumno

    val alumno:Alumno = Alumno("Vale", 41, 5)
    val alumno1:Alumno = Alumno("Fran", 45, 8)
    val alumno2:Alumno = Alumno("Miguel", 19, 8)

    println("EDAD Fran ${alumno1.edad}")
    val listaAlumnos:List<Alumno> = listOf(alumno, alumno1, alumno2)
    //vamos a recorrer la lista de Alumnos //bucle
    for (al in listaAlumnos) //al es un alumno cada vez
    {
        println("NOMBRE ${al.nombre} EDAD ${al.edad} NOTA ${al.nota}")
    }

    for (a in listaAlumnos)
    {
        edadAlumno(a)
    }

    var media = calculaMedia(listaAlumnos)
    println("La media 1 es $media")
    println("La media 2 es  ${listaAlumnos.map { a ->  a.edad}.average()}")
}

//TODO Haced una función que reciba un Alumno e imprima su edad. No devuelve nada
fun edadAlumno (alumno: Alumno)
{
    println("La edad es ${alumno.edad}")
}

fun calculaMedia (lista:List<Alumno>):Float
{
    var media:Float = 0f
    var suma:Int = 0

        for (al in lista)
        {
            suma = suma + al.edad
        }

         media = suma.toFloat() / lista.size

    return media
}
