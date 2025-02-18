package br.com.gallodev.agendapet.dao

import br.com.gallodev.agendapet.model.Cliente

class ClientesDao {

    fun adiciona(cliente: Cliente){
        clientes.add(cliente)
    }
    fun buscaTodos(): List<Cliente>{
        return clientes.toList()
    }

    companion object {
        private val clientes = mutableListOf<Cliente>()
    }
}