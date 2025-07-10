import android.app.Activity
import android.util.Log
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class AuthServiceImpl(private val activity: Activity) : AuthService {

    private val auth: FirebaseAuth = Firebase.auth
    private val _currentUser = MutableStateFlow<FirebaseUser?>(auth.currentUser)

    override val currentUser: StateFlow<FirebaseUser?> = _currentUser.asStateFlow()

    override val isAuthenticated: Boolean
        get() = auth.currentUser != null

    init {
        // Observe auth state changes
        auth.addAuthStateListener {
            _currentUser.value = it.currentUser
        }
    }

    override suspend fun authenticate(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: Exception("Login failed")
                }
            }.await()
    }

    override suspend fun createUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (!task.isSuccessful) {
                    throw task.exception ?: Exception("User creation failed")
                }
            }.await()
    }

    override suspend fun signOut() {
        auth.signOut()
        _currentUser.value = null
    }

    // region 🔐 Phone Auth (Optional Helpers)

    private var verificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    fun startPhoneVerification(phoneNumber: String, onCodeSent: () -> Unit, onError: (Exception) -> Unit) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneCredential(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.e("AuthService", "Phone verification failed", e)
                onError(e)
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@AuthServiceImpl.verificationId = verificationId
                this@AuthServiceImpl.resendToken = token
                onCodeSent()
            }
        }

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, java.util.concurrent.TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyPhoneCode(code: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val id = verificationId ?: return onError(IllegalStateException("Verification ID missing"))
        val credential = PhoneAuthProvider.getCredential(id, code)
        signInWithPhoneCredential(credential, onSuccess, onError)
    }

    private fun signInWithPhoneCredential(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit = {},
        onError: (Exception) -> Unit = {},
    ) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    _currentUser.value = auth.currentUser
                    onSuccess()
                } else {
                    onError(task.exception ?: Exception("Phone sign-in failed"))
                }
            }
    }

    // endregion
}