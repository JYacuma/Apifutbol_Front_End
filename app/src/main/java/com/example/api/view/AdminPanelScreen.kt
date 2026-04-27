package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
    var tabSeleccionada by remember { mutableIntStateOf(0) }

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
            FloatingActionButton(onClick = { /* Lógica de crear */ }, containerColor = Color.Red) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.White)
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(padding)) {
            ScrollableTabRow(selectedTabIndex = tabSeleccionada, containerColor = Color(0xFF1E1E1E), contentColor = Color.Red) {
                Tab(selected = tabSeleccionada == 0, onClick = { tabSeleccionada = 0 }, text = { Text("JUGADORES", color = if(tabSeleccionada==0) Color.Red else Color.Gray) })
                Tab(selected = tabSeleccionada == 1, onClick = { tabSeleccionada = 1 }, text = { Text("EQUIPOS", color = if(tabSeleccionada==1) Color.Red else Color.Gray) })
                Tab(selected = tabSeleccionada == 2, onClick = { tabSeleccionada = 2 }, text = { Text("TÉCNICOS", color = if(tabSeleccionada==2) Color.Red else Color.Gray) })
                Tab(selected = tabSeleccionada == 3, onClick = { tabSeleccionada = 3 }, text = { Text("PARTIDOS", color = if(tabSeleccionada==3) Color.Red else Color.Gray) })
            }

            LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                if (tabSeleccionada == 0) {
                    items(jugadores) { j -> AdminItemCard(j.nombre, j.posicion, onEdit = {}, onDelete = {}) }
                } else if (tabSeleccionada == 1) {
                    items(equipos) { e -> AdminItemCard(e.nombre, e.ciudad, onEdit = {}, onDelete = {}) }
                } else if (tabSeleccionada == 2) {
                    items(entrenadores) { dt -> AdminItemCard(dt.nombre, dt.especialidad, onEdit = {}, onDelete = {}) }
                } else if (tabSeleccionada == 3) {
                    items(partidos) { p ->
                        AdminItemCard("${viewModel.obtenerNombreEquipo(p.idEquipoLocal)} vs ${viewModel.obtenerNombreEquipo(p.idEquipoVisita)}", "${p.golesLocal} - ${p.golesVisita}", onEdit = {}, onDelete = {})
                    }
                }
            }
        }
    }
}

@Composable
fun AdminItemCard(titulo: String, subtitulo: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, fontWeight = FontWeight.Bold, color = Color.White)
                Text(subtitulo, color = Color.Gray)
            }
            Row {
                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = Color.Gray, modifier = Modifier.padding(end=16.dp).clickable { onEdit() })
                Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = Color.Red, modifier = Modifier.clickable { onDelete() })
            }
        }
    }
}