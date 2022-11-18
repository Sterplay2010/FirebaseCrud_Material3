package com.example.numerosromanos.crudrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.databinding.ActivityCrudRealtimeBinding

class CrudRealtimeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCrudRealtimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.register.setOnClickListener {
            startActivity(Intent(this,CrudSaveRealtimeActivity::class.java))
        }
        binding.view.setOnClickListener {
            startActivity(Intent(this,CrudListRealtimeActivity::class.java))
        }

    }
}