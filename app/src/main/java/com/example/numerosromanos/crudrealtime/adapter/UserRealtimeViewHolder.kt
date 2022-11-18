package com.example.numerosromanos.crudrealtime.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.numerosromanos.crudrealtime.model.UserCrud
import com.example.numerosromanos.databinding.ItemUserBinding

class UserRealtimeViewHolder(view:View):RecyclerView.ViewHolder(view) {

    val binding = ItemUserBinding.bind(view)

    fun render(userCrud: UserCrud,editUser:(UserCrud) -> Unit,deleteUser:(UserCrud) -> Unit){
        binding.name.text = userCrud.name
        binding.lastName.text = userCrud.lastName
        binding.userName.text = userCrud.userName
        binding.edit.setOnClickListener { editUser(userCrud) }
        binding.delete.setOnClickListener { deleteUser(userCrud) }
    }

}