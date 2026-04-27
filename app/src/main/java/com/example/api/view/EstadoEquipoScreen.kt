package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // ESTE IMPORT ES EL QUE ARREGLA EL ERROR DE ABAJO
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun EstadoEquipoScreen(navController: NavController, viewModel: FutbolViewModel) {
    // Traemos todos los datos que necesitamos directamente de la base
    val partidos by viewModel.partidos.collectAsState()
    val entrenadores by viewModel.entrenadores.collectAsState()
    val estadisticas by viewModel.estadisticas.collectAsState()
    val jugadores by viewModel.jugadores.collectAsState()

    // Lógica del balance
    val victorias = partidos.count { it.golesLocal > it.golesVisita && it.idEquipoLocal == 1L || it.golesVisita > it.golesLocal && it.idEquipoVisita == 1L }
    val empates = partidos.count { it.golesLocal == it.golesVisita }
    val derrotas = partidos.size - victorias - empates
    val golesFavor = partidos.sumOf { if (it.idEquipoLocal == 1L) it.golesLocal else if (it.idEquipoVisita == 1L) it.golesVisita else 0 }
    val golesContra = partidos.sumOf { if (it.idEquipoLocal == 1L) it.golesVisita else if (it.idEquipoVisita == 1L) it.golesLocal else 0 }
    val mister = entrenadores.find { it.idEquipo == 1L }

    // ¡NUEVO! Calculamos los goleadores AQUÍ MISMO para no tener que tocar el ViewModel
    val goleadoresAgrupados = estadisticas
        .groupBy { stat -> jugadores.find { it.idJugador == stat.idJugador }?.nombre ?: "Desconocido" }
        .mapValues { entry -> entry.value.sumOf { it.goles } }
        .filter { it.value >= 2 } // <-- AQUÍ ESTÁ EL MÍNIMO DE 2 GOLES
        .toList()
        .sortedByDescending { it.second }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Text("ESTADO DEL EQUIPO", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color(0xFFFFC107)) }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("BALANCE GLOBAL", fontWeight = FontWeight.Bold, color = Color(0xFFFFC107))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("V: $victorias", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                        Text("E: $empates", color = MaterialTheme.colorScheme.onSurface.copy(alpha=0.6f), fontWeight = FontWeight.Bold)
                        Text("D: $derrotas", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha=0.1f))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Goles a Favor: $golesFavor", color = MaterialTheme.colorScheme.onSurface)
                        Text("Goles en Contra: $golesContra", color = MaterialTheme.colorScheme.onSurface)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Aprobación del DT (${mister?.nombre ?: "Sin DT"}): 100%", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha=0.5f))
                }
            }
        }

        val competiciones = listOf("CHAMPIONS LEAGUE", "LALIGA EA SPORTS", "COPA DEL REY")
        competiciones.forEach { torneo ->
            item { Text(torneo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color(0xFFFFC107), modifier = Modifier.padding(top = 8.dp)) }

            if (goleadoresAgrupados.isEmpty()) {
                item { Text("Ningún jugador alcanza el mínimo de goles", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onBackground.copy(alpha=0.5f)) }
            } else {
                // AQUÍ CORREGIMOS la forma de leer la lista para que Android Studio no llore con el "(nombre, goles)"
                items(goleadoresAgrupados) { item ->
                    val nombre = item.first
                    val goles = item.second

                    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                        Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(nombre, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
                            Text("⚽ $goles", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                        }
                    }
                }
            }
        }
    }
}