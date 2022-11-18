package com.example.numerosromanos.crudrealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.numerosromanos.crudrealtime.adapter.UserRealtimeAdapter
import com.example.numerosromanos.crudrealtime.model.UserCrud
import com.example.numerosromanos.crudrealtime.utils.FirebaseProvider
import com.example.numerosromanos.databinding.ActivityCrudListRealtimeBinding
import com.example.numerosromanos.realtime.UserRealtime
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase

class CrudListRealtimeActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCrudListRealtimeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrudListRealtimeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun editUser(userCrud: UserCrud){
        val intent = Intent(this,CrudInfoRealtimeActivity::class.java)
        intent.putExtra("id",userCrud.id)
        startActivity(intent)
    }

    private fun deleteUser(userCrud: UserCrud){
        FirebaseProvider.deleteDataCrud(userCrud,binding.root) { initRecyclerView() }
    }

    private fun initRecyclerView(){
        FirebaseDatabase.getInstance()
            .getReference("users")
            .get()
            .addOnSuccessListener { data ->
                var info = mutableListOf<UserCrud>()
                data.children.forEach { user ->
                    info.add(
                        UserCrud(
                            user.child("id").value.toString(),
                            user.child("name").value.toString(),
                            user.child("lastName").value.toString(),
                            user.child("userName").value.toString(),
                        )
                    )
                }
                binding.recycler.layoutManager = LinearLayoutManager(this)
                binding.recycler.adapter = UserRealtimeAdapter(info.toList(),
                    {userCrud -> editUser(userCrud) },
                    {userCrud -> deleteUser(userCrud) })
            }
            .addOnFailureListener {
                Snackbar.make(binding.root,"${it.message}", Snackbar.LENGTH_SHORT).show()
            }
    }

}