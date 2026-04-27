package com.example.api.Repository

import android.util.Log
import com.example.api.model.*
import com.example.api.network.RetrofitClient

class FutbolRepository {
    private val api = RetrofitClient.apiService

    suspend fun obtenerJugadores(idEquipo: Long): List<Jugador> {
        return try {
            api.getJugadoresPorEquipo(idEquipo)
        } catch (e: Exception) {
            Log.e("API_ERROR", "Fallo en Jugadores: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerEntrenadores(): List<Entrenador> {
        return try {
            api.getEntrenadores()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Fallo en Entrenadores: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerPartidos(): List<Partido> {
        return try {
            api.getPartidos()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Fallo en Partidos: ${e.message}")
            emptyList()
        }
    }

    suspend fun obtenerEstadisticas(): List<EstadisticaJugador> {
        return try {
            api.getEstadisticas()
        } catch (e: Exception) {
            Log.e("API_ERROR", "Fallo en Estadisticas: ${e.message}")
            emptyList()
        }
    }

    // ---> ESTA ES LA FUNCIÓN QUE FALTABA PARA EL POST <---
    suspend fun agregarJugador(nuevoJugador: Jugador): Boolean {
        return try {
            api.agregarJugador(nuevoJugador)
            true
        } catch (e: Exception) {
            Log.e("API_ERROR", "Error al fichar: ${e.message}")
            false
        }
    }

    suspend fun obtenerEquipos(): List<Equipo> {
        return try {
            api.getEquipos()
        } catch (e: Exception) {
            android.util.Log.e("API_ERROR", "Fallo en Equipos: ${e.message}")
            emptyList()
        }
    }

    suspend fun traspasarJugador(jugadorActualizado: Jugador): Boolean {
        return try {
            // El !! es seguro porque el jugador ya existe en la base de datos
            api.actualizarJugador(jugadorActualizado.idJugador!!, jugadorActualizado)
            true
        } catch (e: Exception) {
            android.util.Log.e("API_ERROR", "Error al traspasar: ${e.message}")
            false
        }
    }

    suspend fun actualizarEntrenador(entrenadorActualizado: Entrenador): Boolean {
        return try {
            api.actualizarEntrenador(entrenadorActualizado.idEntrenador!!, entrenadorActualizado)
            true
        } catch (e: Exception) {
            android.util.Log.e("API_ERROR", "Error al actualizar DT: ${e.message}")
            false
        }
    }

    suspend fun eliminarJugador(idJugador: Long): Boolean {
        return try {
            val response = api.eliminarJugador(idJugador)
            response.isSuccessful
        } catch (e: Exception) {
            android.util.Log.e("API_ERROR", "Error al eliminar jugador: ${e.message}")
            false
        }
    }
}