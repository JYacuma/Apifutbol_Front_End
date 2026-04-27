package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
fun AdminPanelScreen(viewModel: FutbolViewModel, onBack: () -> Unit) {
    var tabSeleccionada by remember { mutableStateOf(0) }

    val jugadores by viewModel.jugadores.collectAsState()
    val equipos by viewModel.equipos.collectAsState()
    val entrenadores by viewModel.entrenadores.collectAsState()
    val partidos by viewModel.partidos.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BD ADMIN", color = Color.Red, fontWeight = FontWeight.Black) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver", tint = Color.Red) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Lógica de crear según el tab */ }, containerColor = Color.Red) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.White)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(padding)) {
            ScrollableTabRow(selectedTabIndex = tabSeleccionada, containerColor = Color(0xFF1E1E1E), contentColor = Color.Red) {
                Tab(selected = tabSeleccionada == 0, onClick = { tabSeleccionada = 0 }, text = { Text("JUGADORES") })
                Tab(selected = tabSeleccionada == 1, onClick = { tabSeleccionada = 1 }, text = { Text("EQUIPOS") })
                Tab(selected = tabSeleccionada == 2, onClick = { tabSeleccionada = 2 }, text = { Text("TÉCNICOS") })
                Tab(selected = tabSeleccionada == 3, onClick = { tabSeleccionada = 3 }, text = { Text("PARTIDOS") })
            }

            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (tabSeleccionada == 0) {
                    items(jugadores) { jugador -> AdminItemCard(jugador.nombre, jugador.posicion) }
                } else if (tabSeleccionada == 1) {
                    items(equipos) { equipo -> AdminItemCard(equipo.nombre, equipo.ciudad) }
                } else if (tabSeleccionada == 2) {
                    items(entrenadores) { dt -> AdminItemCard(dt.nombre, dt.especialidad) }
                } else if (tabSeleccionada == 3) {
                    items(partidos) { partido ->
                        // AQUÍ ESTÁ LA CORRECCIÓN: idEquipoLocal e idEquipoVisita
                        AdminItemCard(
                            titulo = "${viewModel.obtenerNombreEquipo(partido.idEquipoLocal)} vs ${viewModel.obtenerNombreEquipo(partido.idEquipoVisita)}",
                            subtitulo = "${partido.golesLocal} - ${partido.golesVisita} | ${partido.fecha}"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AdminItemCard(titulo: String, subtitulo: String) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, fontWeight = FontWeight.Bold, color = Color.White)
                Text(subtitulo, color = Color.Gray)
            }
            Row {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray, modifier = Modifier.padding(end=8.dp))
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red)
            }
        }
    }
}