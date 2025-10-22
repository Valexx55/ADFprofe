package edu.adf.profe

import android.Manifest
import android.annotation.TargetApi
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.net.toUri
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.firebase.messaging.FirebaseMessaging
import edu.adf.profe.alarma.AjusteAlarmaActivity
import edu.adf.profe.alarma.GestorAlarma
import edu.adf.profe.animaciones.AndroidPequeActivity
import edu.adf.profe.animaciones.AnimationActivity
import edu.adf.profe.animaciones.RippleYDesaparecerActivity
import edu.adf.profe.authfirebase.MenuAuthActivity
import edu.adf.profe.basedatos.BaseDatosActivity
import edu.adf.profe.biometrico.BioActivity
import edu.adf.profe.canciones.BusquedaCancionesActivity
import edu.adf.profe.contactos.SeleccionContactoActivity
import edu.adf.profe.contactos.SeleccionContactoPermisosActivity
import edu.adf.profe.descargarcanciones.DescargarCancionActivity
import edu.adf.profe.fechayhora.SeleccionFechaYHoraActivity
import edu.adf.profe.foto.FotoActivity
import edu.adf.profe.googleauth.GoogleAuthActivity
import edu.adf.profe.lista.ListaUsuariosActivity
import edu.adf.profe.mapa.MapsActivity
import edu.adf.profe.perros.PerrosActivity
import edu.adf.profe.productos.ListaProductosActivity
import edu.adf.profe.realtimedatabase.InsertarClientesFirebaseActivity
import edu.adf.profe.servicios.PlayActivity
import edu.adf.profe.tabs.TabsActivity
import edu.adf.profe.util.LogUtil
import edu.adf.profe.worker.MiTareaProgramada
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


/**
 * ESTA ES LA ACTIVIDAD DE INICIO
 * DESDE AQUÍ, LANZAMOS EL RESTO DE ACTIVIDDES
 * EN UN FUTURO, PONDREMOS UN MENÚ HAMBURGUESA / LATERAL
 * DE MOMENTO, LO HACEMOS CON INTENTS
 *




 * //TODO bLUETHOHT¿¿ // NFC dni??
 * //TODO SERVICIOS PROPIOS intent service / binded
 * //TODO SQLITE - ROOM -->  RELACIÓN N:m?
 * //FLOW  vs LiveData : Flow dentro de corrutinas y sólo mejora en 2 casos: si tienes que hacer operaciones sobre el flujo (streams/transformaciones) o flujo constante de datos
 * //TODO apuntes JETPCK COMPOSE Y MONETIZACIÓN, DISEÑO Y SEGURIDAD
 * //TODO firma y PUBLICAR APPS
 * // TODO proyecto API MAPA no de google con consulta al API de clima en varios módulos
 * // TODO VISTAS JETPACK COMPOSE
 * //FIXME revisar la versión noche en dispositivos 31 o más
 * // TODO transiones / animaciones
 * // TODO APp shortcuts
 * //
 *


 */
class MainMenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var drawerLayout: DrawerLayout
    lateinit var navigationView: NavigationView
    var menuVisible: Boolean = false
    var launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        Log.d(Constantes.ETIQUETA_LOG, "Volviendo de Ajustes Autonicio")
        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        //ficherop.edit().putBoolean("INICIO_AUTO", true).commit()
        //ponemos alarma a true la primera vez
        ficherop.edit(true) {
            putBoolean("INICIO_AUTO", true)
            putBoolean("ALARMA", false)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) // Actualizamos el tema al tema normal (eliminamos el usado para la splash screen en versiones anteriores)
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.S)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            Log.d(Constantes.ETIQUETA_LOG, "PONIENDO TEMA NOCHE")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu2)




        retardoAntiguo()
        animacionSalidaSplash()


        val ficherop = getSharedPreferences("ajustes", MODE_PRIVATE)
        val inicio_auto = ficherop.getBoolean("INICIO_AUTO", false)
        if (!inicio_auto) {
            //PRIMERA VEZ
            solicitarInicioAutomatico()
            askNotificationPermission() //por notificaciones FIREBASE
        }

        this.drawerLayout = findViewById<DrawerLayout>(R.id.drawer)
        this.navigationView = findViewById<NavigationView>(R.id.navigationView)

        //en esta actividad (this) escuchamos la selección sobre el menú Navigation
        //this.navigationView.setNavigationItemSelectedListener(this)

        // intentCompartir()
        val fichero = getSharedPreferences(Constantes.FICHERO_PREFERENCIAS_INICIO, MODE_PRIVATE)
        val saltarVideo: Boolean = fichero.getBoolean("SALTAR_VIDEO", false)
        if (!saltarVideo)//==false
        {
            val intentvideo = Intent(this, VideoActivity::class.java)
            startActivity(intentvideo)
        }
        // mostrarAPPSinstaladas()
        gestionarPermisosNotis()
        // lanzarAlarma ()
        lanzarWorkManager()


        //Log.d(Constantes.ETIQUETA_LOG, " GOOGLE SERVICE DISPONIBLES = ${GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)}")


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(
                    Constantes.ETIQUETA_LOG,
                    "Fetching FCM registration token failed",
                    task.exception
                )
                return@OnCompleteListener
            } else {
                // Get new FCM registration token
                val token = task.result

                // Log and toast
                val msg =
                    "TOKEN CREADO PARA NOTIFICACIONES = $token"// getString(R.string.msg_token_fmt, token)
                Log.d(Constantes.ETIQUETA_LOG, "${LogUtil.getLogInfo()}  $msg")
                // Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                //Log.d(Constantes.ETIQUETA_LOG, "Token registro FBCM $token")
            }


        })




        this.navigationView.setNavigationItemSelectedListener(fun(item: MenuItem): Boolean {
            Log.d(Constantes.ETIQUETA_LOG, "Opción ${item.order} seleccionada")

            var intent: Intent = when (item.order) {
                2 -> Intent(this, AdivinaNumeroActivity::class.java)
                3 -> Intent(this, ImcActivity::class.java)
                4 -> Intent(this, CuadrosActivity::class.java)
                5 -> Intent(this, SumaActivity::class.java)
                6 -> Intent(this, BusquedaActivity::class.java)
                7 -> {
                    val intentImplicito = Intent(
                        Intent.ACTION_VIEW,
                        "https://adf-formacion.es/".toUri()
                    )//intent implicito
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
                16 -> Intent(this, TabsActivity::class.java)
                17 -> Intent(this, BusquedaCancionesActivity::class.java)
                18 -> Intent(this, SeleccionContactoActivity::class.java)
                19 -> Intent(this, SeleccionContactoPermisosActivity::class.java)
                20 -> Intent(this, FotoActivity::class.java)
                21 -> Intent(this, DescargarCancionActivity::class.java)
                22 -> Intent(this, BioActivity::class.java)
                23 -> Intent(this, MapsActivity::class.java)
                24 -> Intent(this, PlayActivity::class.java)
                25 -> Intent(this, AjusteAlarmaActivity::class.java)
                26 -> Intent(this, SeleccionFechaYHoraActivity::class.java)
                27 -> Intent(this, BaseDatosActivity::class.java)
                28 -> Intent(this, MenuAuthActivity::class.java)
                29 -> Intent(this, InsertarClientesFirebaseActivity::class.java)
                30 -> Intent(this, GoogleAuthActivity::class.java)
                31 -> Intent(this, RippleYDesaparecerActivity::class.java)
                32 -> Intent(this, AndroidPequeActivity::class.java)
                33 -> Intent(this, AnimationActivity::class.java)

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
        //dibujamos con fuente iconográfica
        val fuente = Typeface.createFromAsset(assets, "fuentepatas.ttf")
        val mensaje = findViewById<TextView>(R.id.logopatas)
        mensaje.typeface = fuente
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
        when (item.itemId) {
            android.R.id.home -> {
                Log.d(Constantes.ETIQUETA_LOG, "Botón Hamburguesa tocado")
                if (this.menuVisible) {
                    //cerrar
                    this.drawerLayout.closeDrawers()
                    this.menuVisible = false
                } else {
                    this.drawerLayout.openDrawer(GravityCompat.START)
                    this.menuVisible = true
                }
                // this.menuVisible = !this.menuVisible
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        Log.d(Constantes.ETIQUETA_LOG, "Opción ${item.order} seleccionada")

        var intent: Intent = when (item.order) {
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

    private fun solicitarInicioAutomatico() {
        val manufacturer = Build.MANUFACTURER
        try {
            val intent = Intent()
            if ("xiaomi".equals(manufacturer, ignoreCase = true)) {
                intent.setComponent(
                    ComponentName(
                        "com.miui.securitycenter",
                        "com.miui.permcenter.autostart.AutoStartManagementActivity"
                    )
                )
            }

            launcher.launch(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun mostrarAPPSinstaladas() {
        val packageManager = packageManager
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)

        Log.d(Constantes.ETIQUETA_LOG, "hay ${apps.size} aplicaciones")
        /*for (app in apps) {
            Log.d("AppInfo", "Package: ${app.packageName}, Label: ${packageManager.getApplicationLabel(app)}")
        }*/
        //ordeno por paquete
        val appOrdenadas = apps.sortedBy { it.packageName }
        appOrdenadas.forEach {
            Log.d(
                "AppInfo",
                "Package: ${it.packageName}, Label: ${packageManager.getApplicationLabel(it)}"
            )
            // Log.d("AppInfo", "$it")
        }

    }

    fun gestionarPermisosNotis() {
        val areNotificationsEnabled = NotificationManagerCompat.from(this).areNotificationsEnabled()

        if (!areNotificationsEnabled) {
            // Mostrar un diálogo al usuario explicando por qué necesita habilitar las notificaciones
            mostrarDialogoActivarNotis()
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "Notis desactivadas")
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun mostrarDialogoActivarNotis() {
        var dialogo = AlertDialog.Builder(this)
            .setTitle("AVISO NOTIFICACIONES") //i18n
            //.setTitle("AVISO")
            .setMessage("Para que la app funcione, debe ir a ajustes y activar las notificaciones")
            //.setMessage("¿Desea Salir?")
            .setIcon(R.drawable.imagen_derrota)
            .setPositiveButton("IR") { dialogo, opcion ->
                Log.d(Constantes.ETIQUETA_LOG, "Opción positiva salir =  $opcion")
                val intent = Intent().apply {
                    action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
                    putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                }
                startActivity(intent)

            }
            .setNegativeButton("CANCELAR") { dialogo: DialogInterface, opcion: Int ->
                Log.d(Constantes.ETIQUETA_LOG, "Opción negativa  =  $opcion")
                dialogo.dismiss()
            }


        dialogo.show()//lo muestro
    }

    fun lanzarAlarma() {
        GestorAlarma.programarAlarma(this)
    }

    fun lanzarWorkManager() {
        //definimos restricciones
        val constraints = Constraints.Builder()
            //.setRequiredNetworkType(NetworkType.UNMETERED) // solo Wi-Fi
            //.setRequiresBatteryNotLow(true)               // no ejecutar con batería baja
            //.setRequiresCharging(true)                    // solo cuando esté cargando
            .build()

        //pasamos datos de entrada
        val inputData = workDataOf("USER_ID" to "12345")

        //creamos el trabajo periódico (la petición) con los datos y restricciones anteriores
        val periodicWorkRequest = PeriodicWorkRequestBuilder<MiTareaProgramada>(
            15, TimeUnit.MINUTES // Periodicidad mínima: 15 minutos
        )
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()

        //encolamos la petición
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "MiTareaProgramada",                       // Nombre único
                ExistingPeriodicWorkPolicy.KEEP,        // No reemplazar si ya existe
                periodicWorkRequest
            )

        val tiempo = System.currentTimeMillis() + (60 * 1000 * 15)//(30*1000)//15 minutos
        val dateformatter = SimpleDateFormat("E dd/MM/yyyy ' a las ' hh:mm:ss")
        val mensaje = dateformatter.format(tiempo)
        Log.d(Constantes.ETIQUETA_LOG, "TAREA PROGRAMADA PARA $mensaje")
        Toast.makeText(this, "TAREA PROGRAMADA para $mensaje", Toast.LENGTH_LONG).show()

    }

    /**
     * parece QUE NO se ejecutan las taresas programadas tras el reionicio del móvil
     * se puedo probar esto:
     *
     * tener mi clase application, con este conetenido, registrada en el manifest
     *
     * class MyApp : Application(), Configuration.Provider {
     *     override fun getWorkManagerConfiguration(): Configuration {
     *         return Configuration.Builder()
     *             .setMinimumLoggingLevel(Log.DEBUG)
     *             .build()
     *     }
     * }
     *
     * <application
     *     android:name=".MyApp"
     *     ...>
     * </application>
     *
     */


    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            Log.d(Constantes.ETIQUETA_LOG, "Permisos notificaciones concedidos")
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "Permisos notificaciones NO concedidos")
        }
    }

    /**
     * Método para preguntar por notificaciones (para firebase) a partir de la versión 13 Tiramisú
     */
    private fun askNotificationPermission() {
        // This is only necessary for API level >= 33 (TIRAMISU)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    /**
    Por defecto, cuando ya se ha dibujado la Actividad Principal, la Splash Screen
    desaparece. Sin emabargo, al programar esta función Predraw no se pinta ningún
    fotograma, hasta que no esta función no devuelta true. Por ejemplo
    en este caso, estamos causando un retardo de 6 segundos y hasta que no acabe
    la actividad no empieza a pintarse y mientras, se ve sólo la Splash Screen
     */
    fun retardoAntiguo() {
        // Set up an OnPreDrawListener to the root view.
        //OJO android.R.id.content apunta al FrameLayout que contiene toda la interfaz de tu Activity.
        //Ese content existe siempre, todos nuestros layouts montan en este Frame y sigue estando en JetPack Compose
        val content = findViewById<View>(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    Thread.sleep(50)
                    return if (true) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            })
    }

    fun retardoModerno() {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition {
            Thread.sleep(5000)
            true
        }
    }

    /**
     * La salidad de la SplashScreen, puede ser animada. De modo, que podemos
     * definir un listener al finalizar su tiempo y cargar una animación
     * como ésta
     */
    fun animacionSalidaSplash() {
        //sólo para versiones anteriores
        //también podría obtener la instancia con val splashScreen = installSplashScreen()
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                // Create your custom animation.
                val slideUp = ObjectAnimator.ofFloat(
                    splashScreenView,
                    View.TRANSLATION_X,
                    0f,
                    -splashScreenView.width.toFloat()
                )
                slideUp.interpolator = AnticipateInterpolator()
                slideUp.duration = 20000L

                // Call SplashScreenView.remove at the end of your custom animation.
                slideUp.doOnEnd { splashScreenView.remove() }

                // Run your animation.
                slideUp.start()
            }
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            splashScreen.setOnExitAnimationListener { splashScreenView ->
                splashScreenView.iconView!!.animate()
                    .alpha(0f)
                    .setDuration(3000L)
                    .withEndAction {
                        splashScreenView.remove()
                    }
            }
        }
    }
}