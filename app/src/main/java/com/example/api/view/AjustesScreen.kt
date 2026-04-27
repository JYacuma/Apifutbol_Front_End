package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AjustesScreen(
    onFicharClick: () -> Unit,
    onVenderClick: () -> Unit,
    onGestionDT: () -> Unit,
    onAdminAccess: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(16.dp)) {
        Text("AJUSTES / OFICINA", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color(0xFFFFC107))
        Spacer(modifier = Modifier.height(24.dp))

        BotonAjustes("Mercado de Fichajes", "Buscar nuevos talentos", Icons.Default.ShoppingCart, onFicharClick)
        BotonAjustes("Vender Jugadores", "Gestionar salidas y cesiones", Icons.Default.ExitToApp, onVenderClick)
        BotonAjustes("Gestión de Técnico", "Contratar o despedir DT", Icons.Default.Person, onGestionDT)

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            onClick = onAdminAccess,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red)
        ) {
            Icon(Icons.Default.Settings, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Panel de Administrador (CRUD)")
        }
    }
}

@Composable
fun BotonAjustes(titulo: String, subtitulo: String, icono: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(icono, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(titulo, fontWeight = FontWeight.Bold, color = Color.White)
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}