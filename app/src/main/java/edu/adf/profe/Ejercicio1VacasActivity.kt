package edu.adf.profe

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import edu.adf.profe.databinding.ActivityEjercicio1VacasBinding
import edu.adf.profe.databinding.ActivityVideoBinding

class Ejercicio1VacasActivity : AppCompatActivity() {

    var ncambios: Int = 0
    lateinit var binding:ActivityEjercicio1VacasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEjercicio1VacasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_ejercicio1_vacas)

    }

    fun cambiarFondo(view: View) {
        val valor = view.tag
        if (valor==null)//no tengo nada asociado, es la primera vez que toco es caja/vista
        {
            view.setBackgroundColor(resources.getColor(R.color.black))
            view.tag=true
            ncambios = ncambios+1
            if (ncambios==4)
            {
                Snackbar.make(binding.main, "JUEGO TERMINADO", BaseTransientBottomBar.LENGTH_LONG)
                    .addCallback(object : Snackbar.Callback() {
                    override fun onDismissed(transientBottomBar: Snackbar?, event: Int)
                    {
                        super.onDismissed(transientBottomBar, event)
                        finish() //hacemos el finish de la actividad después de quitarse el snackbar
                    }
                }).show()

            }
        }
        //si ya está en negro
            //no hago nada
        //si no (si está en azul)
            //lo paso a negro

    }
}