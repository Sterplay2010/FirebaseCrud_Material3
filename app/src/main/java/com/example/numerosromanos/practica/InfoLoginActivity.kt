package com.example.numerosromanos.practica

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.databinding.ActivityInfoLoginBinding

class InfoLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        println("Log => Si llego")
        var email = sharedPreferences.getString("EMAIL", "")
        var provedor = sharedPreferences.getString("PROVEDOR", "")

        binding.correo.text = email
        binding.provedor.text = provedor

        binding.salir.setOnClickListener {
            val editor : SharedPreferences.Editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            //Salir de la sesión
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}