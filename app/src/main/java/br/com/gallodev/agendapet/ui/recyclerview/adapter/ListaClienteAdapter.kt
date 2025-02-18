package br.com.gallodev.agendapet.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.model.Cliente

class ListaClienteAdapter(

    private val context: Context,
    clientes: List<Cliente>

) : RecyclerView.Adapter<ListaClienteAdapter.ViewHolder>() {

    // Permite a manipulação da lista de clientes internamente
    private val clientes = clientes.toMutableList()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun vincula(cliente: Cliente) {
            // Vincula os dados do cliente com a view
            val nomeCliente = itemView.findViewById<TextView>(R.id.nome_cliente_formulario)
            nomeCliente.text = cliente.nomeTutor
            val nomeAnimal = itemView.findViewById<TextView>(R.id.nome_animal_formulario)
            nomeAnimal.text = cliente.nomeAnimal
            val telefoneCliente = itemView.findViewById<TextView>(R.id.telefone_cliente_formulario)
            telefoneCliente.text = cliente.telefoneTutor.toString()
            val emailCliente = itemView.findViewById<TextView>(R.id.email_cliente_formulario)
            emailCliente.text = cliente.emailTutor
            val observacaoCliente = itemView.findViewById<TextView>(R.id.observacao_cliente_formulario)
            observacaoCliente.text = cliente.observacaoAnimal
        }
    }

    // Responsavel por criar a view
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.cliente, parent, false)
        return ViewHolder(view)
    }
    // Responsavel por saber o tamanho da lista
    override fun getItemCount(): Int = clientes.size

    // Responsavel por exibir as views
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cliente = clientes[position]
        holder.vincula(cliente)
    }

    fun atualiza(clientes: List<Cliente>) {
        this.clientes.clear() // Limpa a lista de clientes
        this.clientes.addAll(clientes) // Adiciona os novos clientes
        notifyDataSetChanged() // Notifica o adapter que os dados foram atualizados
    }

}
