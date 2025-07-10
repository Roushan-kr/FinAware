package org.finAware.project

import AuthServiceImpl
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.finAware.project.authentication.*
import org.finAware.project.Ui.theme.FinAwareTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            FinAwareApp()
        }
    }
}

@Composable
fun FinAwareApp() {
    val context = LocalContext.current
    val activity = context as ComponentActivity

    // Simple navigation logic
    var currentScreen by remember { mutableStateOf("home") }

    // ViewModel with Android activity passed in
    val viewModel = remember {
        AuthViewModel(AuthServiceImpl(activity))
    }

    FinAwareTheme {
        when (currentScreen) {
            "home" -> FinAwareHomeScreen(
                onGoogleSignInClick = {
                    // TODO: Trigger Google Sign-In if needed
                },
                onNavigateToLogin = {
                    currentScreen = "login"
                }
            )

            "login" -> LoginScreen(
                onNavigateToSignUp = {
                    currentScreen = "signup"
                },
                onLoginSuccess = {
                    currentScreen = "home"
                },
                onGoogleSignInClick = {
                    // TODO: Trigger Google Sign-In
                },
                viewModel = viewModel,
                onBack = {
                    currentScreen = "home"
                }
            )

            "signup" -> SignUpScreen(
                onSignUpClick = { name, email, phone, password ->
                    viewModel.signUp(email, password) {
                        // error handling can go here
                    }
                },
                onVerifyOtpClick = { otp ->
                    // You could use `viewModel.verifyPhoneCode(...)`
                },
                onBack = {
                    currentScreen = "login"
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    FinAwareApp()
}
