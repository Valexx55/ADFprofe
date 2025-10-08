package edu.adf.profe.basedatos

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.basedatos.adapter.AdapterPersonas
import edu.adf.profe.basedatos.entity.Persona
import edu.adf.profe.basedatos.viewmodel.PersonaViewModel
import edu.adf.profe.databinding.ActivityBaseDatosBinding

class BaseDatosActivity : AppCompatActivity() {

    val personas:MutableList<Persona> = mutableListOf()//creamos la lista de personas vacía
   lateinit var binding: ActivityBaseDatosBinding
   lateinit var adapterPersonas: AdapterPersonas

   //al insntaciar este atributo, se ejecuta la sección init de PersonaViewModel
   val personaViewModel:PersonaViewModel by viewModels()//aquí guardamos los datos de la pantalla


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)


        adapterPersonas = AdapterPersonas(personas)
        binding.recview.adapter = adapterPersonas
        binding.recview.layoutManager = LinearLayoutManager(this)
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(binding.recview)

        //ligamos las actualizaciones automáticas de la lista

        personaViewModel.personas.observe(this, {
            personas ->
            //Log.d(Constantes.ETIQUETA_LOG, "Personas = $personas")
            personas?.let {
                Log.d(Constantes.ETIQUETA_LOG, "Personas (${personas.size}) = $personas")
                adapterPersonas.listaPersonas = it
                adapterPersonas.notifyDataSetChanged()
            }
        })

    }

    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre="Andrés", edad = 25))
        personaViewModel.contarPersonas()
    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
        ): Boolean {
            return false // No necesitamos mover los elementos, solo manejar el deslizamiento
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            val persona = this@BaseDatosActivity.adapterPersonas.listaPersonas[position] // Método que debes crear en tu adaptador
            // Aquí es donde eliminamos el ítem
            personaViewModel.borrar(persona)

            // Mostrar Snackbar para deshacer la eliminación
            Snackbar.make(this@BaseDatosActivity.binding.recview, "Persona eliminada", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    // Si el usuario quiere deshacer, simplemente reinsertamos el ítem
                    personaViewModel.insertar(persona)
                }
                .show()
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
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

            // Solo aplicar si se está deslizando hacia la izquierda
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX < 0) {
                val itemView = viewHolder.itemView
                val paint = Paint()
                paint.color = Color.RED

                // Dibuja el fondo rojo
                c.drawRect(
                    itemView.right.toFloat() + dX, // izquierda del fondo
                    itemView.top.toFloat(),
                    itemView.right.toFloat(),      // derecha del fondo
                    itemView.bottom.toFloat(),
                    paint
                )

                // Carga el icono
                val deleteIcon =
                    ContextCompat.getDrawable(recyclerView.context, R.drawable.ic_delete)
                val iconMargin = 32
                val iconSize = 64

                deleteIcon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSize) / 2
                    val iconLeft = itemView.right - iconMargin - iconSize
                    val iconRight = itemView.right - iconMargin
                    val iconBottom = iconTop + iconSize

                    it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    it.draw(c)

                    // 3. Texto "Eliminar"
                    val text = "Eliminar"
                    val textPaint = Paint()
                    textPaint.color = Color.WHITE
                    textPaint.textSize = 40f
                    textPaint.isAntiAlias = true
                    textPaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)

                    // Calcular ancho del texto
                    val textWidth = textPaint.measureText(text)

                    // Dibujar texto a la izquierda del ícono
                    val textX = iconLeft - textWidth - 20f
                    val textY = itemView.top + itemView.height / 2f + 15f // Ajuste vertical

                    c.drawText(text, textX, textY, textPaint)
                }
            }
        }
    }
}