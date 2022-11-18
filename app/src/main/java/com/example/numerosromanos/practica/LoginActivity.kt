package com.example.numerosromanos.practica

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.numerosromanos.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var google: GoogleSignInClient
    var recordar = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        recordar = sharedPreferences.getBoolean("RECORDAR", false)

        if (recordar){
            startActivity(Intent(this,InfoLoginActivity::class.java))
        }

        binding.iniciar.setOnClickListener {
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(binding.email.text.toString(),binding.pass.text.toString())
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("RECORDAR", true)
                        editor.putString("PROVEDOR","Correo Electronico")
                        editor.putString("EMAIL",binding.email.text.toString())
                        startActivity(Intent(this,InfoLoginActivity::class.java))
                    }else{
                        Snackbar.make(binding.root,"Error al iniciar sesión", Snackbar.LENGTH_SHORT).show()
                    }
                }

        }

        binding.google.setOnClickListener {
            google.signOut()
            google.silentSignIn()
            val intent = google.signInIntent
            getResult.launch(intent)
        }

    }

    val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if (it.resultCode== Activity.RESULT_OK){

            val tarea = GoogleSignIn
                .getSignedInAccountFromIntent(it.data)

            var cuenta = tarea.getResult(ApiException::class.java)
            var credenciales = GoogleAuthProvider
                .getCredential(cuenta.idToken,null)

            FirebaseAuth.getInstance()
                .signInWithCredential(credenciales)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putBoolean("RECORDAR", true)
                        editor.putString("PROVEDOR","Google")
                        editor.putString("EMAIL",cuenta.email)
                        startActivity(Intent(this,InfoLoginActivity::class.java))
                    }else{
                        Snackbar.make(binding.root,"Error al cerrar sesión",Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}