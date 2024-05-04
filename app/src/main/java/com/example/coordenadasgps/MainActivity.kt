package com.example.coordenadasgps

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity(), LocationListener {

    private lateinit var locationManager: LocationManager
    private lateinit var ultimaUbicacion: Location
    private var muestraMapa = false

    private val locationPermissionCode = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "KotlinApp"

        val button2: Button = findViewById(R.id.getMapas)
        button2.setOnClickListener {
            muestraMapa = true
            getLocation()
        }

        val button: Button = findViewById(R.id.getClima)
        button.setOnClickListener {
            muestraMapa = false
            getLocation()
        }
    }

    private fun getLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }
        locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null)
    }

    override fun onLocationChanged(location: Location) {
        ultimaUbicacion = location
        if (::ultimaUbicacion.isInitialized) {
            if (ultimaUbicacion != null) {
                if (muestraMapa) {
                    verMapa(ultimaUbicacion)
                } else {
                    obtenerClima(ultimaUbicacion)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation()
            } else {
                Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun verMapa(location: Location) {
        val intent = Intent(this, MapsActivity::class.java)
        intent.putExtra("latitud", location.latitude)
        intent.putExtra("longitud", location.longitude)
        startActivity(intent)
    }

    private fun obtenerClima(location: Location) {
        val latitud = location.latitude
        val longitud = location.longitude
        val apiKey = "225dcc1b29ee5be3b2973d51bf7d612e"

        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$latitud&lon=$longitud&lang=es&appid=$apiKey&units=metric"

        descifrarClima().execute(url)
    }
    private inner class descifrarClima : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            var response: String
            try {
                val url = URL(params[0])
                val conn = url.openConnection() as HttpURLConnection
                conn.requestMethod = "GET"
                conn.connect()

                val stream = conn.inputStream
                val reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()
                var line: String?

                while (reader.readLine().also { line = it } != null) {
                    buffer.append(line + "\n")
                }

                response = buffer.toString()
            } catch (e: Exception) {
                e.printStackTrace()
                response = ""
            }

            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val intent2 = Intent(this@MainActivity, ClimaActivity::class.java)
            intent2.putExtra("clima_info", result)
            startActivity(intent2)
        }
    }
}
