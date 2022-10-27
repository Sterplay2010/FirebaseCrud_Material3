package com.example.numerosromanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.numerosromanos.databinding.ActivityMainBinding
import com.example.numerosromanos.firebase.FirebaseActivity
import com.example.numerosromanos.romanos.RomanosActivity
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.firebase.setOnClickListener {
            startActivity(Intent(this,FirebaseActivity::class.java))
        }

        binding.romanos.setOnClickListener {
            startActivity(Intent(this,RomanosActivity::class.java))
        }
    }


}