package br.com.gallodev.agendapet.presentation.Login

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.databinding.ActivityLoginBinding
import br.com.gallodev.agendapet.presentation.ListCliente.ListaClientesActivity
import br.com.gallodev.agendapet.presentation.formularioCliente.FormularioClienteActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(binding.root)

        vaiParaCadastro()
        vaiParaLista()
    }
    private fun vaiParaCadastro(){
        binding.cadastreSe.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivity(intent)
        }
    }
    private fun vaiParaLista(){
        binding.botaoEntrar.setOnClickListener {
            val intent = Intent(this, ListaClientesActivity::class.java)
            startActivity(intent)
        }

    }
}