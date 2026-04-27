package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun EstadoEquipoScreen(navController: NavController, viewModel: FutbolViewModel) {
    val partidos by viewModel.partidos.collectAsState()
    val entrenadores by viewModel.entrenadores.collectAsState()

    // CORREGIDO: Usando idEquipoLocal e idEquipoVisita
    val victorias = partidos.count { it.golesLocal > it.golesVisita && it.idEquipoLocal == 1L || it.golesVisita > it.golesLocal && it.idEquipoVisita == 1L }
    val empates = partidos.count { it.golesLocal == it.golesVisita }
    val derrotas = partidos.size - victorias - empates
    val golesFavor = partidos.sumOf { if (it.idEquipoLocal == 1L) it.golesLocal else if (it.idEquipoVisita == 1L) it.golesVisita else 0 }
    val golesContra = partidos.sumOf { if (it.idEquipoLocal == 1L) it.golesVisita else if (it.idEquipoVisita == 1L) it.golesLocal else 0 }
    val mister = entrenadores.find { it.idEquipo == 1L }

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFF121212)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("ESTADO DEL EQUIPO", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color(0xFFFFC107))
        }

        item {
            Card(colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("BALANCE GLOBAL", fontWeight = FontWeight.Bold, color = Color(0xFFFFC107))
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("V: $victorias", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                        Text("E: $empates", color = Color.Gray, fontWeight = FontWeight.Bold)
                        Text("D: $derrotas", color = Color.Red, fontWeight = FontWeight.Bold)
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color.DarkGray)
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text("Goles a Favor: $golesFavor", color = Color.White)
                        Text("Goles en Contra: $golesContra", color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("Aprobación del DT (${mister?.nombre ?: "Sin DT"}): 100%", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }
        }

        item {
            Text("ÚLTIMOS PARTIDOS (General)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Gray, modifier = Modifier.padding(top = 8.dp))
        }

        items(partidos) { partido ->
            Card(
                modifier = Modifier.fillMaxWidth().clickable { navController.navigate("detalle_partido/${partido.idPartido}") },
                colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
            ) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(partido.fecha, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                        // CORREGIDO: Usando idEquipoLocal e idEquipoVisita
                        Text(viewModel.obtenerNombreEquipo(partido.idEquipoLocal), modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.White)
                        Text("${partido.golesLocal} - ${partido.golesVisita}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color(0xFFFFC107), modifier = Modifier.padding(horizontal = 16.dp))
                        Text(viewModel.obtenerNombreEquipo(partido.idEquipoVisita), modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, color = Color.White)
                    }
                    Text("Toca para ver goleadores e incidencias", style = MaterialTheme.typography.labelSmall, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp))
                }
            }
        }
    }
}