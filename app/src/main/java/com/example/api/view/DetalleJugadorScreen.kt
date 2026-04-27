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

    val statsDelJugador = estadisticas.filter { it.idJugador == id }
    val goles = statsDelJugador.sumOf { it.goles }
    val asistencias = statsDelJugador.sumOf { it.asistencias }
    val partidos = statsDelJugador.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PERFIL", color = Color(0xFFFFC107), fontWeight = FontWeight.Black) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "VOLVER", tint = Color(0xFFFFC107)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(padding).padding(16.dp)) {
            if (jugador != null) {
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                    Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(70.dp).clip(RoundedCornerShape(12.dp)).background(Color(0xFF3A3422)), contentAlignment = Alignment.Center) {
                            Text("${jugador.dorsal}", color = Color(0xFFFFC107), fontSize = 32.sp, fontWeight = FontWeight.Black)
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Column {
                            Text(jugador.nombre.uppercase(), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color.White)
                            Text("${jugador.posicion} • ${jugador.nacionalidad}", color = Color.Gray)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                Text("RENDIMIENTO TEMPORADA", fontWeight = FontWeight.Bold, color = Color.Gray)
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    EstadisticaCard("PJ", "$partidos", Modifier.weight(1f))
                    EstadisticaCard("GOLES", "$goles", Modifier.weight(1f))
                    EstadisticaCard("ASIST", "$asistencias", Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun EstadisticaCard(label: String, valor: String, modifier: Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(valor, fontSize = 24.sp, fontWeight = FontWeight.Black, color = Color.White)
            Text(label, fontSize = 10.sp, color = Color(0xFFFFC107))
        }
    }
}