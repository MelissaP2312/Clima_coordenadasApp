package com.example.coordenadasgps

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ClimaActivity : AppCompatActivity(){

    private lateinit var lugarTextView: TextView
    private lateinit var statusTextView: TextView
    private lateinit var temperaturaTextView: TextView
    private lateinit var tempminTextView: TextView
    private lateinit var tempmaxTextView: TextView
    private lateinit var sunriseTextView: TextView
    private lateinit var sunsetTextView: TextView
    private lateinit var vientoTextView: TextView
    private lateinit var presionTextView: TextView
    private lateinit var humedadTextView: TextView
    private lateinit var sensacionTextView: TextView
    private lateinit var climaImagen : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clima)

        // Inicialización de las vistas
        lugarTextView = findViewById(R.id.lugarTextView)
        statusTextView = findViewById(R.id.status_clima)
        temperaturaTextView = findViewById(R.id.temperatura)
        tempminTextView = findViewById(R.id.tempe_min)
        tempmaxTextView = findViewById(R.id.tempe_max)
        sunriseTextView = findViewById(R.id.sunrise)
        sunsetTextView = findViewById(R.id.sunset)
        vientoTextView = findViewById(R.id.viento)
        presionTextView = findViewById(R.id.presion)
        humedadTextView = findViewById(R.id.humedad)
        sensacionTextView = findViewById(R.id.sensacion)
        climaImagen = findViewById(R.id.clima_img)

        val textViewClima: TextView = findViewById(R.id.textViewClima)

        val climaInfo = intent.getStringExtra("clima_info")

        if (climaInfo != null) {

            procesarJSON(climaInfo)
        } else {

            textViewClima.text = "No se pudo obtener la información del clima."
        }

    }

    private fun procesarJSON(climaInfo: String) {
        try {
            val jsonObj = JSONObject(climaInfo)
            val clima = jsonObj.getJSONArray("weather").getJSONObject(0)
            val main = jsonObj.getJSONObject("main")
            val sys = jsonObj.getJSONObject("sys")
            val viento = jsonObj.getJSONObject("wind")
            val tiempo: Long = jsonObj.getLong("dt")
            //val iconoCodigo = clima.getString("icon")

            // Asignar valores a las vistas
            lugarTextView.text = jsonObj.getString("name")
            statusTextView.text = clima.getString("main")
            temperaturaTextView.text = "${main.getDouble("temp")}°C"
            tempminTextView.text = "${main.getDouble("temp_min")}°C"
            tempmaxTextView.text = "${main.getDouble("temp_max")}°C"
            sunriseTextView.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sys.getLong("sunrise") * 1000))
            sunsetTextView.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(sys.getLong("sunset") * 1000))
            vientoTextView.text = "${viento.getDouble("speed")} m/s"
            presionTextView.text = "${main.getInt("pressure")} hPa"
            humedadTextView.text = "${main.getInt("humidity")}%"
            sensacionTextView.text = "${main.getDouble("feels_like")}°C"

            //Asignar imagen :)
            //obtenerImagenIcono(iconoCodigo)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /*private fun obtenerImagenIcono(iconoCodigo : String){
        val url = "https://openweathermap.org/img/wn/$iconoCodigo@2x.png"
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()

        val inputStream = connection.inputStream
        val bitmap = BitmapFactory.decodeStream(inputStream)
        climaImagen.setImageBitmap(bitmap)

        inputStream.close()
        connection.disconnect()
    }*/
}