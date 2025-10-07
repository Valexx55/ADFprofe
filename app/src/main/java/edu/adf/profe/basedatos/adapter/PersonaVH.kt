package edu.adf.profe.basedatos.adapter

import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.basedatos.entity.Persona
import edu.adf.profe.databinding.FilaPersonaBinding

class PersonaVH(val filaPersona: FilaPersonaBinding): RecyclerView.ViewHolder(filaPersona.root) {

    fun rellenarFila (persona: Persona)
    {
        this.filaPersona.idpersona.text = persona.id.toString()
        this.filaPersona.nombre.text = persona.nombre
        this.filaPersona.edad.text = persona.edad.toString()
    }
}