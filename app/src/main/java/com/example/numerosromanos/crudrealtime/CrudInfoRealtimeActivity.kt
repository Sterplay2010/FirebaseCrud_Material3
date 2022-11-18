package com.example.numerosromanos.crudrealtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.crudrealtime.model.UserCrud
import com.example.numerosromanos.crudrealtime.utils.FirebaseProvider
import com.example.numerosromanos.databinding.ActivityCrudInfoRealtimeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class CrudInfoRealtimeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCrudInfoRealtimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudInfoRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Intent Extras
        var bundle = intent.extras
        var idUser = bundle?.getString("id")
        initGetOne(idUser!!)
        binding.update.setOnClickListener {
            FirebaseProvider.updateUserInfo(idUser,binding.root) { initGetOne(idUser) }
        }
    }

    fun initGetOne(idUser:String){
        FirebaseDatabase
            .getInstance()
            .getReference("users")
            .child(idUser)
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    var name = it.child("name").value.toString()
                    var lastName = it.child("lastName").value.toString()
                    var userName = it.child("userName").value.toString()
                    binding.name.editText!!.setText(name)
                    binding.lastName.editText!!.setText(lastName)
                    binding.username.editText!!.setText(userName)
                }
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_SHORT).show()
            }
    }


}