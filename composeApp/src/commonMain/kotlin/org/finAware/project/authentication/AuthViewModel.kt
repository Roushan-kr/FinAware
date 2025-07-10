package org.finAware.project.authentication

import AuthService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authService: AuthService = AuthServiceImpl()) : ViewModel() {

    // Explicit type: currentUser is a flow of nullable User
    val currentUser: StateFlow<FirebaseUser?> = authService.currentUser
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // isAuthenticated is true if currentUser is not null
    val isAuthenticated: StateFlow<Boolean> = currentUser
        .map { user -> user != null }
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
}
