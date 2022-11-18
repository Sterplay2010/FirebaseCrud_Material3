package com.example.numerosromanos.crudrealtime.utils

import android.view.View
import com.example.numerosromanos.crudrealtime.model.UserCrud
import com.example.numerosromanos.databinding.ActivityCrudInfoRealtimeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class FirebaseProvider {
    companion object{
        fun saveDataCrud(user: UserCrud,view:View){
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.id)
                .setValue(user)
                .addOnSuccessListener {
                    Snackbar.make(view,"Registro Correcto", Snackbar.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Snackbar.make(view,"Algo salio mal", Snackbar.LENGTH_SHORT).show()
                }
        }

        // Se definen variables en la funciones cuando vas a utilizar algo durante toda la clase val listUser:List<User>
        // Si solo voy a pasar a su padre o a heredar no iria la variable pero igual se lo puedes poner

        fun deleteDataCrud(user: UserCrud,view: View,initRecyclerView:() -> Unit){
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.id)
                .removeValue()
                .addOnSuccessListener {
                    Snackbar.make(view,"Eliminación Exitosa", Snackbar.LENGTH_SHORT).show()
                    initRecyclerView()
                }
                .addOnFailureListener {
                    Snackbar.make(view,"${it.message}", Snackbar.LENGTH_SHORT).show()
                }
        }

        fun updateUserInfo(idUser: String,view: View,initGetOne:(String) -> Unit){
            val binding = ActivityCrudInfoRealtimeBinding.bind(view)
            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(idUser)
                .updateChildren(mapOf(
                    "id" to idUser,
                    "name" to binding.name.editText!!.text.toString(),
                    "lastName" to binding.lastName.editText!!.text.toString(),
                    "userName" to binding.username.editText!!.text.toString(),
                ))
                .addOnSuccessListener {
                    Snackbar.make(binding.root,"Actualización exitosa",Snackbar.LENGTH_SHORT).show()
                    initGetOne(idUser)
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root,"Ocurrio un error",Snackbar.LENGTH_SHORT).show()
                }
        }
    }
}