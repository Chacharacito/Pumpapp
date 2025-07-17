package com.pumpapp.pumpapp.riegos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecAccesorios
import com.pumpapp.pumpapp.R

class RiegoInundacionActivity : AppCompatActivity() {

    companion object {
        private const val TEXTURA_ARENA = "Arena"
        private const val TEXTURA_FRANCO = "Franco"
        private const val TEXTURA_ARCILLA = "Arcilla"


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riego_inundacion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerTexturaSuelo = findViewById<Spinner>(R.id.s_textura_del_suelo)

        val texturas = arrayOf(TEXTURA_ARENA, TEXTURA_FRANCO, TEXTURA_ARCILLA)
        spinnerTexturaSuelo.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            texturas
        )

        findViewById<Button>(R.id.btn_atras2).setOnClickListener {
            lanzarActividadEspecAccesorios(this@RiegoInundacionActivity)
        }
    }
}
