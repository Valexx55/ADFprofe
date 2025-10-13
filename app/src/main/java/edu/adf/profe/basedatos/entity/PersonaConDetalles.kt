package edu.adf.profe.basedatos.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PersonaConDetalles(
    @Embedded val persona: Persona,

    @Relation(
        parentColumn = "id",
        entityColumn = "personaId"
    )
    val empleo: Empleo?,

    /*
    @Relation(
        parentColumn = "id",
        entityColumn = "personaId"
    )
    val coches: List<Coche>*/
)

