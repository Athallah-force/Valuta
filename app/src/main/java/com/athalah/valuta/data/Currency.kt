package com.athalah.valuta.data

import androidx.compose.ui.graphics.vector.ImageVector

data class Currency (
    val name: String,
    val buy: Double,
    val sell: Double,
    val flag: String
)