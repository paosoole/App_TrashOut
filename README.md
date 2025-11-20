TrashOut - Seguimiento de Camiones de Basura

Aplicación móvil desarrollada en Kotlin + Jetpack Compose, que permite el registro, inicio de sesión y seguimiento en tiempo real de la ubicación de camiones recolectores, usando Google Maps y permisos avanzados de ubicación.

📌 Características principales
Función	Descripción
🧑‍✈️ Registro y Login de Conductores	Los conductores crean una cuenta usando un formulario almacenado localmente con Room Database.
📍 Seguimiento GPS en tiempo real	La aplicación muestra la localización actual del camión en Google Maps.
🔐 Recuperación de Contraseña por correo	Si olvidan su clave, pueden solicitar un link de recuperación enviado al correo registrado.
🔑 Permisos de ubicación avanzados	Uso de permisos FINE, COARSE y BACKGROUND para rastreo preciso del recorrido.
🗺️ Integración con Google Maps	Se inicializa Maps con API Key segura.
🏗️ Arquitectura del Proyecto

El proyecto utiliza clean architecture básica:

cl.trashout.ev2_phonetruck
│
├── domain.data
│   ├── config          # Base de datos Room (AppDatabase)
│   ├── DAO             # Acceso a datos locales
│   ├── entities        # Entidades (tablas)
│   └── repository      # Lógica de acceso a datos (UserRepository)
│
├── model               # Modelos UI/Estado
├── ui
│   ├── components      # Botones, campos, barras, diálogos reutilizables
│   ├── navigation      # Estructura de navegación
│   ├── screens         # Pantallas (Login, Registro, Reset, Mapa, etc.)
│   └── theme           # Colores, estilos
│
├── viewModel           # Lógica de presentación (MVVM)
└── TrashOut            # Clase Application global (inicializa BD y Maps)

🛠️ Tecnologías Utilizadas
Tecnología	Uso
Kotlin	Lenguaje principal
Jetpack Compose	Interfaz moderna declarativa
Google Maps API	Visualización de ubicación
Room Database	Almacenamiento de usuarios
MVVM + Repository	Arquitectura
Android Permissions API	Administración de permisos de ubicación
Coroutines/Flow	Trabajo asíncrono
📜 Permisos requeridos

El proyecto requiere permisos especiales para rastrear la ubicación:

<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />

🔑 Configuración de Google Maps API Key

Agrega tu clave en local.properties (no se sube a GitHub):

MAPS_API_KEY=TU_CLAVE_AQUI


En el AndroidManifest.xml se lee así:

<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="${MAPS_API_KEY}" />

📬 Recuperación de Contraseña

El usuario ingresa su correo registrado.

La app valida la cuenta localmente (Room).

Muestra un mensaje (AlertDialog).

Se envía un correo con un link de recuperación (simulado o real, según integración).

🔐 Seguridad

API Key oculta gracias a local.properties.

Base de datos local con Room.

Sin almacenamiento de contraseñas en texto plano.

Manejo seguro de permisos de ubicación.

Autores:
Desarrollado como proyecto académico por:
Paola Narr - Scarlet Jara
Carrera: Ingenieria Informatica
Instituto/Universidad: Instituto PRofesional Duoc UC

---------------------------------------------------------------------------------------------
Visualización y Prueba del Proyecto
🔧 Requisitos Previos

Antes de ejecutar el proyecto asegúrate de tener:

Android Studio Electric Eel o superior

SDK de Android API 24+

Kotlin configurado

Emulador o dispositivo físico con GPS habilitado

▶️ Ejecutar en Emulador Android Studio

Abrir Android Studio

Ir a Device Manager

Crear un emulador con:

Versión recomendada: Android 13 (API 33)

Google Play Services habilitado

Permisos GPS activados

Ejecutar el proyecto presionando Run ▶

📍 Simular Ubicación en Emulador

Para probar la ruta del camión:

Con el emulador abierto ir a More Options (…)

Seleccionar Location

Puedes:

Elegir una ubicación a mano

Cargar un archivo GPX/KML para simular un recorrido real

📱 Ejecutar en Dispositivo Físico

Activar Modo desarrollo

Habilitar Depuración USB

Conectar el celular al PC

Presionar Run ▶

Aceptar los permisos en el móvil

📌 Estado del Proyecto

🟡 Versión inicial funcional
🔜 Próximas características:

Visualización del mapa para ciudadanos

Chat con operadores

Notificaciones push sobre horarios de recolección
