package edu.adf.profe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

/**
 * ESTA ES LA ACTIVIDAD DE INICIO
 * DESDE AQUÍ, LANZAMOS EL RESTO DE ACTIVIDDES
 * EN UN FUTURO, PONDREMOS UN MENÚ HAMBURGUESA / LATERAL
 * DE MOMENTO, LO HACEMOS CON INTENTS
 */
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var menuVisible: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu2)

        this.drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        //en esta actividad (this) escuchamos la selección sobre el menú Navigation
        this.navigationView.setNavigationItemSelectedListener(this)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)//dibuja el incono de menú sólo la flecha
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_menu_24)//le digo que me dibuje la hamburguesa

        //VAMOS A LANZAR LA ACTIVIDAD IMC
        //val intent = Intent(this, ImcActivity::class.java)
        //startActivity(intent)=*/

    }



    //este método se invoca al tocar la hamburguesa
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> {
                Log.d("MIAPP", "Botón Hamburguesa tocado")
                if (this.menuVisible)
                {
                    //cerrar
                    this.drawerLayout.closeDrawers()
                   this.menuVisible=false
                } else {
                    this.drawerLayout.openDrawer(GravityCompat.START)
                   this.menuVisible=true
                }
               // this.menuVisible = !this.menuVisible
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MIAPP", "Opción ${item.order} seleccionada")

        var intent:Intent = when(item.order) {
            2 -> Intent(this, AdivinaNumeroActivity::class.java)
            3 -> Intent(this, ImcActivity::class.java)
            4 -> Intent(this, CuadrosActivity::class.java)
            else /*1*/ -> Intent(this, VersionActivity::class.java)

        }

        startActivity(intent)//voy a otra pantalla

        // más avanzando, con genéricos <>
       /* var objeto:Class<out Activity> = when(item.order) { //Class<*>
            2 ->  AdivinaNumeroActivity::class.java
            3 ->  ImcActivity::class.java
            4 -> CuadrosActivity::class.java
            else /*1*/ -> VersionActivity::class.java

        }
        val miIntent: Intent = Intent(this, objeto)
        startActivity(miIntent) */



        this.drawerLayout.closeDrawers()
        this.menuVisible = false
        return true
    }

}