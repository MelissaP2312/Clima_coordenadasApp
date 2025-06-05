# Clima por Coordenadas App 🌤️📍

Aplicación móvil para Android que muestra el clima actual basado en coordenadas geográficas (latitud y longitud), desarrollada en Kotlin.

## Características ✨

- Obtiene el clima actual usando coordenadas GPS
- Muestra información detallada del clima:
  - Temperatura actual (en °C)
  - Condición climática (soleado, nublado, lluvia, etc.)
  - Humedad
  - Velocidad del viento
  - Presión atmosférica
- Interfaz de usuario limpia e intuitiva
- Uso de la API de OpenWeatherMap

## Tecnologías utilizadas 🛠️

- **Lenguaje**: Kotlin
- **Arquitectura**: MVVM (Model-View-ViewModel)
- **Librerías principales**:
  - Retrofit - Para consumo de API REST
  - ViewModel y LiveData - Para manejo de datos y ciclo de vida
  - Location Services - Para obtener la ubicación del dispositivo
  - Coroutines - Para manejo de operaciones asíncronas
  - Glide - Para carga de imágenes (íconos del clima)

## Requisitos 📋

- Android Studio Arctic Fox o superior
- Dispositivo con Android 8.0 (Oreo) o superior
- Conexión a Internet
- Servicios de ubicación activados (para versión con GPS)
- API key de OpenWeatherMap (ver configuración)

## Configuración ⚙️

1. **Clona el repositorio:**
   ```bash
   git clone https://github.com/MelissaP2312/Clima_coordenadasApp.git
   ```

2. **Abre el proyecto en Android Studio**

3. **Obtén una API key gratuita de OpenWeatherMap**

4. **En el archivo MainActivity.kt agrega:**
    ```bash
   apiKey=tu_api_key_aquí
   ```
   
5. **Sincroniza el proyecto con Gradle**
   
7. **Ejecuta la aplicación en un emulador o dispositivo físico**


   
