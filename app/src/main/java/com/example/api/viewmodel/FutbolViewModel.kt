package com.example.api.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.api.Repository.FutbolRepository
import com.example.api.model.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

enum class ApiState { CARGANDO, EXITO, ERROR }

class FutbolViewModel : ViewModel() {

    private val repository = FutbolRepository()

    private val _apiState = MutableStateFlow(ApiState.CARGANDO)
    val apiState = _apiState.asStateFlow()

    private val _jugadores = MutableStateFlow<List<Jugador>>(emptyList())
    val jugadores = _jugadores.asStateFlow()

    private val _partidos = MutableStateFlow<List<Partido>>(emptyList())
    val partidos = _partidos.asStateFlow()

    private val _estadisticas = MutableStateFlow<List<EstadisticaJugador>>(emptyList())
    val estadisticas = _estadisticas.asStateFlow()

    private val _equipos = MutableStateFlow<List<Equipo>>(emptyList())
    val equipos = _equipos.asStateFlow()

    private val _entrenadores = MutableStateFlow<List<Entrenador>>(emptyList())
    val entrenadores = _entrenadores.asStateFlow()

    init {
        cargarTodoDesdeBackend()
    }

    fun cargarTodoDesdeBackend() {
        _apiState.value = ApiState.CARGANDO

        viewModelScope.launch {
            try {
                withTimeout(10000) {
                    _equipos.value = repository.obtenerEquipos()
                    _entrenadores.value = repository.obtenerEntrenadores()

                    val idEquipoRealMadrid = 1L
                    _jugadores.value = repository.obtenerJugadores(idEquipoRealMadrid)
                    _partidos.value = repository.obtenerPartidos()
                    _estadisticas.value = repository.obtenerEstadisticas()

                    _apiState.value = ApiState.EXITO
                }
            } catch (e: TimeoutCancellationException) {
                _apiState.value = ApiState.ERROR
            } catch (e: Exception) {
                _apiState.value = ApiState.ERROR
            }
        }
    }

    fun getCondicionJugador(idJugador: Long): Int {
        return when (idJugador) {
            1L -> 100; 2L -> 90; 3L -> 88; 4L -> 60; 5L -> 75; 6L -> 95
            else -> 85
        }
    }

    fun obtenerNombreEquipo(id: Long?): String {
        val equipoEncontrado = equipos.value.find { it.idEquipo == id }
        return equipoEncontrado?.nombre ?: "Desconocido"
    }


    fun ficharNuevoJugador(nombre: String, posicion: String, dorsal: Int, nacionalidad: String) {
        viewModelScope.launch {
            val nuevo = Jugador(
                idJugador = null,
                nombre = nombre,
                posicion = posicion,
                dorsal = dorsal,
                fechaNac = "2000-01-01",
                nacionalidad = nacionalidad,
                idEquipo = 1L
            )

            val exito = repository.agregarJugador(nuevo)
            if (exito) {
                cargarTodoDesdeBackend()
            }
        }
    }

    fun ejecutarTraspaso(jugador: Jugador, nuevoIdEquipo: Long) {
        viewModelScope.launch {

            val jugadorActualizado = jugador.copy(idEquipo = nuevoIdEquipo)

            val exito = repository.traspasarJugador(jugadorActualizado)
            if (exito) {

                cargarTodoDesdeBackend()
            }
        }
    }

    fun intercambiarEntrenadores(miEntrenador: Entrenador, entrenadorRival: Entrenador) {
        viewModelScope.launch {
            // Intercambiamos los IDs de los equipos
            val miEntrenadorActualizado = miEntrenador.copy(idEquipo = entrenadorRival.idEquipo)
            val rivalActualizado = entrenadorRival.copy(idEquipo = 1L) // 1L es el Real Madrid

            // Ejecutamos los dos PUT al mismo tiempo
            val exito1 = repository.actualizarEntrenador(miEntrenadorActualizado)
            val exito2 = repository.actualizarEntrenador(rivalActualizado)

            if (exito1 && exito2) {

                cargarTodoDesdeBackend()
            }
        }
    }

    fun eliminarJugador(idJugador: Long) {
        viewModelScope.launch {
            val exito = repository.eliminarJugador(idJugador)
            if (exito) {
                cargarTodoDesdeBackend()
            }
        }
    }
}