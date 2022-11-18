package com.example.numerosromanos.realtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.text.set
import com.example.numerosromanos.databinding.ActivityRealTimeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class RealTimeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRealTimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRealTimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            saveUser(
                binding.nombre.editText?.text.toString(),
                binding.userName.editText?.text.toString(),
                binding.paterno.editText?.text.toString(),
                binding.materno.editText?.text.toString(),
                binding.email.editText?.text.toString(),
            )
        }

        binding.btnBuscar.setOnClickListener {
            getOne(binding.userName.editText!!.text.toString())
        }

        binding.btnGeneral.setOnClickListener {
            startActivity(Intent(this,ListRealtimeActivity::class.java))
        }
    }

    fun saveUser(name:String, userName:String, firstSurname:String,lastSurname:String,email:String){
        var model = UserRealtime(name,userName, firstSurname, lastSurname, email)
        FirebaseDatabase
            .getInstance()
            .getReference("usuarios")
            .child(model.userName)
            .setValue(model)
            .addOnSuccessListener {
                Snackbar.make(binding.root,"Registro Correcto",Snackbar.LENGTH_SHORT).show()
                cleanData()
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"Algo salio mal",Snackbar.LENGTH_SHORT).show()
            }
    }


    fun cleanData(){
        binding.nombre.editText?.text?.clear()
        binding.userName.editText?.text?.clear()
        binding.paterno.editText?.text?.clear()
        binding.materno.editText?.text?.clear()
        binding.email.editText?.text?.clear()
    }


    fun getOne(userName:String){
        FirebaseDatabase
            .getInstance()
            .getReference("usuarios")
            .child(userName)
            .get()
            .addOnSuccessListener {
                if (it.exists()){
                    var name = it.child("name").value.toString()
                    var paterno = it.child("firstSurname").value.toString()
                    var materno = it.child("lastSurname").value.toString()
                    var email = it.child("email").value.toString()

                    binding.nombre.editText!!.setText(name)
                    binding.paterno.editText!!.setText(paterno)
                    binding.materno.editText!!.setText(materno)
                    binding.email.editText!!.setText(email)
                }
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"${it.message}",Snackbar.LENGTH_SHORT).show()
            }
    }

    fun getAll(){
        FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .get()
            .addOnSuccessListener {
                it.children.forEach{ user ->
                    println("LOG USER: ${user.child("name").value}")
                    println("LOG USER: ${user.child("firstSurname").value}")
                    println("LOG USER: ${user.child("lastSurname").value}")
                    println("LOG USER: ${user.child("email").value}")
                }
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"${it.message}",Snackbar.LENGTH_SHORT).show()
            }
    }
}