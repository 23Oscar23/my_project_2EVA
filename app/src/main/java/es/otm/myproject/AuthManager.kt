package es.otm.myproject

import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import kotlinx.coroutines.tasks.await

class AuthManager {

    private val auth: FirebaseAuth by lazy { Firebase.auth }

    suspend fun login(email: String, password: String): FirebaseUser?{
        return try{
            val authResult = auth.signInWithEmailAndPassword(email, password).await()
            authResult.user
        }catch (e: Exception){
            null
        }
    }

    suspend fun register(email: String, password: String): FirebaseUser?{
        return try {
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            authResult.user
        }catch (e: Exception){
            null
        }
    }

    suspend fun rememberPass(email: String) {
        try {
            auth.sendPasswordResetEmail(email).await()
        }catch (e: Exception){
            null
        }
    }

    fun logOut(){
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

}