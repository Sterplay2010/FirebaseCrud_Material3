package com.example.numerosromanos.crudrealtime.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.numerosromanos.R
import com.example.numerosromanos.crudrealtime.model.UserCrud

class UserRealtimeAdapter(
    private val userRealTimeList:List<UserCrud>, private val editUser:(UserCrud) -> Unit,
    private val deleteUser:(UserCrud) -> Unit):RecyclerView.Adapter<UserRealtimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserRealtimeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserRealtimeViewHolder(layoutInflater.inflate(R.layout.item_user,parent,false))
    }

    override fun onBindViewHolder(holder: UserRealtimeViewHolder, position: Int) {
        val item = userRealTimeList[position]
        holder.render(item, editUser, deleteUser)
    }

    override fun getItemCount(): Int = userRealTimeList.size
}