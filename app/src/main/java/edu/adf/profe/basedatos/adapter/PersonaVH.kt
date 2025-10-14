package edu.adf.profe.basedatos.adapter

import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.basedatos.entity.PersonaConDetalles
import edu.adf.profe.databinding.FilaPersonaBinding

class PersonaVH(val filaPersona: FilaPersonaBinding): RecyclerView.ViewHolder(filaPersona.root) {

    //fun rellenarFila (persona: Persona)
    fun rellenarFila (personaConDetalles: PersonaConDetalles)
    {
        this.filaPersona.idpersona.text =  personaConDetalles.persona.id.toString() //persona.id.toString()
        this.filaPersona.nombre.text = personaConDetalles.persona.nombre //persona.nombre
        this.filaPersona.edad.text = personaConDetalles.persona.edad.toString()
        this.filaPersona.nombreEmpleo.text = personaConDetalles.empleo?.nombre;
       // this.itemView.tag = personaConDetalles.persona.id.toString() // otra idea para luego saber con gettag el id de la persona tocada
    }
}