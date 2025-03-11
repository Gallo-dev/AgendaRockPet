package br.com.gallodev.agendapet.presentation.Login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.gallodev.agendapet.data.AppDataBase.ClienteDao
import br.com.gallodev.agendapet.data.model.Cliente
import br.com.gallodev.agendapet.data.repository.Authentication

class LoginViewModel(private val authentication: Authentication) : ViewModel() {
    private val _loginResult = MutableLiveData<Result<Boolean>>()
    val loginResult: LiveData<Result<Boolean>> get() = _loginResult

    fun login(email: String, password: String) {
        authentication.login(email, password) { success, error ->
            if (success) {
                _loginResult.postValue(Result.success(true))
            } else {
                val errorMessage = error ?: "Erro desconhecido"
                _loginResult.postValue(Result.failure(Exception(errorMessage)))
            }
        }
    }
}