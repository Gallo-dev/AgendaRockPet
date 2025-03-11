package br.com.gallodev.agendapet.presentation.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.databinding.ActivityLoginBinding
import br.com.gallodev.agendapet.presentation.ListCliente.ListaClientesActivity
import br.com.gallodev.agendapet.presentation.formularioCliente.FormularioClienteActivity
import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.google.firebase.Firebase
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(binding.root)

        viewModel = ViewModelProvider(this,ViewModelFactory())[LoginViewModel::class.java]

        val emailEditText = binding.emailLogin.text.toString()
        val senhaEditText = binding.senhaLogin.text.toString()
        val loginButton = binding.botaoEntrar

        vaiParaCadastro()
        botaoSalvarLogin()
    }
    private fun vaiParaCadastro(){
        binding.cadastreSe.setOnClickListener {
            val intent = Intent(this, FormularioClienteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun botaoSalvarLogin(){
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

            viewModel.loginResult.observe(this) { result ->
                result.onSuccess {
                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                }.onFailure { exception ->
                    Toast.makeText(this, "Erro ao fazer login: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
            }
            val intent = Intent(this, ListaClientesActivity::class.java)

//            lifecycleScope.launch {
//                val email = binding.emailLogin.text.toString()
//                val senha = binding.senhaLogin.text.toString()
//                val loginSucesso = viewModel.fazerLogin(email, senha)
//            }
            startActivity(intent)
        }

    }
}