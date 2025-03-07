package br.com.gallodev.agendapet.presentation.detalhesCliente

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.gallodev.agendapet.data.AppDataBase.ClienteDao
import br.com.gallodev.agendapet.data.model.Cliente
import kotlinx.coroutines.launch

class DetalheClienteViewModel(private val clienteDao: ClienteDao): ViewModel(){
    private val _cliente = MutableLiveData<Cliente>()
    val cliente: MutableLiveData<Cliente>
        get() = _cliente

    fun buscaClientePorId(id: Int){
        viewModelScope.launch {
            clienteDao.getClienteById(id).collect{ clienteEncontrado ->
                cliente.postValue(clienteEncontrado)
            }
        }

    }
}
class DetalheClienteViewModelFactory(private val clienteDao: ClienteDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(DetalheClienteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return DetalheClienteViewModel(clienteDao) as T
        }
        throw IllegalArgumentException("Classe ViewModel desconhecida")
    }
}