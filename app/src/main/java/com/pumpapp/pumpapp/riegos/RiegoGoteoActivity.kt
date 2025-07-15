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
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecificacionesHidraulicas
import com.pumpapp.pumpapp.R

class RiegoGoteoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riego_goteo)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        /**
         *  val sistemaRiego = intent.getIntExtra(EXTRA_SISTEMA_RIEGO, RIEGO_GOTEO)
         *             val intent = when (sistemaRiego) {
         *                 RIEGO_INUNDACION -> Intent(this, RiegoInundacionActivity::class.java)
         *                 RIEGO_GOTEO -> Intent(this, RiegoGoteoActivity::class.java)
         *                 else -> Intent(this, MainActivity::class.java)
         *             }
         *
         */

        val sistemaUnidades = intent.getStringExtra(EXTRA_SISTEMA_UNIDADES) ?: SISTEMA_INTERNACIONAL

        findViewById<Button>(R.id.btn_atras7).setOnClickListener {
            lanzarActividadEspecificacionesHidraulicas(this@RiegoGoteoActivity, RIEGO_GOTEO, sistemaUnidades)
            MediaPlayer.create(this, R.raw.kara).start()
        }
    }
}