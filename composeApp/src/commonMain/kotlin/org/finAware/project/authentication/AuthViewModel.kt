package org.finAware.project.authentication

import AuthService
import AuthServiceImpl
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authService: AuthService
) : ViewModel() {

    val currentUser: StateFlow<FirebaseUser?> = authService.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isAuthenticated: StateFlow<Boolean> = currentUser
        .map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun login(email: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                authService.authenticate(email, password)
            } catch (e: Exception) {
                onError(e.message ?: "Login failed")
            }
        }
    }

    fun signUp(email: String, password: String, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                authService.createUser(email, password)
            } catch (e: Exception) {
                onError(e.message ?: "Sign up failed")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            authService.signOut()
        }
    }

    fun startPhoneAuth(
        phone: String,
        onCodeSent: () -> Unit,
        onError: (String) -> Unit
    ) {
        (authService as? AuthServiceImpl)?.startPhoneVerification(
            phone,
            onCodeSent,
            { onError(it.message ?: "Phone verification failed") }
        )
    }

    fun verifyOtp(
        otp: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        (authService as? AuthServiceImpl)?.verifyPhoneCode(
            otp,
            onSuccess,
            { onError(it.message ?: "OTP verification failed") }
        )
    }
}