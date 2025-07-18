package com.pumpapp.pumpapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CreditosActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_creditos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageViewAutor = findViewById<ImageView>(R.id.iv_autor)

        val spinnerAutores = findViewById<Spinner>(R.id.s_autores)
        val autores = arrayOf("Juan Sebastian Valencia Chara", "Juan Pablo Lopez Castillo", "José Luciano Mejia Arias")
        spinnerAutores.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            autores
        )
        spinnerAutores.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val image = when (position) {
                    0 -> R.drawable.chara
                    1 -> R.drawable.juanpablo
                    2 -> R.drawable.luciano
                    else -> R.drawable.chara
                }

                imageViewAutor.setImageResource(image)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}