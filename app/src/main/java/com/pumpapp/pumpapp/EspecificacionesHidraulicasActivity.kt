package com.pumpapp.pumpapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class EspecificacionesHidraulicasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_especificaciones_hidraulicas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sMaterialDeLaTuberia = findViewById<Spinner>(R.id.s_material_de_la_tuberia)

        val materiales = arrayOf("PVC", "Acero", "Plástico", "Hierro")
        sMaterialDeLaTuberia.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            materiales
        )

        val btnAtras = findViewById<Button>(R.id.btn_atras)
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}