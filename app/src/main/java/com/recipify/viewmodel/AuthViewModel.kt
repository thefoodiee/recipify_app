package com.recipify.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState?>()
    val authState: LiveData<AuthState?> = _authState

    private val _currentUserEmail = MutableLiveData<String?>()
    val currentUserEmail: LiveData<String?> = _currentUserEmail

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        val user = auth.currentUser
        if (user == null) {
            _authState.value = AuthState.UnAuthenticated
            _currentUserEmail.value = null
        } else {
            _authState.value = AuthState.Authenticated
            _currentUserEmail.value = user.email
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    _currentUserEmail.value = auth.currentUser?.email
                } else {
                    _authState.value = AuthState.Error("Invalid credentials")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    _currentUserEmail.value = auth.currentUser?.email
                } else {
                    _authState.value = AuthState.Error("Account already exists")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.UnAuthenticated
        _currentUserEmail.value = null
    }

    fun clearAuthState() {
        _authState.value = null
    }

    // In AuthViewModel.kt
    var emailInput by mutableStateOf("")
        private set

    var passwordInput by mutableStateOf("")
        private set

    fun updateEmailInput(newEmail: String) {
        emailInput = newEmail
    }

    fun updatePasswordInput(newPassword: String) {
        passwordInput = newPassword
    }

}


sealed class AuthState{
    object Authenticated : AuthState()
    object UnAuthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}