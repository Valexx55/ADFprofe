package edu.adf.profe

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import edu.adf.profe.lista.ListaUsuariosActivity
import edu.adf.profe.perros.PerrosActivity
import edu.adf.profe.productos.ListaProductosActivity

/**
 * ESTA ES LA ACTIVIDAD DE INICIO
 * DESDE AQUÍ, LANZAMOS EL RESTO DE ACTIVIDDES
 * EN UN FUTURO, PONDREMOS UN MENÚ HAMBURGUESA / LATERAL
 * DE MOMENTO, LO HACEMOS CON INTENTS
 *
 * //TODO TERMINAR PRENSA APP X
 * //TODO INFLAR X
 * //TODO SERIALIZABLE VS PARCELABLE X
 * //TODO hacer que el Usuario pueda seleccionar una FOTo y que se visualice en el IMAGEVIEW
 * //TODO RECYCLERVIEW - listas  LISTVIEW no X
 * //TODO HTTP API RETROFIT - PREVIO CORUTINAS KT - COLECCIONES -KT - git hub
 * //TODO FRAGMENTS - VIEWPAGER - TABS
 * //TODO NOTIFICACIONES - PENDING INTENT
 * //TODO FIREBASE
 * //TODO PERMISOS PELIGROSOS
 * //TODO CÁMARA FOTOS / VIDEO
 * //TODO GPS Y MAPAS // bLUETHOHT¿¿ // NFC dni??
 * //TODO SERVICIOS DEL SISTEMA (DOWNLOAD MANAGER, ALARM MANAGER)
 * //TODO SERVICIOS PROPIOS??
 * //TODO RECIEVERS
 * //TODO PROVIDERS
 * //TODO SQLITE - ROOM
 * //TODO LIVE DATA?
 * //TODO apuntes JETPCK COMPOSE Y MONETIZACIÓN, DISEÑO Y SEGURIDAD
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
        //this.navigationView.setNavigationItemSelectedListener(this)

       // intentCompartir()
        val fichero = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        val saltarVideo: Boolean =  fichero.getBoolean("SALTAR_VIDEO", false)
        if (!saltarVideo)//==false
        {
            val intentvideo = Intent(this, VideoActivity::class.java)
            startActivity(intentvideo)
        }



        this.navigationView.setNavigationItemSelectedListener (fun (item: MenuItem): Boolean {
            Log.d(Constantes.ETIQUETA_LOG, "Opción ${item.order} seleccionada")

            var intent:Intent = when(item.order) {
                2 -> Intent(this, AdivinaNumeroActivity::class.java)
                3 -> Intent(this, ImcActivity::class.java)
                4 -> Intent(this, CuadrosActivity::class.java)
                5 -> Intent(this, SumaActivity::class.java)
                6 -> Intent(this, BusquedaActivity::class.java)
                7 -> {
                    val intentImplicito = Intent(Intent.ACTION_VIEW, "https://adf-formacion.es/".toUri())//intent implicito
                    Intent.createChooser(intentImplicito, "Elige APP para ver ADF WEB")
                    //Intent(this, WebViewActivity::class.java)//intent explícito
                }
                8 -> Intent(this, SpinnerActivity::class.java)
                9 -> Intent(this, FormularioActivity::class.java)
                10 -> Intent(this, Ejercicio1VacasActivity::class.java)
                11 -> Intent(this, PrensaActivity::class.java)
                12 -> Intent(this, InflarActivity::class.java)
                13 -> Intent(this, ListaUsuariosActivity::class.java)
                14 -> Intent(this, ListaProductosActivity::class.java)
                15 -> Intent(this, PerrosActivity::class.java)
                else /*1*/ -> Intent(this, VersionActivity::class.java)

            }

            startActivity(intent)//voy a otra pantalla

            this.drawerLayout.closeDrawers()
            this.menuVisible = false
            return true //en una lambda, no hace poner return
        })
        //con función lambda
       /* this.navigationView.setNavigationItemSelectedListener{
            Log.d(Constantes.ETIQUETA_LOG, "Opción ${it.order} seleccionada")

            var intent:Intent = when(it.order) {
                2 -> Intent(this, AdivinaNumeroActivity::class.java)
                3 -> Intent(this, ImcActivity::class.java)
                4 -> Intent(this, CuadrosActivity::class.java)
                5 -> Intent(this, SumaActivity::class.java)
                else /*1*/ -> Intent(this, VersionActivity::class.java)

            }

            startActivity(intent)//voy a otra pantalla

            this.drawerLayout.closeDrawers()
            this.menuVisible = false
            true //en una lambda, no hace poner return
        }*/

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)//dibuja el incono de menú sólo la flecha
        this.supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_menu_24)//le digo que me dibuje la hamburguesa

        //VAMOS A LANZAR LA ACTIVIDAD IMC
        //val intent = Intent(this, ImcActivity::class.java)
        //startActivity(intent)=*/

    }

/*
    fun intentCompartir()
    {
        val intentEnviarTexto = Intent(Intent.ACTION_SEND)//ENVIAR
        intentEnviarTexto.type="text/plain"//TIPO MIME  de qué tipo es la información -- "extensión"
        intentEnviarTexto.putExtra(Intent.EXTRA_TEXT, "Hola desde Android :)")
        startActivity(Intent.createChooser(intentEnviarTexto, "Enviar mensaje con ..."))
    }*/

    //este métod o se invoca al tocar la hamburguesa
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            android.R.id.home -> {
                Log.d(Constantes.ETIQUETA_LOG, "Botón Hamburguesa tocado")
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
        Log.d(Constantes.ETIQUETA_LOG, "Opción ${item.order} seleccionada")

        var intent:Intent = when(item.order) {
            2 -> Intent(this, AdivinaNumeroActivity::class.java)
            3 -> Intent(this, ImcActivity::class.java)
            4 -> Intent(this, CuadrosActivity::class.java)
            5 -> Intent(this, SumaActivity::class.java)
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