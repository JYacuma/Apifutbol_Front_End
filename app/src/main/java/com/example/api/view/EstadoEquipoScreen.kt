package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun EstadoEquipoScreen(navController: NavController, viewModel: FutbolViewModel) {
    val partidos by viewModel.partidos.collectAsState()
    val estadisticas by viewModel.estadisticas.collectAsState()
    val jugadores by viewModel.jugadores.collectAsState()

    val victorias = partidos.count { it.golesLocal > it.golesVisita && it.idEquipoLocal == 1L || it.golesVisita > it.golesLocal && it.idEquipoVisita == 1L }
    val golesFavor = partidos.sumOf { if (it.idEquipoLocal == 1L) it.golesLocal else if (it.idEquipoVisita == 1L) it.golesVisita else 0 }

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)), contentPadding = PaddingValues(16.dp)) {
        item {
            Text("ESTADO DEL EQUIPO", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color(0xFFFFC107))
            Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("BALANCE TEMPORADA", color = Color(0xFFFFC107), fontWeight = FontWeight.Bold)
                    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Victorias: $victorias", color = Color.White)
                        Text("Goles Totales: $golesFavor", color = Color.White)
                    }
                }
            }
        }

        val torneos = listOf("CHAMPIONS LEAGUE", "LALIGA EA SPORTS", "COPA DEL REY")
        torneos.forEach { torneo ->
            item { Text(torneo, color = Color(0xFFFFC107), fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp)) }

            val goleadores = estadisticas
                .groupBy { s -> jugadores.find { it.idJugador == s.idJugador }?.nombre ?: "Anónimo" }
                .mapValues { it.value.sumOf { g -> g.goles } }
                .filter { it.value >= 2 }
                .toList().sortedByDescending { it.second }

            if (goleadores.isEmpty()) {
                item { Text("Sin datos de goleadores", color = Color.Gray, style = MaterialTheme.typography.bodySmall) }
            } else {
                items(goleadores) { (nombre, goles) ->
                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(nombre, color = Color.White)
                            Text("⚽ $goles", color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}