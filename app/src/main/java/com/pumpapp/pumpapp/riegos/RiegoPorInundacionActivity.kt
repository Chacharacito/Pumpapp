package com.pumpapp.pumpapp.riegos

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.EXTRA_SISTEMA_UNIDADES
import com.pumpapp.pumpapp.MainActivity.Companion.RIEGO_INUNDACION
import com.pumpapp.pumpapp.MainActivity.Companion.SISTEMA_INTERNACIONAL
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecificaciones
import com.pumpapp.pumpapp.R

class RiegoPorInundacionActivity : AppCompatActivity() {

    companion object {
        private const val TEXTURA_ARENA = "Arena"
        private const val TEXTURA_FRANCO = "Franco"
        private const val TEXTURA_ARCILLA = "Arcilla"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riego_por_inundacion)

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

        val sistemaSeleccion = intent.getStringExtra(EXTRA_SISTEMA_UNIDADES) ?: SISTEMA_INTERNACIONAL

        findViewById<Button>(R.id.btn_atras2).setOnClickListener {
            lanzarActividadEspecificaciones(this@RiegoPorInundacionActivity, RIEGO_INUNDACION, sistemaSeleccion)
            MediaPlayer.create(this, R.raw.kara).start()
        }
    }
}
