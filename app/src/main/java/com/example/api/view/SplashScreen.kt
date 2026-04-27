package com.example.api.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.api.viewmodel.ApiState
import com.example.api.viewmodel.FutbolViewModel

@Composable
fun SplashScreen(viewModel: FutbolViewModel, onSplashFinished: () -> Unit) {
    val estadoApi by viewModel.apiState.collectAsState()
    val contexto = LocalContext.current // Para poder cerrar la app

    LaunchedEffect(estadoApi) {
        if (estadoApi == ApiState.EXITO) {
            onSplashFinished()
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("REAL MADRID C.F.", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Text("BIENVENIDO FLORENTINO PÉREZ", fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)

            Spacer(modifier = Modifier.height(40.dp))

            when (estadoApi) {
                ApiState.CARGANDO -> {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Conectando con el servidor...", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                }
                ApiState.ERROR -> {
                    Text("Error de conexión", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("El servidor tardó más de 10 segundos.", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f))
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { (contexto as? android.app.Activity)?.finish() }, // Cierra la app
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Sal e inténtalo de nuevo", color = MaterialTheme.colorScheme.onError)
                    }
                }
                ApiState.EXITO -> { }
            }
        }
    }
}