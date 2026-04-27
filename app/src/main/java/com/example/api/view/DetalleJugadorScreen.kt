package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.viewmodel.FutbolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleJugadorScreen(id: Long, viewModel: FutbolViewModel, onBack: () -> Unit) {
    val jugadores by viewModel.jugadores.collectAsState()
    val estadisticas by viewModel.estadisticas.collectAsState()

    val jugador = jugadores.find { it.idJugador == id }

    // Calcular Estadísticas Reales
    val statsDelJugador = estadisticas.filter { it.idJugador == id }
    val golesTotales = statsDelJugador.sumOf { it.goles }
    val asistenciasTotales = statsDelJugador.sumOf { it.asistencias }
    val partidosJugados = statsDelJugador.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PERFIL DEL JUGADOR", color = Color(0xFFFFC107), fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver", tint = Color(0xFFFFC107)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(padding).padding(16.dp)) {
            if (jugador != null) {
                // Ficha Técnica
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(modifier = Modifier.size(64.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFF3A3422)), contentAlignment = Alignment.Center) {
                                Text("${jugador.dorsal}", color = Color(0xFFFFC107), fontSize = 32.sp, fontWeight = FontWeight.Black)
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(jugador.nombre.uppercase(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color.White)
                                Text("${jugador.posicion} • ${jugador.nacionalidad}", color = Color.LightGray)
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Equipo Actual: ${viewModel.obtenerNombreEquipo(jugador.idEquipo)}", color = Color(0xFFFFC107), fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("ESTADÍSTICAS TEMPORADA", fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))


                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    StatBox(titulo = "Partidos", valor = "$partidosJugados", modifier = Modifier.weight(1f))
                    StatBox(titulo = "Goles", valor = "$golesTotales", modifier = Modifier.weight(1f))
                    StatBox(titulo = "Asistencias", valor = "$asistenciasTotales", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun StatBox(titulo: String, valor: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(valor, fontSize = 28.sp, fontWeight = FontWeight.Black, color = Color.White)
            Text(titulo, fontSize = 12.sp, color = Color.LightGray)
        }
    }
}