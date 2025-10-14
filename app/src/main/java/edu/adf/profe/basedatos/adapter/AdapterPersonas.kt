package edu.adf.profe.basedatos.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.adf.profe.Constantes
import edu.adf.profe.basedatos.entity.PersonaConDetalles
import edu.adf.profe.databinding.FilaPersonaBinding

//class AdapterPersonas (var listaPersonas:List<Persona>): Adapter<PersonaVH>() {
class AdapterPersonas (var listaPersonas: List<PersonaConDetalles>, val onClickFila: (PersonaConDetalles) -> Unit): Adapter<PersonaVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaVH {
        var filaPersona = FilaPersonaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PersonaVH(filaPersona)
    }

    override fun getItemCount(): Int {
        return listaPersonas.size
    }

    override fun onBindViewHolder(holder: PersonaVH, position: Int) {
        val personaActual = this.listaPersonas[position]
        holder.rellenarFila(personaActual)
        holder.itemView.setOnClickListener {
            Log.d(Constantes.ETIQUETA_LOG, "Tocado fila $personaActual")
            onClickFila(personaActual)//TODO crear un sólo listener para todas las filas
            //clickFila(personaActual)
        }
    }

}