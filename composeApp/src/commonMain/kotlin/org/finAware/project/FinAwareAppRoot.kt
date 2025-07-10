package org.finAware.project
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable

@Composable
fun FinAwareAppRoot() {
    var currentScreen by remember { mutableStateOf("home") }

    when (currentScreen) {
        "home" -> FinAwareHomeScreen(
            onNavigateToLogin = {
                currentScreen = "login"
            },
            onGoogleSignInClick = TODO()
        )

        "login" -> LoginScreen(
            onBack = { currentScreen = "home" }
        )
    }
}

@Composable
fun LoginScreen(onBack: () -> Unit) {
    TODO("Not yet implemented")
}
