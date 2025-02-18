package br.com.gallodev.agendapet.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gallodev.agendapet.dao.ClientesDao
import br.com.gallodev.agendapet.databinding.ActivityMainBinding
import br.com.gallodev.agendapet.ui.recyclerview.adapter.ListaClienteAdapter

class ListaClientesActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val dao = ClientesDao()
    private val adapter = ListaClienteAdapter(this, clientes = dao.buscaTodos())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configuraRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.buscaTodos()) // Atualiza a lista de clientes
        fabAddCliente()
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.listaClientesRecyclerView // Obtém a referência para o RecyclerView
        recyclerView.adapter = adapter
    }

    // Método para configurar o botão de adicionar cliente
    private fun fabAddCliente() {
        binding.fabAddCliente.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivity(intent)
        }
    }

}