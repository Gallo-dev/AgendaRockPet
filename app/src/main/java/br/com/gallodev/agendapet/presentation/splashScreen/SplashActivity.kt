package br.com.gallodev.agendapet.presentation.splashScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.gallodev.agendapet.databinding.ActivitySplashBinding
import br.com.gallodev.agendapet.presentation.ListCliente.ListaClientesActivity
import br.com.gallodev.agendapet.presentation.Login.LoginActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        supportActionBar?.hide()
        setContentView(binding.root)

        setupAction()
    }
    private fun setupAction(){
        binding.animationView.postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },4000)
    }
}