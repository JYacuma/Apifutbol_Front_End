package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun DetalleEntrenadorScreen(idEntrenador: Long, viewModel: FutbolViewModel) {
    val entrenadores by viewModel.entrenadores.collectAsState()
    val partidos by viewModel.partidos.collectAsState()
    val entrenador = entrenadores.find { it.idEntrenador == idEntrenador }


    val partidosEquipo = partidos.filter { it.idEquipoLocal == entrenador?.idEquipo || it.idEquipoVisita == entrenador?.idEquipo }
    val victorias = partidosEquipo.count {
        (it.idEquipoLocal == entrenador?.idEquipo && it.golesLocal > it.golesVisita) ||
                (it.idEquipoVisita == entrenador?.idEquipo && it.golesVisita > it.golesLocal)
    }
    val rendimiento = if (partidosEquipo.isNotEmpty()) (victorias * 100) / partidosEquipo.size else 0

    if (entrenador == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text("Buscando técnico...") }
        return
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(modifier = Modifier.padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "DIRECTOR TÉCNICO", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.labelLarge)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = entrenador.nombre.uppercase(), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = viewModel.obtenerNombreEquipo(entrenador.idEquipo), style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("ESPECIALIDAD", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                    Text(entrenador.especialidad, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
                }
            }
            Card(modifier = Modifier.weight(1f), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("RENDIMIENTO", fontSize = 12.sp, color = MaterialTheme.colorScheme.primary)
                    Text("$rendimiento%", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}