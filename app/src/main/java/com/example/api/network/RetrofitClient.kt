package com.example.api.network

import com.example.api.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

// 1. Las rutas de tu Spring Boot en Render
interface ApiService {
    @GET("/api/jugadores/equipo/{idEquipo}")
    suspend fun getJugadoresPorEquipo(@Path("idEquipo") idEquipo: Long): List<Jugador>

    @GET("/api/entrenadores")
    suspend fun getEntrenadores(): List<Entrenador>

    @GET("/api/partidos")
    suspend fun getPartidos(): List<Partido>

    @GET("/api/estadisticas")
    suspend fun getEstadisticas(): List<EstadisticaJugador>

    @POST("/api/jugadores")
    suspend fun agregarJugador(@Body jugador: Jugador): Jugador

    @GET("/api/equipos")
    suspend fun getEquipos(): List<Equipo>

    @retrofit2.http.PUT("/api/jugadores/{id}")
    suspend fun actualizarJugador(
        @retrofit2.http.Path("id") idJugador: Long,
        @retrofit2.http.Body jugador: Jugador
    ): Jugador

    @retrofit2.http.PUT("/api/entrenadores/{id}")
    suspend fun actualizarEntrenador(
        @retrofit2.http.Path("id") idEntrenador: Long,
        @retrofit2.http.Body entrenador: Entrenador
    ): Entrenador

    @retrofit2.http.DELETE("/api/jugadores/{id}")
    suspend fun eliminarJugador(@retrofit2.http.Path("id") id: Long): retrofit2.Response<Unit>
}

// 2. Conexión a tu servidor en la nube
object RetrofitClient {

    // ¡NUEVA URL DE PRODUCCIÓN!
    private const val BASE_URL = "https://backendapifutbol.onrender.com"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}