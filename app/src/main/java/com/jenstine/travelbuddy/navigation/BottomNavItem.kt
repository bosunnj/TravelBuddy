package com.jenstine.travelbuddy.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Explore
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    READ("read",  Icons.AutoMirrored.Filled.MenuBook, "Read"),
    SEE("see",   Icons.Filled.Explore,  "See"),
    WRITE("write", Icons.Filled.Edit,   "Write")
}
