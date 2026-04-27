package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.api.viewmodel.FutbolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetallePartidoScreen(viewModel: FutbolViewModel, idPartido: Long, onBack: () -> Unit) {
    val partidos by viewModel.partidos.collectAsState()
    val estadisticas by viewModel.estadisticas.collectAsState()
    val jugadores by viewModel.jugadores.collectAsState()

    val partido = partidos.find { it.idPartido == idPartido }
    val stats = estadisticas.filter { it.idPartido == idPartido }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("RESULTADO FINAL", style = MaterialTheme.typography.titleMedium, color = Color(0xFFFFC107)) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver", tint = Color(0xFFFFC107)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize().background(Color(0xFF121212)).padding(16.dp)) {

            Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(24.dp)) {
                    Text(partido?.fecha ?: "Fecha no disponible", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                        // CORREGIDO: Usando idEquipoLocal e idEquipoVisita
                        Text(viewModel.obtenerNombreEquipo(partido?.idEquipoLocal ?: 0), modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge, color = Color.White)

                        Text("${partido?.golesLocal ?: 0} - ${partido?.golesVisita ?: 0}",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Black,
                            color = Color(0xFFFFC107),
                            modifier = Modifier.padding(horizontal = 16.dp))

                        Text(viewModel.obtenerNombreEquipo(partido?.idEquipoVisita ?: 0), modifier = Modifier.weight(1f), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyLarge, color = Color.White)
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(partido?.estadio ?: "Estadio", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("INCIDENCIAS DEL ENCUENTRO", fontWeight = FontWeight.Bold, color = Color(0xFFFFC107))
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                if (stats.isEmpty()) {
                    item { Text("No hay incidencias registradas en este partido.", color = Color.Gray) }
                } else {
                    items(stats) { stat ->
                        val jugador = jugadores.find { it.idJugador == stat.idJugador }
                        Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                            Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text(jugador?.nombre ?: "Jugador desconocido", modifier = Modifier.weight(1f), color = Color.White)
                                Row {
                                    if (stat.goles > 0) {
                                        Badge(containerColor = Color(0xFFFFC107)) { Text("⚽ ${stat.goles}", color = Color.Black) }
                                        Spacer(modifier = Modifier.width(8.dp))
                                    }
                                    if (stat.asistencias > 0) {
                                        Badge(containerColor = Color(0xFF03A9F4)) { Text("👟 ${stat.asistencias}", color = Color.White) }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}