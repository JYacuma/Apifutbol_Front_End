package com.example.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import com.example.api.view.FutbolAppNavigation
import com.example.api.viewmodel.FutbolViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: FutbolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Detecta si tu celular está en modo oscuro o claro
            val modoOscuro = isSystemInDarkTheme()

            // PALETA MODO OSCURO: Negro y Dorado
            val coloresOscuros = darkColorScheme(
                primary = Color(0xFFFFC107),       // Dorado
                background = Color(0xFF121212),    // Negro de fondo
                surface = Color(0xFF1E1E1E),       // Gris oscuro para tarjetas
                onPrimary = Color.Black,           // Texto negro sobre botones dorados
                onBackground = Color.White,        // Texto blanco sobre fondo negro
                onSurface = Color.White            // Texto blanco sobre tarjetas
            )

            // PALETA MODO CLARO: Blanco y Dorado
            val coloresClaros = lightColorScheme(
                primary = Color(0xFFFFC107),       // Dorado
                background = Color(0xFFF8F9FA),    // Gris ultra clarito/Blanco perla para fondo
                surface = Color(0xFFFFFFFF),       // Blanco puro para las tarjetas
                onPrimary = Color.Black,           // Texto negro sobre botones dorados
                onBackground = Color(0xFF212529),  // Texto gris oscuro/casi negro
                onSurface = Color(0xFF212529)      // Texto oscuro sobre tarjetas
            )

            // Asignamos la paleta dependiendo de cómo tengas el celular
            MaterialTheme(colorScheme = if (modoOscuro) coloresOscuros else coloresClaros) {
                FutbolAppNavigation(viewModel = viewModel)
            }
        }
    }
}