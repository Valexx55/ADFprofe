package edu.adf.profe.lista

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.Usuario
import edu.adf.profe.databinding.ActivityListaUsuariosBinding
import kotlin.system.measureNanoTime
import kotlin.time.measureTime

class ListaUsuariosActivity : AppCompatActivity() {

    var listaUsuarios = listOf<Usuario>(Usuario("Vale", 41, 'M', true, R.color.mirojo),
        Usuario("JuanMa", 45, 'M', true, R.color.miazul),
        Usuario("Cristina", 56, 'F', true, R.color.micolorsecundario),
        Usuario("Patri", 46, 'F', true, R.color.minaranja),
        Usuario("JoseAntonio", 53, 'M', true, R.color.mirojo),
        Usuario("Marcos", 31, 'M', true, R.color.gris),
        Usuario("Jorge", 18, 'M', true, R.color.azulclaro),
        Usuario("Jorge", 33, 'M', true, R.color.white)
    )

    lateinit var binding: ActivityListaUsuariosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false  )
        //this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true  )
        //this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, true  )
        //this.binding.recViewUsuarios.layoutManager = GridLayoutManager(this, 2)


    }

    fun ordenarPorNombre(view: View) {
        //this.listaUsuarios.sortedBy { usuario -> usuario.nombre }

        var ns = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedBy { it.nombre }}
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR POR NOMBRE TARDA ${ns} nanosegundos")

        //this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.nombre }//de mayor a menor
        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }
    fun ordenarPorEdad(view: View) {
        var ns1 = measureNanoTime { this.listaUsuarios = this.listaUsuarios.sortedWith { usuario0, usuario1 -> usuario0.edad - usuario1.edad } }
        var ns2 = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.edad } }

        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR CON WITH TARDA ${ns1} nanosegundos y con BY ${ns2}")

        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)

    }

    fun ordenarPorNombreYEdad(view: View) {

        var ns = measureNanoTime {
        this.listaUsuarios = this.listaUsuarios.sortedWith(
            compareBy<Usuario> { it.nombre }.
            thenBy { it.edad }

                //.thenByDescending { it.edad }
        )}
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR POR NOMBRE y edad TARDA ${ns} nanosegundos")
        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }


}