package edu.adf.profe

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d("MIAPP", "Opción ${item.order} seleccionada")
        this.drawerLayout.closeDrawers()
        this.menuVisible = false
        return true
    }

}