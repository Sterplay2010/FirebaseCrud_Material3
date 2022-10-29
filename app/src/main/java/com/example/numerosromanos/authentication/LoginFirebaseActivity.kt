package com.example.numerosromanos.authentication

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.numerosromanos.databinding.ActivityLoginFirebaseBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginFirebaseActivity : AppCompatActivity() {
    private lateinit var google:GoogleSignInClient
    private lateinit var binding: ActivityLoginFirebaseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginFirebaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Variable para google
        val options = GoogleSignInOptions.Builder(
            GoogleSignInOptions.DEFAULT_SIGN_IN
        ).requestIdToken("903170697747-jh93utjqq2kjpumh0g2oelnb2mhlv45j.apps.googleusercontent.com")
            .requestEmail()
            .build()

        google = GoogleSignIn.getClient(this,options)

        binding.registrar.setOnClickListener {
            FirebaseAuth
                .getInstance()
                .createUserWithEmailAndPassword("sterplay2010@yahoo.com","12345678")
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        Snackbar.make(binding.root,"Registro exitoso", Snackbar.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(binding.root,"Error al registrar", Snackbar.LENGTH_SHORT).show()
                    }
                }
        }

        binding.iniciar.setOnClickListener {
            FirebaseAuth
                .getInstance()
                .signInWithEmailAndPassword(binding.email.text.toString(),binding.pass.text.toString())
                .addOnCompleteListener{
                    if (it.isSuccessful){
                        Snackbar.make(binding.root,"Registro exitoso",Snackbar.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(binding.root,"Error al registrar",Snackbar.LENGTH_SHORT).show()
                    }
                }
        }

        binding.veri.setOnClickListener {
            var user = FirebaseAuth
                .getInstance()
                .currentUser
            if (user!=null)
                Snackbar.make(binding.root,"Usuario => ${user.email}",Snackbar.LENGTH_SHORT).show()
            else
                Snackbar.make(binding.root,"Sin inicio de sesión",Snackbar.LENGTH_SHORT).show()
        }

        binding.salir.setOnClickListener {
            FirebaseAuth.getInstance()
                .signOut()
            var user = FirebaseAuth
                .getInstance()
                .currentUser
            if (user!=null)
                Snackbar.make(binding.root,"Error al cerrar sesión",Snackbar.LENGTH_SHORT).show()
            else
                Snackbar.make(binding.root,"Inicia sesión nuevamente",Snackbar.LENGTH_SHORT).show()
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
                        Snackbar.make(binding.root,"Usuario: ${cuenta.displayName}",Snackbar.LENGTH_SHORT).show()
                    }else{
                        Snackbar.make(binding.root,"Error al cerrar sesión",Snackbar.LENGTH_SHORT).show()
                    }
                }
        }
    }
}