package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.api.model.Entrenador
import com.example.api.viewmodel.FutbolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GestionarTecnicoScreen(viewModel: FutbolViewModel, onBack: () -> Unit) {
    val entrenadores by viewModel.entrenadores.collectAsState()

    val miEntrenador = entrenadores.find { it.idEquipo == 1L }
    val entrenadoresRivales = entrenadores.filter { it.idEquipo != 1L }

    var rivalSeleccionado by remember { mutableStateOf<Entrenador?>(null) }
    var mostrarDialogo by remember { mutableStateOf(false) }
    var mostrarExito by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GESTIÓN TÉCNICA", color = MaterialTheme.colorScheme.primary, style = MaterialTheme.typography.titleMedium) },
                navigationIcon = {
                    IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, contentDescription = "Volver") }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).background(MaterialTheme.colorScheme.background)) {

            // Mi Entrenador Actual
            if (miEntrenador != null) {
                Text("TU ENTRENADOR ACTUAL", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                Card(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(miEntrenador.nombre.uppercase(), style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimary)
                        Text("Especialidad: ${miEntrenador.especialidad}", color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
                    }
                }
            }

            Text("ENTRENADORES DISPONIBLES (TRUEQUE)", modifier = Modifier.padding(16.dp), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)

            LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(entrenadoresRivales) { rival ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(rival.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("Eq: ${viewModel.obtenerNombreEquipo(rival.idEquipo)} • ${rival.especialidad}", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                            }
                            IconButton(onClick = {
                                rivalSeleccionado = rival
                                mostrarDialogo = true
                            }) {
                                Icon(Icons.Default.SwapHoriz, contentDescription = "Intercambiar", tint = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
            }
        }
    }

    // --- ALERTA DE CONFIRMACIÓN ---
    if (mostrarDialogo && rivalSeleccionado != null && miEntrenador != null) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            title = { Text("Confirmar Intercambio") },
            text = { Text("¿Deseas enviar a ${miEntrenador.nombre} al ${viewModel.obtenerNombreEquipo(rivalSeleccionado?.idEquipo)} a cambio de traer a ${rivalSeleccionado?.nombre} al Real Madrid?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.intercambiarEntrenadores(miEntrenador, rivalSeleccionado!!)
                    mostrarDialogo = false
                    mostrarExito = "¡Intercambio realizado! ${rivalSeleccionado?.nombre} es el nuevo DT del Real Madrid."
                }) { Text("Confirmar") }
            },
            dismissButton = { TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") } }
        )
    }

    // --- MENSAJE DE ÉXITO (Y TE REGRESA) ---
    if (mostrarExito.isNotEmpty()) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Operación Exitosa") },
            text = { Text(mostrarExito) },
            confirmButton = {
                Button(onClick = {
                    mostrarExito = ""
                    onBack() // Te manda de regreso a la pantalla de Ajustes
                }) { Text("Aceptar") }
            }
        )
    }
}