package com.example.numerosromanos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.numerosromanos.authentication.LoginFirebaseActivity
import com.example.numerosromanos.crudrealtime.CrudRealtimeActivity
import com.example.numerosromanos.databinding.ActivityMainBinding
import com.example.numerosromanos.examen.GatoActivity
import com.example.numerosromanos.firebase.FirebaseActivity
import com.example.numerosromanos.mapas.MapsActivity
import com.example.numerosromanos.practica.LoginActivity
import com.example.numerosromanos.realtime.RealTimeActivity
import com.example.numerosromanos.romanos.RomanosActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import java.lang.Integer.parseInt

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Firebase Messaing Token
        getFirebaseToken()

        binding.firebase.setOnClickListener {
            startActivity(Intent(this,FirebaseActivity::class.java))
        }

        binding.romanos.setOnClickListener {
            startActivity(Intent(this,RomanosActivity::class.java))
        }

        binding.auth.setOnClickListener {
            startActivity(Intent(this,LoginFirebaseActivity::class.java))
        }

        binding.maps.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
        }

        binding.sharedPreferences.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }

        binding.realTime.setOnClickListener {
            startActivity(Intent(this,RealTimeActivity::class.java))
        }

        binding.practice.setOnClickListener {
            startActivity(Intent(this,CrudRealtimeActivity::class.java))
        }

        binding.gato.setOnClickListener {
            startActivity(Intent(this,GatoActivity::class.java))
        }
    }

    fun getFirebaseToken(){
        FirebaseMessaging
            .getInstance()
            .token
            .addOnCompleteListener {
                if (it.isSuccessful){
                    println("Log Notification: ${it.result}")
                }else{
                    Snackbar.make(binding.root,"Error => ${it.exception?.message}",Snackbar.LENGTH_SHORT).show()
                }
            }
    }


}