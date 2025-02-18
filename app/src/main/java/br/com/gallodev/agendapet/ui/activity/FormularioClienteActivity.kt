package br.com.gallodev.agendapet.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.gallodev.agendapet.dao.ClientesDao
import br.com.gallodev.agendapet.databinding.ActivityFormularioClienteBinding
import br.com.gallodev.agendapet.model.Cliente

class FormularioClienteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFormularioClienteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityFormularioClienteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Dados do Cliente"

        configuraBotaoSalvar()
    }

    private fun configuraBotaoSalvar() {
        val botaoSalvar = binding.botaoSalvarFormulario
        val dao = ClientesDao()
        botaoSalvar.setOnClickListener {
            val clienteNovo = cliente()
            dao.adiciona(clienteNovo)
            finish()
        }
    }

    private fun cliente(): Cliente {
        val campoNomeTutor = binding.nomeClienteFormulario.text
        val nomeTuotor = campoNomeTutor.toString()

        val campoNomeAnimal = binding.nomeAnimalFormulario.text
        val nomeAnimal = campoNomeAnimal.toString()

        val campoTelefone = binding.telefoneClienteFormulario.text
        val telefoneTutor = campoTelefone.toString()

        val campoEmail = binding.emailClienteFormulario.text
        val emailTotor = campoEmail.toString()

        val campoObservacao = binding.observacaoClienteFormulario.text
        val observacaoAnimal = campoObservacao.toString()


        return Cliente(
            nomeTutor = nomeTuotor,
            nomeAnimal = nomeAnimal,
            telefoneTutor = telefoneTutor,
            emailTutor = emailTotor,
            observacaoAnimal = observacaoAnimal,
        )
    }
}