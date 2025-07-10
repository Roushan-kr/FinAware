package org.finAware.project.authentication

import AuthService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class AuthServiceImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
) : AuthService {

    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    override val currentUser: Flow<FirebaseUser?> = _currentUser.asStateFlow()

    override val isAuthenticated: Boolean
        get() = auth.currentUser != null

     val currentUserId: String?
        get() = auth.currentUser?.uid

    init {
        auth.addAuthStateListener {
            _currentUser.value = it.currentUser
        }
    }

    override suspend fun authenticate(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val result = CompletableDeferred<Unit>()
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) result.complete(Unit)
                    else result.completeExceptionally(task.exception ?: Exception("Unknown Error"))
                }
            result.await()
        }
    }

    override suspend fun createUser(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val result = CompletableDeferred<Unit>()
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) result.complete(Unit)
                    else result.completeExceptionally(task.exception ?: Exception("Unknown Error"))
                }
            result.await()
        }
    }

    override suspend fun signOut() {
        withContext(Dispatchers.IO) {
            auth.signOut()
        }
    }
}
