package com.example.numerosromanos.examen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.numerosromanos.crudrealtime.model.UserCrud
import com.example.numerosromanos.databinding.ActivityGatoBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class GatoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityGatoBinding

    private var gameList = mutableListOf<GameModel>()
    private var playerList = mutableListOf<String>()
    private var ganador = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGatoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.X.setOnClickListener {
            FirebaseDatabase
                .getInstance()
                .getReference("Players")
                .child("Users")
                .get()
                .addOnSuccessListener {
                    if (it.exists()){
                        if (it.children.toList()[0].value.toString() == "X"){
                            Toast.makeText(applicationContext, "Ya hay un jugador X", Toast.LENGTH_SHORT).show()
                        }else{
                            setPlayer("X")
                        }
                    }else{
                        setPlayer("X")
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_SHORT).show()
                }
            GameUtils.player = "X"
        }
        binding.O.setOnClickListener {
            FirebaseDatabase
                .getInstance()
                .getReference("Players")
                .child("Users")
                .get()
                .addOnSuccessListener {
                    if (it.exists()){
                        if (it.children.toList()[0].value.toString() == "O"){
                            Toast.makeText(applicationContext, "Ya hay un jugador O", Toast.LENGTH_SHORT).show()
                        }else{
                            setPlayer("O")
                        }
                    }else{
                        setPlayer("O")
                    }
                }
                .addOnFailureListener {
                    Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_SHORT).show()
                }
            GameUtils.player = "O"
        }

        binding.one.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("one")
        }
        binding.two.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("two")
        }
        binding.three.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("three")
        }
        binding.four.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("four")
        }
        binding.five.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("five")
        }
        binding.six.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("six")
        }
        binding.seven.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("seven")
        }
        binding.eight.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("eight")
        }
        binding.nine.setOnClickListener {
            checkGameTable()
            saveMovementPlayer("nine")
        }

        fetchLastMovements()
    }


    fun checkGameTable(){
        when(true){
            (binding.one.text == "X" && binding.two.text == "X" && binding.three.text == "X") -> {ganador = "X"}
            (binding.four.text == "X" && binding.five.text == "X" && binding.six.text == "X") -> {ganador = "X"}
            (binding.seven.text == "X" && binding.eight.text == "X" && binding.nine.text == "X")  -> {ganador = "X"}
            (binding.one.text == "X" && binding.five.text == "X" && binding.nine.text == "X")  -> {ganador = "X"}
            (binding.seven.text == "X" && binding.five.text == "X" && binding.three.text == "X")  -> {ganador = "X"}
            (binding.one.text == "X" && binding.four.text == "X" && binding.seven.text == "X")  -> {ganador = "X"}
            (binding.two.text == "X" && binding.five.text == "X" && binding.eight.text == "X") -> {ganador = "X"}
            (binding.three.text == "X" && binding.six.text == "X" && binding.nine.text == "X")  -> {ganador = "X"}
            (binding.one.text == "O" && binding.two.text == "O" && binding.three.text == "O")  -> {ganador = "O"}
            (binding.four.text == "O" && binding.five.text == "O" && binding.six.text == "O") -> {ganador = "O"}
            (binding.seven.text == "O" && binding.eight.text == "O" && binding.nine.text == "O") -> {ganador = "O"}
            (binding.one.text == "O" && binding.five.text == "O" && binding.nine.text == "O") -> {ganador = "O"}
            (binding.seven.text == "O" && binding.five.text == "O" && binding.three.text == "O") -> {ganador = "O"}
            (binding.one.text == "O" && binding.four.text == "O" && binding.seven.text == "O") -> {ganador = "O"}
            (binding.two.text == "O" && binding.five.text == "O" && binding.eight.text == "O") -> {ganador = "O"}
            (binding.three.text == "O" && binding.six.text == "O" && binding.nine.text == "O") -> {ganador = "O"}
            else -> {}
        }
    }

    fun setPlayer(player:String){
        playerList.add(player)
        FirebaseDatabase
            .getInstance()
            .getReference("Players")
            .child("Users")
            .setValue(playerList)
            .addOnSuccessListener {
                binding.X.isEnabled = false
                binding.O.isEnabled = false
                Toast.makeText(applicationContext, "Eres el jugador ${player}", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(applicationContext, "Error al seleccionar usuario", Toast.LENGTH_SHORT).show()
            }
    }



    fun fetchLastMovements(){
        FirebaseDatabase.getInstance()
            .getReference("GAME")
            .child("TableGame")
            .limitToLast(1)
            .addChildEventListener(object:ChildEventListener{
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    var posicion = snapshot.child("posicion").value.toString()
                    var jugador = snapshot.child("jugador").value.toString()
                    if (ganador != "O" && ganador != "X"){
                        if (jugador != GameUtils.player){
                            gameList.add(GameModel(posicion,jugador))
                            when(posicion){
                                "one"->{
                                    binding.one.text = jugador
                                    binding.one.isEnabled = false
                                    binding.one.isClickable = false
                                }
                                "two"->{
                                    binding.two.text = jugador
                                    binding.two.isEnabled = false
                                    binding.two.isClickable = false
                                }
                                "three"->{
                                    binding.three.text = jugador
                                    binding.three.isEnabled = false
                                    binding.three.isClickable = false
                                }
                                "four"->{
                                    binding.four.text = jugador
                                    binding.four.isEnabled = false
                                    binding.four.isClickable = false
                                }
                                "five"->{
                                    binding.five.text = jugador
                                    binding.five.isEnabled = false
                                    binding.five.isClickable = false
                                }
                                "six"->{
                                    binding.six.text = jugador
                                    binding.six.isEnabled = false
                                    binding.six.isClickable = false
                                }
                                "seven"->{
                                    binding.seven.text = jugador
                                    binding.seven.isEnabled = false
                                    binding.seven.isClickable = false
                                }
                                "eight"->{
                                    binding.eight.text = jugador
                                    binding.eight.isEnabled = false
                                    binding.eight.isClickable = false
                                }
                                "nine"->{
                                    binding.nine.text = jugador
                                    binding.nine.isEnabled = false
                                    binding.nine.isClickable = false
                                }
                            }
                        }
                    }else{
                        Toast.makeText(applicationContext, "El ganador es el jugador $ganador", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                }

                override fun onCancelled(error: DatabaseError) {
                }

            })
    }

    fun saveMovementPlayer(idBtn:String){

        if (ganador != "X" && ganador != "O"){
            gameList.add(GameModel(idBtn,GameUtils.player))
            FirebaseDatabase.getInstance()
                .getReference("GAME")
                .child("TableGame")
                .setValue(gameList)
                .addOnSuccessListener {
                    when(idBtn){
                        "one"->{
                            binding.one.text = GameUtils.player
                            binding.one.isEnabled = false
                            binding.one.isClickable = false
                        }
                        "two"->{
                            binding.two.text = GameUtils.player
                            binding.two.isEnabled = false
                            binding.two.isClickable = false
                        }
                        "three"->{
                            binding.three.text = GameUtils.player
                            binding.three.isEnabled = false
                            binding.three.isClickable = false
                        }
                        "four"->{
                            binding.four.text = GameUtils.player
                            binding.four.isEnabled = false
                            binding.four.isClickable = false
                        }
                        "five"->{
                            binding.five.text = GameUtils.player
                            binding.five.isEnabled = false
                            binding.five.isClickable = false
                        }
                        "six"->{
                            binding.six.text = GameUtils.player
                            binding.six.isEnabled = false
                            binding.six.isClickable = false
                        }
                        "seven"->{
                            binding.seven.text = GameUtils.player
                            binding.seven.isEnabled = false
                            binding.seven.isClickable = false
                        }
                        "eight"->{
                            binding.eight.text = GameUtils.player
                            binding.eight.isEnabled = false
                            binding.eight.isClickable = false
                        }
                        "nine"->{
                            binding.nine.text = GameUtils.player
                            binding.nine.isEnabled = false
                            binding.nine.isClickable = false
                        }
                        else->{
                            Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()}
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(applicationContext, "Ocurrio un error", Toast.LENGTH_SHORT).show()
                }
        }else{
            Toast.makeText(this, "El ganador es el jugador $ganador", Toast.LENGTH_SHORT).show()
        }
    }



}