package edu.adf.profe.mapa

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresPermission
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.adf.profe.Constantes
import edu.adf.profe.R
import edu.adf.profe.databinding.ActivityMapsBinding

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var locationManager: LocationManager //SERVICIO PARA LA UBICACIÓN (GPS, RED MÓVIL, WIFI)
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient //Objeto que realmente averigua mi ubicación
    private lateinit var locationRequest: LocationRequest //Petición al objeto anterior
    private lateinit var locationCallback: LocationCallback // función a la vuelta de la petición de ubicación
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    val laucherGpsActivation = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        {
            if (gpsActivado())
            {
                accederALaUbicacion()
            } else {
                Log.d(Constantes.ETIQUETA_LOG, "GPS DESACTIVADO")
                Toast.makeText(this, "GPS DESACTIVADO - SIN ACCESO A LA UBICACIÓN", Toast.LENGTH_LONG).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        this.locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 646)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        //SOLICITAMOS PERMISOS DE UBICACIÓN
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 646)
    }

    fun gpsActivado (): Boolean
    {
        var gpsActivo = false

            gpsActivo = this.locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        return gpsActivo
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
        deviceId: Int
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Log.d(Constantes.ETIQUETA_LOG, "Con permisos para la ubicación")
            if (gpsActivado())
            {
                accederALaUbicacion()
            } else {
                solicitarActivacion()
            }
                //accedo a la ubicación
            //else
                //solicitar la activación del GPS
        } else {
            Log.d(Constantes.ETIQUETA_LOG, "SIN permisos para la ubicación")
            Toast.makeText(this, "SIN ACCESO A LA UBICACIÓN", Toast.LENGTH_LONG).show()
        }

    }

    private fun accederALaUbicacion() {
        this.fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        this.locationRequest = LocationRequest.create()
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        this.locationRequest.setInterval(5000)
        this.locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                if (null != locationResult) {
                    var ultimaubicacion = locationResult.lastLocation
                    Log.d(Constantes.ETIQUETA_LOG, "Ultima ubicación = $ultimaubicacion")
                    this@MapsActivity.fusedLocationProviderClient.removeLocationUpdates(
                        locationCallback
                    )
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )

        }
    }

     fun solicitarActivacion() {
        val intentActivacionGSP = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        laucherGpsActivation.launch(intentActivacionGSP)
    }

}