package com.pumpapp.pumpapp.sistemas

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity
import com.pumpapp.pumpapp.R

class RiegoPorInundacionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riego_por_inundacion)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sTexturaDelSuelo = findViewById<Spinner>(R.id.s_textura_del_suelo)

        val texturasDelSuelo = arrayOf("Arena", "Franco", "Arcilla")
        sTexturaDelSuelo.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            texturasDelSuelo
        )

        val btnAtras = findViewById<Button>(R.id.btn_atras2)
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}