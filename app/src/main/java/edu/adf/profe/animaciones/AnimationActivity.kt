package edu.adf.profe.animaciones

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import edu.adf.profe.Constantes
import edu.adf.profe.R

class AnimationActivity : AppCompatActivity(), AnimationListener {
    lateinit var imange1:ImageView
    lateinit var imange2:ImageView
    lateinit var layoutPadre:LinearLayout
    lateinit var animacion:Animation
    var stopAnimacion = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)
        this.imange1 = findViewById<ImageView>(R.id.imagen1anim)
        this.imange2 = findViewById<ImageView>(R.id.imagen2anim)
        this.layoutPadre = findViewById<LinearLayout>(R.id.layout_padre)

        this.imange1.setImageResource(R.drawable.emoticono_risa)
        this.imange2.setImageResource(R.drawable.emoticono_risa)

        this.animacion = AnimationUtils.loadAnimation(this, R.anim.animacion)
        this.animacion.reset()
        //this.layoutPadre.startAnimation(this.animacion)
        this.layoutPadre.animation = this.animacion
        this.layoutPadre.startLayoutAnimation()
        //this.layoutPadre.startAnimation(this.animacion)
        //this.layoutPadre.startAnimation(this.animacion)
        animacion.setAnimationListener(this)
    }

    override fun onAnimationStart(animation: Animation?) {
        Log.d(Constantes.ETIQUETA_LOG, "onAnimationStart")
    }

    override fun onAnimationEnd(animation: Animation?) {
        Log.d(Constantes.ETIQUETA_LOG, "onAnimationEnd")
        if (!stopAnimacion)
        {
            this.imange1.setImageResource(R.mipmap.ic_launcher)
            this.imange2.setImageResource(R.mipmap.ic_launcher_round)

            this.layoutPadre.startAnimation(animation)
        }

    }

    override fun onAnimationRepeat(animation: Animation?) {
        Log.d(Constantes.ETIQUETA_LOG, "onAnimationRepeat")
    }

    fun toque (view: View)
    {
        Log.d(Constantes.ETIQUETA_LOG, "imagen tocada...detengo animación")
        this.animacion.cancel()
        this.stopAnimacion = true
    }
}