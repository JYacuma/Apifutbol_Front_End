package com.example.api

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.api.ui.theme.ApiTheme
import com.example.api.view.FutbolAppNavigation
import com.example.api.viewmodel.FutbolViewModel

class MainActivity : ComponentActivity() {


    private val viewModel: FutbolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApiTheme {

                FutbolAppNavigation(viewModel = viewModel)
            }
        }
    }
}