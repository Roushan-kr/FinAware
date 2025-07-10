import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    val currentUser: Flow<FirebaseUser?>
    val isAuthenticated: Boolean

    suspend fun authenticate(email: String, password: String)
    suspend fun createUser(email: String, password: String)
    suspend fun signOut()
}
