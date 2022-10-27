package com.example.numerosromanos.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.numerosromanos.databinding.ActivityFirebaseRegistrarBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Integer.parseInt

class FirebaseRegistrarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFirebaseRegistrarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.guardar.setOnClickListener {
            saveData()
            cleanData()
        }

    }

    fun cleanData(){
        binding.nombre.text.clear()
        binding.materno.text.clear()
        binding.paterno.text.clear()
        binding.edad.text.clear()
        binding.hombre.isChecked = false
        binding.mujer.isChecked = false
    }

    fun saveData(){
        var sexo = ""
        if (binding.hombre.isChecked) sexo = "Hombre" else sexo = "Mujer"
        var data = mapOf(
            "nombre" to binding.nombre.text.toString(),
            "paterno" to binding.paterno.text.toString(),
            "materno" to binding.materno.text.toString(),
            "edad" to parseInt(binding.edad.text.toString()),
            "sexo" to sexo,
        )
        FirebaseFirestore
            .getInstance()
            .collection("usuarios")
            .document()
            .set(data)
            .addOnSuccessListener {
                Snackbar.make(binding.root,"Registro exitoso", Snackbar.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show()
            }
    }
}