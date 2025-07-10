import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthService {
    val currentUser: Flow<FirebaseUser?>
    val isAuthenticated: Boolean

    suspend fun authenticate(email: String, password: String)
    suspend fun createUser(email: String, password: String)
    suspend fun signOut()
}