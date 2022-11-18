package com.example.numerosromanos.crudrealtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.crudrealtime.model.UserCrud
import com.example.numerosromanos.crudrealtime.utils.FirebaseProvider
import com.example.numerosromanos.databinding.ActivityCrudSaveRealtimeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class CrudSaveRealtimeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCrudSaveRealtimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudSaveRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.save.setOnClickListener { saveDate() }
    }

    fun getRandomString(length: Int) : String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    fun saveDate(){
        var user = UserCrud(
            getRandomString(6),
            binding.name.editText!!.text.toString(),
            binding.lastName.editText!!.text.toString(),
            binding.username.editText!!.text.toString(),
        )
        FirebaseProvider.saveDataCrud(user,binding.root)
        cleanData()
    }

    fun cleanData(){
        binding.name.editText!!.text.clear()
        binding.lastName.editText!!.text.clear()
        binding.username.editText!!.text.clear()
    }
}