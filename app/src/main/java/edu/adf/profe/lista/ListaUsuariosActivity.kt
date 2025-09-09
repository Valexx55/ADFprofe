package edu.adf.profe.lista

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.Usuario
import edu.adf.profe.databinding.ActivityListaUsuariosBinding
import kotlin.system.measureNanoTime

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
    lateinit var adapter: UsuariosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.binding = ActivityListaUsuariosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.adapter = this.adapter
        this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false  )
        //this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, true  )
        //this.binding.recViewUsuarios.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, true  )
        //this.binding.recViewUsuarios.layoutManager = GridLayoutManager(this, 2)


        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false // No soportamos mover ítems verticalmente
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                Log.d(Constantes.ETIQUETA_LOG, "Tocada la posición ${position} en la dirección ${direction}")
                adapter.notifyItemChanged(position)

            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float, // desplazamiento horizontal
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val itemView = viewHolder.itemView
                val background = ColorDrawable()
                val icon: Drawable?
                val iconMargin = 32
                val iconTop = itemView.top + (itemView.height - 64) / 2
                val iconBottom = iconTop + 64

                if (dX > 0) { // Swipe a la derecha
                    background.color = Color.parseColor("#388E3C") // Verde
                    background.setBounds(itemView.left, itemView.top, itemView.left + dX.toInt(), itemView.bottom)
                    background.draw(c)

                    icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_archive)
                    icon?.setBounds(
                        itemView.left + iconMargin,
                        iconTop,
                        itemView.left + iconMargin + 64,
                        iconBottom
                    )
                    icon?.draw(c)

                } else if (dX < 0) { // Swipe a la izquierda
                    background.color = Color.parseColor("#D32F2F") // Rojo
                    background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    background.draw(c)

                    icon = ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                    icon?.setBounds(
                        itemView.right - iconMargin - 64,
                        iconTop,
                        itemView.right - iconMargin,
                        iconBottom
                    )
                    icon?.draw(c)
                }

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(this.binding.recViewUsuarios)

    }

    fun ordenarPorNombre(view: View) {
        //this.listaUsuarios.sortedBy { usuario -> usuario.nombre }

        var ns = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedBy { it.nombre }}
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR POR NOMBRE TARDA ${ns} nanosegundos")

        //this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.nombre }//de mayor a menor
        this.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.adapter = this.adapter
    }
    fun ordenarPorEdad(view: View) {
        var ns1 = measureNanoTime { this.listaUsuarios = this.listaUsuarios.sortedWith { usuario0, usuario1 -> usuario0.edad - usuario1.edad } }
        var ns2 = measureNanoTime {  this.listaUsuarios = this.listaUsuarios.sortedByDescending { it.edad } }

        this.adapter = UsuariosAdapter(this.listaUsuarios)
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR CON WITH TARDA ${ns1} nanosegundos y con BY ${ns2}")

        this.binding.recViewUsuarios.adapter = this.adapter

    }

    fun ordenarPorNombreYEdad(view: View) {

        var ns = measureNanoTime {
        this.listaUsuarios = this.listaUsuarios.sortedWith(
            compareBy<Usuario> { it.nombre }.
            thenBy { it.edad }

                //.thenByDescending { it.edad }
        )}
        Log.d(Constantes.ETIQUETA_LOG, "EN ORDENAR POR NOMBRE y edad TARDA ${ns} nanosegundos")
        this.adapter = UsuariosAdapter(this.listaUsuarios)
        this.binding.recViewUsuarios.adapter = this.adapter
    }






}