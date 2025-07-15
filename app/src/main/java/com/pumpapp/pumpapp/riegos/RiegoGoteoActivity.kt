package com.pumpapp.pumpapp.riegos

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.EXTRA_SISTEMA_UNIDADES
import com.pumpapp.pumpapp.MainActivity.Companion.RIEGO_GOTEO
import com.pumpapp.pumpapp.MainActivity.Companion.SISTEMA_INTERNACIONAL
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecificaciones
import com.pumpapp.pumpapp.R

class RiegoGoteoActivity : AppCompatActivity() {
    companion object {
        private const val RUGOSIDAD_HIERRO = 1.5e-4
        private const val RUGOSIDAD_PVC = 2.3e-6
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riego_goteo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val sistemaSeleccion = intent.getStringExtra(EXTRA_SISTEMA_UNIDADES) ?: SISTEMA_INTERNACIONAL

        findViewById<Button>(R.id.btn_atras7).setOnClickListener {
            lanzarActividadEspecificaciones(this@RiegoGoteoActivity, RIEGO_GOTEO, sistemaSeleccion)
            MediaPlayer.create(this, R.raw.kara).start()
        }
    }
}