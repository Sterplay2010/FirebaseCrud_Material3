package com.example.numerosromanos.notifications

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.databinding.ActivityNotificationsBinding

class NotificationsActivity : AppCompatActivity() {
    private lateinit var binding:ActivityNotificationsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}