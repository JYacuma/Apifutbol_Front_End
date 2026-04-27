package com.example.api.model

data class Jugador(
    val idJugador: Long? = null, // Al poner null, Retrofit lo vuelve invisible
    val nombre: String,
    val posicion: String,
    val dorsal: Int,
    val fechaNac: String,
    val nacionalidad: String,
    val idEquipo: Long
)