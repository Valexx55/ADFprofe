package edu.adf.profe.servicios

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import edu.adf.profe.Constantes
import edu.adf.profe.R


class PlayActivity : AppCompatActivity() {
    lateinit var startButton: Button
    var stopButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        startButton = findViewById<View?>(R.id.botonstart) as Button
        stopButton = findViewById<View?>(R.id.botonstop) as Button

        startButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val startIntent: Intent = Intent(this@PlayActivity, PlayService::class.java)
                startIntent.setAction(Constantes.STARTFOREGROUND_ACTION)
                startService(startIntent)
            }
        })

        stopButton!!.setOnClickListener(object : View.OnClickListener { //clase anónima
            override fun onClick(v: View?) {

                val intent = Intent(this@PlayActivity, PlayService::class.java)
                this@PlayActivity.stopService(intent)
                Log.d(Constantes.ETIQUETA_LOG, "DETENGO SERVICIO nueva forma con stopService desde Activity")
                /*TODO OBSERVACIÓN: SI DETENGO EL SERVICIO CON UN INTENT, DENTRO DE ESE SERVICIO
                SE EJECUTA EL STOPSELF Y DESPUÉS, POR EL CICLO DE VIDA DEL SERIVICIO, EL ONDESTROY
                ESTO PROVOCA PARAR DOS VECES EL MEDIA PLAYER Y FALLA*/
                /*
                val stopIntent: Intent = Intent(this@PlayActivity, PlayService::class.java)
                stopIntent.setAction(Constantes.STOPFOREGROUND_ACTION)
                startService(stopIntent)*/
            }
        })
    }
}
