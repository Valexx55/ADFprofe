package edu.adf.profe.lista

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.R
import edu.adf.profe.Usuario

class UsuariosAdapter (var listaUsuarios: List<Usuario>): RecyclerView.Adapter<UsuarioViewHolder> (){

    //"inflamos la fila"
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UsuarioViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val filaUsuario = layoutInflater.inflate(R.layout.fila_usuario, parent, false)
        val usuarioViewHolder = UsuarioViewHolder(filaUsuario)
        return usuarioViewHolder

    }

    //" rellenamos la fila"
    override fun onBindViewHolder(
        holder: UsuarioViewHolder,
        position: Int
    ) {
        val usuarioActual = this.listaUsuarios.get(position)
        holder.rellenarFilaUsuario(usuarioActual)
    }

    //gracias a este métod o, el Recycler sabe cuántos items tiene que pintar
    override fun getItemCount(): Int {
        return this.listaUsuarios.size
    }
}