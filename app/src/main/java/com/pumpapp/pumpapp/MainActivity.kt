package com.pumpapp.pumpapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sSistemaUnidades = findViewById<Spinner>(R.id.s_sistema_unidades)

        val sistemas = arrayOf("Internacional", "Imperial")
        sSistemaUnidades.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            sistemas
        )

        val ibRiegoPorGoteo = findViewById<ImageButton>(R.id.ib_goteo)
        ibRiegoPorGoteo.setImageResource(R.drawable.riego_por_goteo)

        ibRiegoPorGoteo.setOnClickListener {
            Toast.makeText(this, "Prueba 1", Toast.LENGTH_SHORT).show()
        }

        val ibRiegoPorAspersion = findViewById<ImageButton>(R.id.ib_aspersion)
        ibRiegoPorAspersion.setImageResource(R.drawable.riego_por_aspersion)

        ibRiegoPorAspersion.setOnClickListener {
            Toast.makeText(this, "Prueba 2", Toast.LENGTH_SHORT).show()
        }

        val ibRiegoPorInundacion = findViewById<ImageButton>(R.id.ib_inundacion)
        ibRiegoPorInundacion.setImageResource(R.drawable.riego_por_inundacion)

        ibRiegoPorInundacion.setOnClickListener {
            Toast.makeText(this, "Prueba 3", Toast.LENGTH_SHORT).show()
        }
    }
}