package br.com.gallodev.agendapet.presentation.detalhesCliente

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.data.AppDataBase.PetApplication
import br.com.gallodev.agendapet.databinding.ActivityListaDetalhesBinding
import java.io.File

class ListaDetalhesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaDetalhesBinding
    private val clienteViewModel: DetalheClienteViewModel by viewModels {
        DetalheClienteViewModelFactory((application as PetApplication).database.clienteDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaDetalhesBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(binding.root)

        val clienteId = intent.getIntExtra("CLIENTE", -1)
        if (clienteId != -1) {
            clienteViewModel.buscaClientePorId(clienteId)
        } else {
            Log.e("ListaDetalhesActivity", "ID do cliente não encontrado")
        }

        // observando o cliente para exibir os detalhes
        clienteViewModel.cliente.observe(this) { cliente ->
            if (cliente != null) {
                binding.imagemPerfilClienteDetalhe.let { carregaImagem(cliente.imagemPerfil, it) }
                binding.nomeClienteDetalhe.text = cliente.nomeTutor
                binding.nomeAnimalDetalhe.text = cliente.nomeAnimal
                binding.telefoneClienteDetalhe.text = cliente.telefoneTutor
                binding.emailClienteDetalhe.text = cliente.emailTutor
            }
        }
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