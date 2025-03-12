package br.com.gallodev.agendapet.presentation.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.com.gallodev.agendapet.data.repository.Authentication
import br.com.gallodev.agendapet.databinding.ActivityLoginBinding
import br.com.gallodev.agendapet.presentation.ListCliente.ListaClientesActivity
import br.com.gallodev.agendapet.presentation.agendamentos.AgendamentoActivity
import br.com.gallodev.agendapet.presentation.formularioCliente.FormularioClienteActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val authentication by lazy { Authentication() }
    private val viewModel: LoginViewModel by viewModels {LoginViewModelFactory(authentication) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(binding.root)


        vaiParaCadastro()
        recuperarSenha()
        botaoEntrar()
    }
    private fun vaiParaCadastro(){
        binding.cadastreSe.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivity(intent)
        }
    }
    private fun recuperarSenha(){
        binding.esqueciASenha.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun botaoEntrar(){
        binding.botaoEntrar.setOnClickListener {
            val emailLogin = binding.emailLogin.text.toString().trim()
            if (emailLogin.isEmpty()) {
                binding.emailLogin.error = "Campo Obrigatório!"
                return@setOnClickListener
            }
            val senhaLogin = binding.senhaLogin.text.toString().trim()
            if (senhaLogin.isEmpty()) {
                binding.senhaLogin.error = "Campo Obrigatório!"
                return@setOnClickListener
                viewModel.login(emailLogin, senhaLogin)
            }

            viewModel.login(emailLogin, senhaLogin)

            viewModel.loginResult.observe(this) { result ->
                result.onSuccess {
                    val intent = Intent(this, AgendamentoActivity::class.java)
                    startActivity(intent)
                    finish()

                }.onFailure { exception ->
                    Toast.makeText(this, "Senha ou usuário invalido", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}