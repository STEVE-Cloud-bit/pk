@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.pkfarm.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("PKFarm") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                navController?.navigate("calendar")
            }) {
                Text("View Crop Calendar")
            }
        }
    }
}
