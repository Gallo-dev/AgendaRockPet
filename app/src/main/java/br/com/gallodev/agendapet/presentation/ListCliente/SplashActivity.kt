package br.com.gallodev.agendapet.presentation.ListCliente

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import br.com.gallodev.agendapet.R
import br.com.gallodev.agendapet.databinding.ActivitySplashBinding

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
            val intent = Intent(this, ListaClientesActivity::class.java)
            startActivity(intent)
            finish()
        },4000)
    }
}