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
    val madrid = jugadores.filter { it.idEquipo == 1L }

    val agrupados = madrid.groupBy { j ->
        val p = j.posicion.lowercase()
        when {
            p.contains("por") -> "PORTEROS"
            p.contains("def") || p.contains("lat") -> "DEFENSAS"
            p.contains("med") || p.contains("vol") -> "MEDIOCAMPISTAS"
            p.contains("del") || p.contains("ext") -> "DELANTEROS"
            else -> "OTROS"
        }
    }.toSortedMap(compareBy {
        when(it) { "PORTEROS" -> 1; "DEFENSAS" -> 2; "MEDIOCAMPISTAS" -> 3; "DELANTEROS" -> 4; else -> 5 }
    })

    LazyColumn(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)), contentPadding = PaddingValues(16.dp)) {
        item { Text("PRIMER EQUIPO", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Black, color = Color(0xFFFFC107)) }

        agrupados.forEach { (pos, lista) ->
            item { Text(pos, color = Color.Gray, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)) }
            items(lista) { jugador ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp).clickable { navController.navigate("detalle_jugador/${jugador.idJugador}") },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFF3A3422)), contentAlignment = Alignment.Center) {
                            Text("${jugador.dorsal}", color = Color(0xFFFFC107), fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Column {
                            Text(jugador.nombre.uppercase(), fontWeight = FontWeight.Black, color = Color.White)
                            Text("${jugador.posicion} • ${jugador.nacionalidad}", color = Color.LightGray, fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}