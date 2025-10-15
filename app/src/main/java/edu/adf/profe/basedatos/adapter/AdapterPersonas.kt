package edu.adf.profe.basedatos.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView.Adapter
import edu.adf.profe.R
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
        /*holder.itemView.setOnClickListener {
            Log.d(Constantes.ETIQUETA_LOG, "Tocado fila $personaActual")
            onClickFila(personaActual)//TODO crear un sólo listener para todas las filas
            //clickFila(personaActual)
        }*/
        holder.itemView.tag = personaActual
        holder.itemView.setOnClickListener (::listenerFila)
        holder.itemView.setOnLongClickListener {
                    val p = PopupMenu(it.context, it)
                    p.getMenuInflater().inflate(R.menu.menu_item_personas, p.getMenu());
                    p.show()
                    it.setBackgroundColor(Color.GRAY)
                    //TODO desmarcar
                    p.setOnDismissListener {

                        // Quitar selección al cerrar el menú
                        //selectedPosition = RecyclerView.NO_POSITION
                        //notifyDataSetChanged()
                    }

                    true
                }



    }
    fun listenerFila (view:View)
    {
        val personaFila =  view.tag as PersonaConDetalles
        onClickFila(personaFila)
    }

}