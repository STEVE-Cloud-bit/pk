package com.example.pkfarm.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@Composable
fun PkFarmTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography(),
        content = content
    )
}
