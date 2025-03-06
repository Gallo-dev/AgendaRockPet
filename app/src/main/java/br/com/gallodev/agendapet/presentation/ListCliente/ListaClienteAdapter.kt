package br.com.gallodev.agendapet.presentation.ListCliente

import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.compose.ui.semantics.text
import androidx.recyclerview.widget.RecyclerView
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.data.model.Cliente
import java.io.File
import kotlin.io.path.exists

class ClienteAdapter(

    private var clientes: List<Cliente>

) : RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder>() {

    class ClienteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imagemPerfilCliente: ImageView = itemView.findViewById(R.id.imagem_item_animal)
        private val nomeCliente: TextView = itemView.findViewById(R.id.nome_cliente_formulario)
        private val nomeAnimal: TextView = itemView.findViewById(R.id.nome_animal_formulario)
        private val dataAtendimento: TextView = itemView.findViewById(R.id.data_atendimento)
        private val horaAtendimento: TextView = itemView.findViewById(R.id.hora_atendimento)
        private val telefoneCliente: TextView =
            itemView.findViewById(R.id.telefone_cliente_formulario)
        private val emailCliente: TextView = itemView.findViewById(R.id.email_cliente_formulario)
        private val observacaoCliente: TextView =
            itemView.findViewById(R.id.observacao_cliente_formulario)

        fun vincula(cliente: Cliente) {
            Log.i("ClienteAdapter", "vincula chamado")
            // Vincula os dados do cliente com a view
            nomeCliente.text = cliente.nomeTutor
            nomeAnimal.text = cliente.nomeAnimal
            dataAtendimento.text = cliente.dataAtendimento
            horaAtendimento.text = cliente.horaAtendimento
            telefoneCliente.text = cliente.telefoneTutor
            emailCliente.text = cliente.emailTutor
            observacaoCliente.text = cliente.observacaoAnimal

            carregaImagem(cliente.imagemPerfil, imagemPerfilCliente)
        }

        private fun carregaImagem(caminhoImagem: String?, imageView: ImageView) {
            if (!caminhoImagem.isNullOrEmpty()) {
                val arquivo = File(caminhoImagem)
                if (arquivo.exists()) {
                    val bitmap = BitmapFactory.decodeFile(arquivo.absolutePath)
                    imageView.setImageBitmap(bitmap)
                } else {
                    Log.e("ClienteAdapter", "Arquivo de imagem não encontrado: $caminhoImagem")
                    imageView.setImageResource(R.drawable.ic_launcher_foreground)
                }
            } else {
                Log.e("ClienteAdapter", "Caminho da imagem nulo ou vazio")
                imageView.setImageResource(R.drawable.ic_launcher_foreground)
            }
        }
    }

    // Responsavel por criar a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClienteViewHolder {
        Log.i("ClienteAdapter", "onCreateViewHolder chamado")
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.cliente_item, parent, false)
        return ClienteViewHolder(inflater)
    }

    // Responsavel por saber o tamanho da lista
    override fun getItemCount(): Int = clientes.size

    // Responsavel por exibir as views
    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        Log.i("ClienteAdapter", "onBindViewHolder chamado")
        val cliente = clientes[position]
        holder.vincula(cliente)
    }

    fun atualizaClientes(clientes: List<Cliente>) {
        Log.i("ClienteAdapter", "atualizaClientes chamado")
       this.clientes = clientes // Atualiza a lista de clientes
        notifyDataSetChanged() // Notifica o adapter que os dados foram atualizados
    }


}
