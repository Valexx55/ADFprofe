package edu.adf.profe.lista

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.R
import edu.adf.profe.Usuario
import edu.adf.profe.databinding.ActivityListaUsuariosBinding

class ListaUsuariosActivity : AppCompatActivity() {

    var listaUsuarios = listOf<Usuario>(Usuario("Vale", 41, 'M', true, R.color.mirojo),
        Usuario("JuanMa", 45, 'M', true, R.color.miazul),
        Usuario("Cristina", 56, 'F', true, R.color.micolorsecundario),
        Usuario("Patri", 46, 'F', true, R.color.minaranja),
        Usuario("JoseAntonio", 53, 'M', true, R.color.mirojo))

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
        this.listaUsuarios = this.listaUsuarios.sortedBy { it.nombre }
        //this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.nombre }//de mayor a menor
        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)
    }
    fun ordenarPorEdad(view: View) {
        this.listaUsuarios = this.listaUsuarios.sortedWith { usuario0, usuario1 -> usuario0.edad - usuario1.edad }
        this.binding.recViewUsuarios.adapter = UsuariosAdapter(this.listaUsuarios)

    }

    //TODO ordenar por nombre y edad
}