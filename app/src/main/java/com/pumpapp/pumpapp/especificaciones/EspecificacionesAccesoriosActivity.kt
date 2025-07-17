package com.pumpapp.pumpapp.especificaciones

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecHidraulicas
import com.pumpapp.pumpapp.MainActivity.Companion.obtenerSistemaRiegoDesdePrefs
import com.pumpapp.pumpapp.R
import com.pumpapp.pumpapp.enums.SistemaRiego
import com.pumpapp.pumpapp.riegos.RiegoGoteoActivity
import com.pumpapp.pumpapp.riegos.RiegoInundacionActivity

class EspecificacionesAccesoriosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_especificaciones_accesorios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<Button>(R.id.btn_siguiente3).setOnClickListener {
            val sistemaRiego =
                obtenerSistemaRiegoDesdePrefs(this@EspecificacionesAccesoriosActivity)

            val intent = when (sistemaRiego) {
                SistemaRiego.RIEGO_GOTEO -> Intent(this, RiegoGoteoActivity::class.java)
                SistemaRiego.RIEGO_INUNDACION -> Intent(this, RiegoInundacionActivity::class.java)
            }

            startActivity(intent)
            MediaPlayer.create(this, R.raw.kara).start()
        }

        findViewById<Button>(R.id.btn_atras3).setOnClickListener {
            lanzarActividadEspecHidraulicas(this@EspecificacionesAccesoriosActivity)
        }
    }
}