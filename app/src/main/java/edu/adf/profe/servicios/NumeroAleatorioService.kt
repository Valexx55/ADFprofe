package edu.adf.profe.servicios

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import edu.adf.profe.notificaciones.Notificaciones
import edu.adf.profe.receptor.FinServicioReceiver

class NumeroAleatorioService : Service() {

    var numeroAleatorio = 0

    //se ejecuta al iniciar el servicio
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //return super.onStartCommand(intent, flags, startId)

        //programamos la escucha del final del serivicio
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        val finServicioReceiver = FinServicioReceiver()
        val intentFilter = IntentFilter("SERV_ALEATORIO_FINAL")
        //"nuestro receptor está pendiente de esa señal intent filter"
        localBroadcastManager.registerReceiver(finServicioReceiver, intentFilter)

        val notificacionSegundoPlano = Notificaciones.crearNotificacionSegundoPlano(this)
        startForeground(65, notificacionSegundoPlano)

        try{
            //simulamos que consumimos un API
            Thread.sleep(5000)
            numeroAleatorio = (Math.random()*100+1).toInt()

        }catch (e:Exception)
        {
            Log.e("MIAPP", e.message, e)
        }

        stopForeground(STOP_FOREGROUND_REMOVE)//elimina el servicio del primer plano (foregorund) y la notificación

        stopSelf()//detenemos el servicio lo paramos del tod o (si no, seguría en segundo plano)

        return START_NOT_STICKY//se ejecuta el servicio no se reinicia
    }

    override fun onBind(intent: Intent): IBinder {
        /* El sistema no llama nunca a onBind() en un started service
        onBind(Intent intent) es un método abstracto en la clase Service, y se usa para que otros componentes (como actividades o servicios) se "conecten" al servicio y se comuniquen directamente con él mediante un Binder.

        Es parte de lo que se llama un Bound Service (servicio enlazado o vinculado).*/
        TODO("Return the communication channel to the service.")
    }

    //se ejecuta cuando se para el servicio
    override fun onDestroy() {

        val intent_fin = Intent("SERV_ALEATORIO_FINAL")
        intent_fin.putExtra("NUM_ALEATORIO", this.numeroAleatorio)
        val localBroadcastManager = LocalBroadcastManager.getInstance(this)
        //lanzamos la señal, como diciendo que ha acabado nuestro servicio
        localBroadcastManager.sendBroadcast(intent_fin)

        super.onDestroy()
    }
}