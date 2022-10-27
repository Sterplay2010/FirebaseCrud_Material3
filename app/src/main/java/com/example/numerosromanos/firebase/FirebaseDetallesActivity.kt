package com.example.numerosromanos.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.databinding.ActivityFirebaseDetallesBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import java.lang.Integer.parseInt

class FirebaseDetallesActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFirebaseDetallesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseDetallesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtener extras del intent
        var bundle = intent.extras
        var idPersona = bundle?.getString("id")
        setDataOnLoad(idPersona)
        binding.guardar.setOnClickListener {
            updateData(idPersona)
        }

    }

    fun updateData(id:String?){
        var sexo = ""
        if (binding.hombre.isChecked) sexo = "Hombre" else sexo = "Mujer"
        var data = mapOf(
            "nombre" to binding.nombre.text.toString(),
            "paterno" to binding.paterno.text.toString(),
            "materno" to binding.materno.text.toString(),
            "edad" to parseInt(binding.edad.text.toString()),
            "mujer" to sexo,
        )
        FirebaseFirestore
            .getInstance()
            .collection("usuarios")
            .document(id!!)
            .set(data, SetOptions.merge())
            .addOnSuccessListener {
                Snackbar.make(binding.root,"Se actualizo correctamente",Snackbar.LENGTH_SHORT).show()
                setDataOnLoad(id)
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"Ocurrio un error al actualizar",Snackbar.LENGTH_SHORT).show()
            }
    }

    fun setDataOnLoad(id:String?){
        FirebaseFirestore
            .getInstance()
            .collection("usuarios")
            .document(id!!)
            .get()
            .addOnSuccessListener { doc ->
                if (doc != null) {
                   binding.nombre.setText(doc["nombre"].toString())
                   binding.materno.setText(doc["materno"].toString())
                   binding.paterno.setText(doc["paterno"].toString())
                   binding.edad.setText(doc["edad"].toString())
                    if (doc["sexo"]=="Hombre") binding.hombre.isChecked = true else binding.mujer.isChecked = true
                } else {
                    Snackbar.make(binding.root,"El documento no se encontro",Snackbar.LENGTH_SHORT).show()
                }
            }
    }
}