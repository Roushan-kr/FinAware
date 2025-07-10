package org.finAware.project.authentication

import AuthService
import AuthServiceImpl
import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import org.finAware.project.R.drawable.google
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun LoginScreen(
    onNavigateToSignUp: () -> Unit,
    onLoginSuccess: () -> Unit,
    onGoogleSignInClick: () -> Unit,
    viewModel: AuthViewModel,
    onBack: () -> Unit
) {

    val activity = LocalView.current.context as? Activity

    val viewModel = remember {
        AuthViewModel(AuthServiceImpl(requireNotNull(activity)))
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        errorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                viewModel.login(email, password) { error ->
                    errorMessage = error
                }
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onGoogleSignInClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = google),
                contentDescription = "Google Sign In",
                tint = Color.Unspecified,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onNavigateToSignUp) {
            Text("Don't have an account? Sign Up")
        }

        TextButton(onClick = onBack) {
            Text("← Back to Home")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val fakeAuthService = object : AuthService {
        override val currentUser: StateFlow<FirebaseUser?> = MutableStateFlow(null)
        override val isAuthenticated: Boolean = false
        override suspend fun authenticate(email: String, password: String) {}
        override suspend fun createUser(email: String, password: String) {}
        override suspend fun signOut() {}
        fun startPhoneVerification(
            phone: String,
            onCodeSent: () -> Unit,
            onError: (Exception) -> Unit
        ) {}
        fun verifyPhoneCode(
            code: String,
            onSuccess: () -> Unit,
            onError: (Exception) -> Unit
        ) {}
    }

    val fakeViewModel = AuthViewModel(authService = fakeAuthService)

    MaterialTheme {
        Surface {
            LoginScreen(
                onNavigateToSignUp = {},
                onLoginSuccess = {},
                onGoogleSignInClick = {},
                viewModel = fakeViewModel,
                onBack = {}
            )
        }
    }
}