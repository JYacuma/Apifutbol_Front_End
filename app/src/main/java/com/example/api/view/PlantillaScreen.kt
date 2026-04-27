package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun PlantillaScreen(navController: NavController, viewModel: FutbolViewModel) {
    val jugadores by viewModel.jugadores.collectAsState()
    // Filtramos para que solo salgan los del Real Madrid (Equipo 1)
    val plantillaRealMadrid = jugadores.filter { it.idEquipo == 1L }

    // Agrupamos a los jugadores por su posición para que salgan ordenados
    val plantillaAgrupada = plantillaRealMadrid.groupBy { jugador ->
        val pos = jugador.posicion.lowercase()
        when {
            pos.contains("por") -> "PORTEROS"
            pos.contains("def") || pos.contains("lat") -> "DEFENSAS"
            pos.contains("med") || pos.contains("vol") -> "MEDIOCAMPISTAS"
            pos.contains("del") || pos.contains("ext") -> "DELANTEROS"
            else -> "OTROS"
        }
    }.toSortedMap(compareBy { key ->
        when(key) { "PORTEROS" -> 1; "DEFENSAS" -> 2; "MEDIOCAMPISTAS" -> 3; "DELANTEROS" -> 4; else -> 5 }
    })

    LazyColumn(
        modifier = Modifier.fillMaxSize().background(Color(0xFF121212)),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text("PRIMER EQUIPO", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color(0xFFFFC107))
            Spacer(modifier = Modifier.height(8.dp))
        }

        plantillaAgrupada.forEach { (grupo, lista) ->
            item {
                Text(grupo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.Gray, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp))
            }
            items(lista) { jugador ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { navController.navigate("detalle_jugador/${jugador.idJugador}") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(modifier = Modifier.padding(12.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                        // El Cuadro del Dorsal estilo FIFA
                        Box(
                            modifier = Modifier.size(56.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF3A3422)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("${jugador.dorsal}", color = Color(0xFFFFC107), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        // Información del Jugador
                        Column {
                            Text(jugador.nombre.uppercase(), fontWeight = FontWeight.Black, color = Color.White, fontSize = 16.sp)
                            Text("${jugador.posicion} • ${jugador.nacionalidad}", color = Color.LightGray, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}