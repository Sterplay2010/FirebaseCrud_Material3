package com.example.numerosromanos.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numerosromanos.R
import com.example.numerosromanos.databinding.ActivityFirebaseConsultarBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Integer.parseInt

class FirebaseConsultarActivity : AppCompatActivity(),PersonaAdapter.Eventos {
    private lateinit var binding:ActivityFirebaseConsultarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirebaseConsultarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getData()
    }

    fun getData(){
        FirebaseFirestore
            .getInstance()
            .collection("usuarios")
            .get()
            .addOnSuccessListener { data ->
                var res = data.map { doc ->
                    Persona(doc.id,
                        doc["nombre"].toString(),
                        doc["paterno"].toString(),
                        doc["materno"].toString(),
                        parseInt(doc["edad"].toString()),
                        doc["sexo"].toString()
                    )
                }
                setData(res)
            }
            .addOnFailureListener {
                Toast.makeText(this, "Ocurrio un error", Toast.LENGTH_SHORT).show()
            }

    }

    fun setData(data:List<Persona>){
        var adapter:PersonaAdapter? = null
        binding.recycler.layoutManager = LinearLayoutManager(this)
        adapter = PersonaAdapter(this,this)
        binding.recycler.adapter = adapter
        adapter!!.submitList(data)
    }

    override fun onIntemClick(element: Persona) {
        startActivity(Intent(this,FirebaseDetallesActivity::class.java).putExtra("id",element.id))
    }

    override fun onDeleteData(element: Persona) {
        FirebaseFirestore
            .getInstance()
            .collection("usuarios")
            .document(element.id)
            .delete()
            .addOnSuccessListener {
                Snackbar.make(binding.root,"Eliminación exitosa",Snackbar.LENGTH_SHORT).show()
                getData()
            }
            .addOnFailureListener{
                Snackbar.make(binding.root,"Fallo al eliminar",Snackbar.LENGTH_SHORT).show()
            }
    }


}