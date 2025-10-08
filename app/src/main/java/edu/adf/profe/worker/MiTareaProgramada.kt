package edu.adf.profe.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import edu.adf.profe.Constantes

class MiTareaProgramada(context: Context, workerParams: WorkerParameters): Worker(context, workerParams) {

    //COSAS PARA PROFUNDIZAR:
    /**
     * ALARMMANAGER VS WORKMANAGER
     *
     * A diferencia de los trabajadores regulares de WorkManager, las alarmas exactas de AlarmManager activan un dispositivo en modo Descanso. Por lo tanto, no es eficiente en términos de energía y administración de recursos. Úsalo solo para alarmas o notificaciones precisas, como eventos de calendario, no para el trabajo en segundo plano recurrente
     */
    /**
     * ELIMINAR TAREAS PROGRAMADAS
     *
     * 1) Por el id de la request
     * val request = OneTimeWorkRequestBuilder<MyWorker>().build()
     * WorkManager.getInstance(context).enqueue(request)
     *
     * // Cancelar más tarde por ID
     * WorkManager.getInstance(context).cancelWorkById(request.id)
     *
     * 2) cancelar por el nombre
     * WorkManager.getInstance(context).cancelUniqueWork("MyUniqueWork")
     *
     * 3) Para cancerlar todas las tareas de la app
     * WorkManager.getInstance(context).cancelAllWork()
     *
     * 4) USANDO UN TAG
     *
     * val request = OneTimeWorkRequestBuilder<MyWorker>()
     *     .addTag("syncTask")
     *     .build()
     * WorkManager.getInstance(context).enqueue(request)
     *
     * y luego, cancelamos por Tag
     *
     * WorkManager.getInstance(context).cancelAllWorkByTag("syncTask")
     *
     *
     *
     * PODEMOS DEVOVLER DATOS DE SALIDA CON Result.success(dATA)
     *
     * a) y a su vez, consumir ese resultado con LiveData
     *
     * val workRequest = OneTimeWorkRequestBuilder<MyWorker>().build()
     *
     * WorkManager.getInstance(context).enqueue(workRequest)
     *
     * // Observar el resultado
     * WorkManager.getInstance(context)
     *     .getWorkInfoByIdLiveData(workRequest.id)
     *     .observe(lifecycleOwner) { workInfo ->
     *         if (workInfo != null && workInfo.state == WorkInfo.State.SUCCEEDED) {
     *             val result = workInfo.outputData.getString("result_key")
     *             Log.d("WorkManager", "Resultado: $result")
     *         }
     *     }
     *
     *
     *b)
     * o con comprobaciones bloqueantes des si ha acabado
     *
     * val workInfo = WorkManager.getInstance(context)
     *     .getWorkInfoById(workRequest.id)
     *     .get() // Bloquea el hilo hasta obtener resultado
     *
     * if (workInfo.state == WorkInfo.State.SUCCEEDED) {
     *     val result = workInfo.outputData.getString("result_key")
     * }
     *
     */
    override fun doWork(): Result {
        // Obtener parámetros de entrada
        val userId = inputData.getString("USER_ID") ?: return Result.failure()

        // Aquí va tu lógica de segundo plano
        //Log.d(Constantes.ETIQUETA_LOG, "Ejecutando trabajo MiTareaProgramada: $userId")
        val timestamp = java.text.SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault()).format(java.util.Date())
        Log.d(Constantes.ETIQUETA_LOG, "[$timestamp] Ejecutando trabajo MiTareaProgramada: $userId")

       // Notificaciones.lanzarNotificacion(applicationContext)

        return Result.success()
    }
}