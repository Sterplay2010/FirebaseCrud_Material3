package com.example.numerosromanos.realtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numerosromanos.databinding.ActivityListRealtimeBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class ListRealtimeActivity : AppCompatActivity(), RealTimeAdapter.Eventos {
    private lateinit var binding:ActivityListRealtimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseDatabase.getInstance()
            .getReference("usuarios")
            .get()
            .addOnSuccessListener {
                var data = mutableListOf<UserRealtime>()
                it.children.forEach{ user ->
                    data.add(UserRealtime(user.child("name").value.toString(),user.child("userName").value.toString(),user.child("firstSurname").value.toString(),user.child("firstSurname").value.toString(),user.child("email").value.toString()))
                }
                setData(data.toList())
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

    var adapter:RealTimeAdapter?=null

    fun setData(list: List<UserRealtime>){
        binding.recycler.layoutManager = LinearLayoutManager(this)
        adapter = RealTimeAdapter(this,this)
        binding.recycler.adapter = adapter
        adapter!!.submitList(list)
    }

    override fun onIntemClick(element: UserRealtime, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onStatusChange(element: UserRealtime, position: Int, status: Boolean) {
        TODO("Not yet implemented")
    }


}