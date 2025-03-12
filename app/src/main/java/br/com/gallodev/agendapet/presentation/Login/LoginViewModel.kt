package br.com.gallodev.agendapet.presentation.Login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.gallodev.agendapet.data.repository.Authentication

class LoginViewModel(private val authentication: Authentication) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<Boolean>>()
    val loginResult: LiveData<Result<Boolean>> get() = _loginResult

    fun login(email: String, password: String) {
        authentication.login(email, password) { success, error ->
            if (success) {
                _loginResult.postValue(Result.success(true))
                Log.d("LoginViewModel", "Login bem-sucedido")
            } else {
                val errorMessage = error ?: "Erro desconhecido"
                _loginResult.postValue(Result.failure(Exception(errorMessage)))
                Log.d("LoginViewModel", "Erro ao fazer login: $errorMessage")
            }
        }
    }
}

class LoginViewModelFactory(private val authentication: Authentication) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(LoginViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(authentication) as T
        }
        throw  IllegalArgumentException("Unknown ViewModel class")
    }
}