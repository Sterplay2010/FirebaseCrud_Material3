package com.example.numerosromanos.firebase

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.numerosromanos.databinding.CardBinding

class PersonaAdapter(private val eventos: PersonaAdapter.Eventos,context: Context): ListAdapter<Persona, PersonaAdapter.ViewHolder>(DiffutilCallback) {

    val ctx = context

    interface Eventos{
        fun onIntemClick(element: Persona)
        fun onDeleteData(element: Persona)
    }

    inner class ViewHolder(private val binding: CardBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(element:Persona,position:Int) = with(binding){
            binding.nombre.text = element.nombre
            binding.nombre.setOnClickListener {
                this@PersonaAdapter.eventos.onIntemClick(element)
            }
            binding.eliminar.setOnClickListener {
                this@PersonaAdapter.eventos.onDeleteData(element)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var item = CardBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(item)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position),position)
    }

    private object DiffutilCallback:DiffUtil.ItemCallback<Persona>() {
        override fun areItemsTheSame(oldItem: Persona, newItem: Persona): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Persona, newItem: Persona): Boolean {
            return newItem == oldItem
        }

    }

}