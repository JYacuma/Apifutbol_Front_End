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
fun AjustesScreen(onFicharClick: () -> Unit, onVenderClick: () -> Unit, onGestionDT: () -> Unit, onAdminAccess: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize().background(Color(0xFF121212)).padding(16.dp)) {
        Text("AJUSTES / OFICINA", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Black, color = Color(0xFFFFC107))
        Spacer(modifier = Modifier.height(24.dp))

        BotonOpcion("Mercado de Fichajes", "Traer cracks mundiales", Icons.Default.ShoppingCart, onFicharClick)
        BotonOpcion("Vender Jugadores", "Gestionar salidas", Icons.Default.ExitToApp, onVenderClick)
        BotonOpcion("Gestión de Técnico", "Staff de Ancelotti", Icons.Default.Person, onGestionDT)

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onAdminAccess, modifier = Modifier.fillMaxWidth()) {
            Text("Panel de Administrador 🔒", color = Color.Red.copy(alpha = 0.7f))
        }
    }
}

@Composable
fun BotonOpcion(t: String, s: String, i: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), colors = CardDefaults.cardColors(containerColor = Color(0xFF1E1E1E))) {
        Row(modifier = Modifier.padding(16.dp)) {
            Icon(i, contentDescription = null, tint = Color(0xFFFFC107), modifier = Modifier.size(30.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(t, fontWeight = FontWeight.Bold, color = Color.White)
                Text(s, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
        }
    }
}