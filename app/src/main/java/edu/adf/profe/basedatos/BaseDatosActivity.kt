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
import edu.adf.profe.basedatos.entity.Empleo
import edu.adf.profe.basedatos.entity.Persona
import edu.adf.profe.basedatos.entity.PersonaConDetalles
import edu.adf.profe.basedatos.viewmodel.PersonaViewModel
import edu.adf.profe.databinding.ActivityBaseDatosBinding
import java.util.Date

class BaseDatosActivity : AppCompatActivity() {

  //  var personas:MutableList<Persona> = mutableListOf()//creamos la lista de personas vacía
   var personas:MutableList<PersonaConDetalles> = mutableListOf()//creamos la lista de personas vacía
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

       // personaViewModel.personas.observe(this, {
        personaViewModel.personasDetalles.observe(this, {
            personas ->
            //Log.d(Constantes.ETIQUETA_LOG, "Personas = $personas")
            personas?.let {
                Log.d(Constantes.ETIQUETA_LOG, "Personas (${personas.size}) = $personas")
                adapterPersonas.listaPersonas = it
                //TODO deberíamos controlar si la lista se ha actualizado por borrar
                // o por insertar y en qué posición, para así usar
                // notifyItemRemoved(posicion_elemento_eliminado);
                // o notifyItemInserted(posicion_elemento_insertado);
                //y repintar sólo esa posición de la fila
                //adapterPersonas.notifyDataSetChanged()
               when (personaViewModel.ultimaOperacionBD)
                {
                    UltimaOperacionBD.INSERTAR -> {
                        adapterPersonas.notifyItemInserted(personaViewModel.posicionAfectada)
                        //adapterPersonas.notifyDataSetChanged()
                        Log.d(Constantes.ETIQUETA_LOG, "Lista actualizada tras INSERCIÓN en pos ${personaViewModel.posicionAfectada}")
                                                  }
                    UltimaOperacionBD.BORRAR -> {
                        adapterPersonas.notifyItemRemoved (personaViewModel.posicionAfectada)
                    Log.d(Constantes.ETIQUETA_LOG, "Lista actualizada tras BORRADO en pos ${personaViewModel.posicionAfectada}")}
                    UltimaOperacionBD.NINGUNA -> {
                        adapterPersonas.notifyDataSetChanged()
                        Log.d(Constantes.ETIQUETA_LOG, "Lista actualizada sin inserción ni borrado")
                    }
                }
                personaViewModel.ultimaOperacionBD = UltimaOperacionBD.NINGUNA//actualizamos
            }
        })

    }

    fun insertarPersona(view: View) {
        personaViewModel.insertar(Persona(nombre =generarNombre(), edad =generarNumeroAleatorio()), personaViewModel.personasDetalles.value!!.size)
        personaViewModel.contarPersonas()
    }

    fun insertarPersonaYEmpleo(view: View) {
        val personaAux = Persona(nombre =generarNombre(), edad =generarNumeroAleatorio())
        val empleoAux = Empleo(0, 0, "BARRANDERO", Date(), 1500.0, Empleo.TipoContrato.TEMPORAL)
        personaViewModel.insertarPersonaYEmpleo(personaAux, personaViewModel.personasDetalles.value!!.size, empleoAux)

    }

    val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(
            recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
        ): Boolean {
            return false // No necesitamos mover los elementos, solo manejar el deslizamiento
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            if (direction == ItemTouchHelper.RIGHT)
            {
                Log.d(Constantes.ETIQUETA_LOG, "Swiped right - favorito")
                adapterPersonas.notifyItemChanged(position)//repintamos el azul a su original
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "Swiped left - eliminar")
                val persona = this@BaseDatosActivity.adapterPersonas.listaPersonas[position].persona
                val empleo = this@BaseDatosActivity.adapterPersonas.listaPersonas[position].empleo
                // Método que debes crear en tu adaptador
                // Aquí es donde eliminamos el ítem
                personaViewModel.borrar(persona, position)

                // Mostrar Snackbar para deshacer la eliminación
                Snackbar.make(this@BaseDatosActivity.binding.recview, "Persona eliminada", Snackbar.LENGTH_LONG)
                    .setAction("Deshacer") {
                        // Si el usuario quiere deshacer, simplemente reinsertamos el ítem
                        personaViewModel.insertarPersonaYEmpleo(persona, position, empleo!!)
                    }
                    .show()
            }


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

            else if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && dX > 0) {
                Log.d(Constantes.ETIQUETA_LOG, "Está girando a la derecha")
                val itemView = viewHolder.itemView
                val paint = Paint()
                paint.color = Color.BLUE

                val margenIzquierdo = itemView.left.toFloat()
                val margenDerecho =itemView.left.toFloat() + dX
                val margenSuperior = itemView.top.toFloat()
                val margenInferior = itemView.bottom.toFloat()

                Log.d(Constantes.ETIQUETA_LOG, "MIZ = $margenIzquierdo MD = $margenDerecho MSUP = $margenSuperior MINF = $margenInferior")

                // Dibuja el fondo azul
                c.drawRect(
                    margenIzquierdo, // izquierda del fondo
                    margenSuperior,
                    margenDerecho,      // derecha del fondo
                    margenInferior,
                    paint
                )

                // Carga el icono
                val favoriteIcon =
                    ContextCompat.getDrawable(recyclerView.context, R.drawable.baseline_favorite_24)
                val iconMargin = 32
                val iconSize = 64

                favoriteIcon?.let {
                    val iconTop = itemView.top + (itemView.height - iconSize) / 2
                    val iconLeft = itemView.left + iconMargin
                    val iconRight = itemView.left + iconMargin + iconSize
                    val iconBottom = iconTop + iconSize

                    it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    it.draw(c)

                    // 3. Texto "favorito"
                    val text = "favorito"
                    val textPaint = Paint()
                    textPaint.color = Color.WHITE
                    textPaint.textSize = 40f
                    textPaint.isAntiAlias = true
                    textPaint.typeface = Typeface.create(Typeface.DEFAULT_BOLD, Typeface.BOLD_ITALIC)

                    // Calcular ancho del texto
                    val textWidth = textPaint.measureText(text)

                    // Dibujar texto a la izquierda del ícono
                    val textX = itemView.left + iconMargin + iconSize + 20f //iconRight - textWidth - 20f
                    val textY = itemView.top + itemView.height / 2f + 15f // Ajuste vertical

                    c.drawText(text, textX, textY, textPaint)
                }
            }
        }
    }

    fun generarNumeroAleatorio(): Int {
        return (1..100).random()
    }

    // para generar nombrea aleatorios
    fun generarNombre(): String {
        val silabas = listOf(
            "ma", "ri", "an", "jo", "se", "la", "lu", "mi", "el", "no",
            "da", "na", "so", "le", "pe", "ro", "car", "al", "be", "vi"
        )

        val cantidadSilabas = (2..3).random() // nombres de 2 o 3 sílabas

        val nombre = StringBuilder()
        repeat(cantidadSilabas) {
            nombre.append(silabas.random())
        }

        // Capitalizar la primera letra
        return nombre.toString().replaceFirstChar { it.uppercaseChar() }
    }

}