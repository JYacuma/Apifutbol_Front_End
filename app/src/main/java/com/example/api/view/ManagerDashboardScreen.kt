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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun ManagerDashboardScreen(navController: NavController, viewModel: FutbolViewModel) {
    var tabSeleccionada by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF1E1E1E)) {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = "General") },
                    label = { Text("General", fontSize = 10.sp) },
                    selected = tabSeleccionada == 0,
                    onClick = { tabSeleccionada = 0 },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Black, indicatorColor = Color(0xFFFFC107), unselectedIconColor = Color.Gray, selectedTextColor = Color(0xFFFFC107), unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Plantilla") },
                    label = { Text("Plantilla", fontSize = 10.sp) },
                    selected = tabSeleccionada == 1,
                    onClick = { tabSeleccionada = 1 },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Black, indicatorColor = Color(0xFFFFC107), unselectedIconColor = Color.Gray, selectedTextColor = Color(0xFFFFC107), unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Star, contentDescription = "Estado") },
                    label = { Text("Estado", fontSize = 10.sp) },
                    selected = tabSeleccionada == 2,
                    onClick = { tabSeleccionada = 2 },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Black, indicatorColor = Color(0xFFFFC107), unselectedIconColor = Color.Gray, selectedTextColor = Color(0xFFFFC107), unselectedTextColor = Color.Gray)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Settings, contentDescription = "Ajustes") },
                    label = { Text("Ajustes", fontSize = 10.sp) },
                    selected = tabSeleccionada == 3,
                    onClick = { tabSeleccionada = 3 },
                    colors = NavigationBarItemDefaults.colors(selectedIconColor = Color.Black, indicatorColor = Color(0xFFFFC107), unselectedIconColor = Color.Gray, selectedTextColor = Color(0xFFFFC107), unselectedTextColor = Color.Gray)
                )
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (tabSeleccionada) {
                0 -> TabGeneral(viewModel, onPartidoClick = { id -> navController.navigate("detalle_partido/$id") })
                1 -> PlantillaScreen(navController, viewModel)
                2 -> EstadoEquipoScreen(navController, viewModel)
                3 -> AjustesScreen(
                    onFicharClick = { navController.navigate("mercado/0") },
                    onVenderClick = { navController.navigate("mercado/1") },
                    onGestionDT = { navController.navigate("gestionar_tecnico") },
                    onAdminAccess = { navController.navigate("admin_panel") }
                )
            }
        }
    }
}

@Composable
fun TabGeneral(viewModel: FutbolViewModel, onPartidoClick: (Long) -> Unit) {
    val partidos by viewModel.partidos.collectAsState()
    val proximoPartido = partidos.firstOrNull()
    val ultimosResultados = if (partidos.size > 1) partidos.drop(1) else emptyList()

    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212))) {
        Text("GENERAL", modifier = Modifier.fillMaxWidth().padding(top = 24.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.titleLarge, color = Color(0xFFFFC107), fontWeight = FontWeight.Bold)
        Text("Bienvenido Florentino Pérez", modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            if (proximoPartido != null) {
                item {
                    Text("PRÓXIMO COMPROMISO", color = Color.Gray, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onPartidoClick(proximoPartido.idPartido) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD4AF37))
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(proximoPartido.fecha, color = Color.Black, fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                    Text("LOCAL", fontSize = 10.sp, color = Color.DarkGray)
                                    Text(viewModel.obtenerNombreEquipo(proximoPartido.idEquipoLocal), fontWeight = FontWeight.Black, color = Color.Black, textAlign = TextAlign.Center)
                                }
                                Text("VS", fontWeight = FontWeight.Black, color = Color.Black, modifier = Modifier.padding(horizontal = 8.dp))
                                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1f)) {
                                    Text("VISITANTE", fontSize = 10.sp, color = Color.DarkGray)
                                    Text(viewModel.obtenerNombreEquipo(proximoPartido.idEquipoVisita), fontWeight = FontWeight.Black, color = Color.Black, textAlign = TextAlign.Center)
                                }
                            }
                        }
                    }
                }
            }

            if (ultimosResultados.isNotEmpty()) {
                item { Text("ÚLTIMOS RESULTADOS", color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp)) }
                items(ultimosResultados) { partido ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onPartidoClick(partido.idPartido) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                    ) {
                        Row(modifier = Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text(viewModel.obtenerNombreEquipo(partido.idEquipoLocal), modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color.White)
                            Surface(color = Color.Black, shape = MaterialTheme.shapes.small) {
                                Text("${partido.golesLocal} - ${partido.golesVisita}", modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp), fontWeight = FontWeight.Black, color = Color(0xFFFFC107))
                            }
                            Text(viewModel.obtenerNombreEquipo(partido.idEquipoVisita), modifier = Modifier.weight(1f), textAlign = TextAlign.Center, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}