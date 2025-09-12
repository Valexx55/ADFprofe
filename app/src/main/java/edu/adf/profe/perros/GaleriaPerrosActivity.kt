package edu.adf.profe.perros

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.R

class GaleriaPerrosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria_perros)
        //TODO
        /*
         *1 obtener del Intent la info de la raza
         * 2 lanzar la petición GET con un corutina de retrofit al api de perros
         */

    }
}