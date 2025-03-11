package br.com.gallodev.agendapet.data.AppDataBase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.gallodev.agendapet.data.model.Cliente
import kotlinx.coroutines.flow.Flow

@Dao
interface ClienteDao {
    @Insert
    suspend fun insert(cliente: Cliente)

    @Update
    suspend fun update(cliente: Cliente)

    @Delete
    suspend fun delete(cliente: Cliente)

    @Query ("SELECT * FROM clientes")
    fun getAllClientes(): Flow<List<Cliente>>

    @Query("SELECT * FROM clientes WHERE id = :id")
    fun getClienteById(id: Int): Flow<Cliente> // Retorna um Flow para observação contínua

//    @Query("SELECT * FROM clientes WHERE emailTutor = :email AND senha = :senha")
//    fun verificaCredenciais(email: String, senha: String): Cliente?

}