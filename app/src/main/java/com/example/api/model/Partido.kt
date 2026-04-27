package com.example.api.model

data class Partido(
    val idPartido: Long,
    val fecha: String,
    val estadio: String,
    val idEquipoLocal: Long?,
    val idEquipoVisita: Long?,
    val golesLocal: Int,
    val golesVisita: Int
)