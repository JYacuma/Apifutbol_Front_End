package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.api.viewmodel.FutbolViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MercadoScreen(viewModel: FutbolViewModel, tipoOperacion: Int, onBack: () -> Unit) {
    val jugadores by viewModel.jugadores.collectAsState()

    // 0 = Fichar (Excluir Real Madrid), 1 = Vender (Solo Real Madrid)
    val esFichaje = tipoOperacion == 0
    val titulo = if (esFichaje) "MERCADO DE FICHAJES" else "VENTA DE JUGADORES"
    val jugadoresMostrar = jugadores.filter { if (esFichaje) it.idEquipo != 1L else it.idEquipo == 1L }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo, color = Color(0xFFFFC107), fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = onBack) { Icon(Icons.Default.ArrowBack, "Volver", tint = Color(0xFFFFC107)) } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF121212))
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(padding)) {
            items(jugadoresMostrar) { jugador ->
                Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(jugador.nombre.uppercase(), fontWeight = FontWeight.Black, color = Color.White)
                        Text("${jugador.posicion} • Eq: ${viewModel.obtenerNombreEquipo(jugador.idEquipo)}", color = Color.Gray)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(onClick = { /* Lógica de traspaso futura */ }, colors = ButtonDefaults.buttonColors(containerColor = if(esFichaje) Color(0xFF4CAF50) else Color.Red)) {
                                Text(if(esFichaje) "Comprar" else "Vender")
                            }
                        }
                    }
                }
            }
        }
    }
}