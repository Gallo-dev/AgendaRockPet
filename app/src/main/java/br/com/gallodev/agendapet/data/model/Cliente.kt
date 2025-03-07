package br.com.gallodev.agendapet.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable

@Entity(tableName = "clientes")
@Parcelize
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
): Parcelable
