package com.example.api.model

data class ResultadoPartido(
    val idPartido: Long,
    val fecha: String,
    val estadio: String,
    val equipoLocal: String,
    val equipoVisita: String,
    val golesLocal: Int,
    val golesVisita: Int
)