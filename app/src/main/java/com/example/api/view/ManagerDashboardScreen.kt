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
                val items = listOf("General" to Icons.Default.Home, "Plantilla" to Icons.Default.Person, "Estado" to Icons.Default.Star, "Ajustes" to Icons.Default.Settings)
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.second, contentDescription = item.first) },
                        label = { Text(item.first, fontSize = 10.sp) },
                        selected = tabSeleccionada == index,
                        onClick = { tabSeleccionada = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            indicatorColor = Color(0xFFFFC107),
                            unselectedIconColor = Color.Gray,
                            selectedTextColor = Color(0xFFFFC107),
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
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
                    Card(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).clickable { onPartidoClick(proximoPartido.idPartido) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFD4AF37))
                    ) {
                        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(proximoPartido.fecha, color = Color.Black, fontWeight = FontWeight.Bold)
                            Row(modifier = Modifier.fillMaxWidth().padding(top = 12.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("LOCAL", fontSize = 10.sp, color = Color.DarkGray)
                                    Text(viewModel.obtenerNombreEquipo(proximoPartido.idEquipoLocal), fontWeight = FontWeight.Black, color = Color.Black)
                                }
                                Text("VS", fontWeight = FontWeight.Black, color = Color.Black)
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text("VISITANTE", fontSize = 10.sp, color = Color.DarkGray)
                                    Text(viewModel.obtenerNombreEquipo(proximoPartido.idEquipoVisita), fontWeight = FontWeight.Black, color = Color.Black)
                                }
                            }
                        }
                    }
                }
            }
            if (ultimosResultados.isNotEmpty()) {
                item { Text("ÚLTIMOS RESULTADOS", color = Color.Gray, fontWeight = FontWeight.Bold) }
                items(ultimosResultados) { partido ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable { onPartidoClick(partido.idPartido) },
                        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                    ) {
                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
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