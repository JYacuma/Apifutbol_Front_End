package com.example.api.model

data class Entrenador(
    val idEntrenador: Long,
    val nombre: String,
    val especialidad: String,
    val idEquipo: Long?
)