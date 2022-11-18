package com.example.numerosromanos.realtime

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.numerosromanos.databinding.ItemcardBinding

class RealTimeAdapter(private val eventos: RealTimeAdapter.Eventos,context: Context): ListAdapter<UserRealtime, RealTimeAdapter.ViewHolder>(DiffutilCallback) {

    val ctx = context

    interface Eventos{
        fun onIntemClick(element: UserRealtime,position: Int)
        fun onStatusChange(element: UserRealtime,position: Int,status:Boolean)
    }

    inner class ViewHolder(private val binding: ItemcardBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(element:UserRealtime,position:Int) = with(binding){
            binding.name.text = element.name
            binding.first.text = element.firstSurname
            binding.last.text = element.lastSurname
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = ItemcardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    private object DiffutilCallback:DiffUtil.ItemCallback<UserRealtime>() {
        override fun areItemsTheSame(oldItem: UserRealtime, newItem: UserRealtime): Boolean {
            return  oldItem.userName == newItem.userName
        }

        override fun areContentsTheSame(oldItem: UserRealtime, newItem: UserRealtime): Boolean {
            return newItem == oldItem
        }

    }

}