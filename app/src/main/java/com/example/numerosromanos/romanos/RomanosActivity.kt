package com.example.numerosromanos.romanos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.numerosromanos.databinding.ActivityRomanosBinding

class RomanosActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRomanosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRomanosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.saveResult.setOnClickListener {
            binding.result.text = getNumber(Integer.parseInt(binding.number.text.toString()))
        }
    }

    fun getNumber(number:Int):String{
        var num = number
        var romano = ""
        var conversion = mapOf(
            "C" to 100,
            "XC" to 90,
            "L" to 50,
            "XL" to 40,
            "X" to 10,
            "IX" to 9,
            "V" to 5,
            "IV" to 4,
            "I" to 1,
        )

        conversion.forEach { item ->
            while (num >= item.value){
                romano += item.key
                num -= item.value
            }
        }

        return romano

    }
}