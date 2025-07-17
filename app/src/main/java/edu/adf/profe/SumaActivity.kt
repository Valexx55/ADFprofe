package edu.adf.profe

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.adf.profe.databinding.ActivitySumaBinding

class SumaActivity : AppCompatActivity() {

    /**
     * PASOS PARA USAR LA VINCULACIÓN DE VISTAS / BINDING (MÁS FÁCIL QUE EL FINDVIEWBYID)
     *
     * 1) MODIFICO EL FICHERO GRADLE DEL MÓDULO APP (moDULE app build.gradle.kts)
     *
     * buildFeatures {
     *         viewBinding = true
     *     }
     *
     * Después de esto, es necesario hacer SYNC del fichero de Gradle
     * (File -> Sync gradle files)
     *
     * 2) En la actividad, me declaro como atributo de la clase, un objeto de la clase "binding"
     * El nombre de esta clase, viene determinado por el nombre del archivo de layout XML
     * de tal manera que si mi archivo se llama archivo_layout.xml, la clase binding será
     * ArchivoLayoutBinding (se añade el sufijo binding)
     *
     *  lateinit var binding: ActivitySumaBinding
     *
     *  3) Para inicializar correctamente el objeto biding anterior, dentro de oncreate, hacemos:
     *
     *     binding = ActivitySumaBinding.inflate(layoutInflater)
     *     val view = binding.root
     *     setContentView(view)
     *
     */

    lateinit var binding: ActivitySumaBinding //activity_suma.xml

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*versión nueva con Binding*/
        binding = ActivitySumaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //binding tendrá atributos (generados automáticamente) como el id de las vistas
        //que me dan acceso directo a esas vistas (sin tener que hacer findViewById)
        /*fin versión nueva*/

        /*versión "vieja" findViewById*/
        /*enableEdgeToEdge()
        setContentView(R.layout.activity_suma)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        /*versión "vieja" findViewById*/
    }

    fun sumar(view: View) {
        //coger el primer número
        //var numero1:Int = findViewById<EditText>(R.id.cajaUno).text.toString().toInt()
        var numero1:Int = binding.cajaUno.text.toString().toInt()
        //coger el segundo número
        //var numero2:Int = findViewById<EditText>(R.id.cajaDos).text.toString().toInt()
        var numero2:Int = binding.cajaDos.text.toString().toInt()
        //sumar los dos números
        var suma = numero1+numero2
        //mostrar resultado
        //findViewById<TextView>(R.id.resultadoSuma).text = suma.toString()
        var mensaje:String = resources.getString(R.string.mensaje_resultado_suma, numero1, numero2, suma)
        binding.resultadoSuma.text = mensaje

    }
}