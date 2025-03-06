package br.com.gallodev.agendapet.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clientes")

data class Cliente(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nomeTutor: String,
    val nomeAnimal: String,
    val telefoneTutor: String,
    val emailTutor: String,
    val observacaoAnimal: String,
    val dataAtendimento: String,
    val horaAtendimento: String,
    val imagemPerfil: String? = null // Caminho para a imagem de perfil
)
