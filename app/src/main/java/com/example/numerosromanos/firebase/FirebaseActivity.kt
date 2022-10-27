package com.example.numerosromanos.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.databinding.ActivityFirebaseBinding

class FirebaseActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFirebaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registrar.setOnClickListener {
            startActivity(Intent(this,FirebaseRegistrarActivity::class.java))
        }

        binding.consultar.setOnClickListener {
            startActivity(Intent(this,FirebaseConsultarActivity::class.java))
        }
    }
}