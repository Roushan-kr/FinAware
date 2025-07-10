package org.finAware.project.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SignUpScreen(
    onSignUpClick: (String, String, String, String) -> Unit,
    onVerifyOtpClick: (String) -> Unit,
    onBack: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var otp by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf<String?>(null) }
    var otpSent by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Sign Up", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email Address") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Phone Number") },
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSignUpClick(email, password, phone, name)
                otpSent = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign Up")
        }

        if (otpSent) {
            Spacer(modifier = Modifier.height(16.dp))

            Text("Enter OTP sent to your phone")

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = otp,
                onValueChange = { otp = it },
                label = { Text("OTP") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { onVerifyOtpClick(otp) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Verify OTP")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("← Back to Login")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    MaterialTheme {
        Surface {
            SignUpScreen(
                onSignUpClick = { name, email, phone, password ->
                    println("Preview sign-up clicked: $name, $email, $phone, $password")
                },
                onVerifyOtpClick = { otp ->
                    println("Preview OTP verify: $otp")
                },
                onBack = {
                    println("Preview back clicked")
                }
            )
        }
    }
}