package org.finAware.project.Ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier

@Composable
fun BottomNavBar() {
    val items = listOf(
        BottomNavItem("Dashboard", Icons.Filled.Dashboard),
        BottomNavItem("Simulator", Icons.Filled.Settings),
        BottomNavItem("Learning", Icons.Filled.School),
        BottomNavItem("Profile", Icons.Filled.Person)
    )

    var selectedIndex by remember { mutableIntStateOf(0) }

    NavigationBar(
        tonalElevation = 4.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = selectedIndex == index,
                onClick = { selectedIndex = index }
            )
        }
    }
}

data class BottomNavItem(val label: String, val icon: ImageVector)

@Preview(showBackground = true ,showSystemUi = true)
@Composable
fun BottomNavBarPreview() {
    MaterialTheme {
        BottomNavBar()
    }
}
