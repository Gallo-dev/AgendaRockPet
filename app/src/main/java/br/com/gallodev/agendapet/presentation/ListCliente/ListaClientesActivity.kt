package br.com.gallodev.agendapet.presentation.ListCliente

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.gallodev.agendapet.data.AppDataBase.PetApplication
import br.com.gallodev.agendapet.databinding.ActivityListaClienteBinding
import br.com.gallodev.agendapet.presentation.detalhesCliente.ListaDetalhesActivity
import br.com.gallodev.agendapet.presentation.formularioCliente.FormularioClienteActivity
import kotlinx.coroutines.launch

class ListaClientesActivity : AppCompatActivity() {

    private lateinit var clienteAdapter: ListaClienteAdapter
    private val binding by lazy {
        ActivityListaClienteBinding.inflate(layoutInflater)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(binding.root)

        configuraRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        val clienteDao = (application as PetApplication).database.clienteDao()

        lifecycleScope.launch {
            clienteDao.getAllClientes().collect { clientes ->
                if (clientes.isEmpty()) {
                    binding.textoListaVazia.visibility = View.VISIBLE
                } else {
                    binding.textoListaVazia.visibility = View.GONE
                }
                clienteAdapter.atualizaClientes(clientes)
            }
        }

        fabAddCliente()
    }

    private fun configuraRecyclerView() {
        val recyclerView =
            binding.listaClientesRecyclerView // Obtém a referência para o RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)

        clienteAdapter = ListaClienteAdapter(emptyList()){ cliente ->
            val intent = Intent(this, ListaDetalhesActivity::class.java).apply {
                putExtra("CLIENTE", cliente.id) // Passa o ID do cliente como extra
            }
            startActivity(intent)
        }
        recyclerView.adapter = clienteAdapter
    }


    // Método para configurar o botão de adicionar cliente
    private fun fabAddCliente() {
        binding.fabAddCliente.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivity(intent)
        }
    }

}