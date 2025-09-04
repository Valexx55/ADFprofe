package edu.adf.profe.lista

import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.Usuario

/**
 * Esta clase representa el hueco /fila que se recicla y cuyo contenido se actualiza
 * con la información del usuario que toque
 */
class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)  {

    //definimos un atributo para ligarlo a cada columna de la fila

    val tvNombreUsuario: TextView = itemView.findViewById<TextView>(R.id.nombreusuario)
    val tvEdadUsuario: TextView = itemView.findViewById<TextView>(R.id.edadusuario)
    val tvSexoUsuario: TextView = itemView.findViewById<TextView>(R.id.sexousuario)
    val lcolorfav: LinearLayout = itemView.findViewById<LinearLayout>(R.id.colorusuariofav)

    /**
     * Cargamos la información del usuario en su contenedor
     * @param el usuario corriente/actual
     */
    fun rellenarFilaUsuario (usuario: Usuario)
    {
        this.tvNombreUsuario.text = usuario.nombre
        this.tvSexoUsuario.text = usuario.sexo.toString()
        this.tvEdadUsuario.text = usuario.edad.toString()
        Log.d(Constantes.ETIQUETA_LOG, "COLOR FAV = ${usuario.colorFavorito}")
        //this.lcolorfav.setBackgroundColor(usuario.colorFavorito)//este id es el id del recurso R.color y no lo coge bien. Espera un valor hexadecimal
        this.lcolorfav.setBackgroundColor(this.lcolorfav.context.getColor(usuario.colorFavorito))//este sí lo coge bien, porque del id del recurso, coge el color y lo traduce al hexadecimal correcto

    }


}