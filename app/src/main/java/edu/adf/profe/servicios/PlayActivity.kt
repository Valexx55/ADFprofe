package edu.adf.profe.servicios

import android.content.Intent
import android.os.Bundle
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

        stopButton!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val stopIntent: Intent = Intent(this@PlayActivity, PlayService::class.java)
                stopIntent.setAction(Constantes.STOPFOREGROUND_ACTION)
                startService(stopIntent)
            }
        })
    }
}
