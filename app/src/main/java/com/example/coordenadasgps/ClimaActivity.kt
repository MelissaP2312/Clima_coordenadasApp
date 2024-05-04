package com.example.coordenadasgps

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.bumptech.glide.Glide


class ClimaActivity : AppCompatActivity(){

    private lateinit var lugarTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var temperaturaTextView: TextView
    private lateinit var tempminTextView: TextView
    private lateinit var tempmaxTextView: TextView
    private lateinit var climaImagen : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)

        lugarTextView = findViewById(R.id.lugarTextView)
        statusTextView = findViewById(R.id.status_clima)
        temperaturaTextView = findViewById(R.id.temperatura)
        tempminTextView = findViewById(R.id.tempe_min)
        tempmaxTextView = findViewById(R.id.tempe_max)
        climaImagen = findViewById(R.id.clima_img)

        val climaInfo = intent.getStringExtra("clima_info")

        if (climaInfo != null) {
            procesarJSON(climaInfo)
        }

    }

    private fun procesarJSON(climaInfo: String) {
        try {
            val jsonObj = JSONObject(climaInfo)
            val clima = jsonObj.getJSONArray("weather").getJSONObject(0)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val viento = jsonObj.getJSONObject("wind")
            val nubes = jsonObj.getJSONObject("clouds") // Acceso a los datos de nubes
            val visibilidad = jsonObj.getInt("visibility") // Acceso a la visibilidad directamente

            val iconoCodigo = clima.getString("icon")

            // Asigna los valores de los detalles
            lugarTextView.text = jsonObj.getString("name")
            statusTextView.text = clima.getString("main")
            temperaturaTextView.text = "${main.getDouble("temp")}°C"
            tempminTextView.text = "${main.getDouble("temp_min")}°C"
            tempmaxTextView.text = "${main.getDouble("temp_max")}°C"

            // Poner datos en las tarjetas dinámicas
            val detalles = listOf(
                Triple("Presión", R.drawable.presion, main.getInt("pressure").toString() + " hPa"),
                Triple("Humedad", R.drawable.humedad, main.getInt("humidity").toString() + "%"),
                Triple("Viento", R.drawable.aire, viento.getDouble("speed").toString() + " m/s"),
                Triple("Dirección", R.drawable.direccion, viento.getDouble("deg").toString() + "°"),
                Triple("Ráfaga", R.drawable.viento, viento.getDouble("gust").toString() + " m/s"),
                Triple("Sensación térmica", R.drawable.termico, main.getDouble("feels_like").toString() + "°"),
                Triple("Salida del sol", R.drawable.sunrise, SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sys.getLong("sunrise") * 1000))),
                Triple("Puesta del sol", R.drawable.sunset, SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sys.getLong("sunset") * 1000))),
                Triple("Nubes", R.drawable.nubes, nubes.getInt("all").toString() + "%"),
                Triple("Visibilidad", R.drawable.visibilidad, visibilidad.toString() + " m")
            )

            // Se generan las tarjetitas de los detalles
            val detallesContainer = findViewById<LinearLayout>(R.id.contenedor_detalles)
            for (detalle in detalles) {
                val detalleView = layoutInflater.inflate(R.layout.etiquetas_detalles, detallesContainer, false)
                detalleView.findViewById<TextView>(R.id.detallesInfo).text = detalle.first
                detalleView.findViewById<ImageView>(R.id.pic).setImageResource(detalle.second)
                detalleView.findViewById<TextView>(R.id.expoInfoDetalle).text = detalle.third
                detallesContainer.addView(detalleView)
            }

            // Asignar imagen :)
            obtenerImagenIcono("https://openweathermap.org/img/wn/$iconoCodigo@2x.png")

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //Usa dependencia Glide para poner la imagen en el ImageView
    private fun obtenerImagenIcono(url : String){
        Glide.with(this)
            .load(url)
            .into(climaImagen)
    }
}