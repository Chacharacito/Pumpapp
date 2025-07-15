package com.pumpapp.pumpapp

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    enum class Sistema { //Se usa para los calculos
        SI, IMPERIAL
    }

    companion object {
        const val PREFERENCES_CONTAINER = "pumpapp_preferences"
        const val PREF_POSICION_SISTEMA = "pref_posicion_sistema"

        const val EXTRA_SISTEMA_UNIDADES = "extra_sistema_unidades"
        const val EXTRA_SISTEMA_RIEGO = "extra_sistema_riego"

        const val RIEGO_GOTEO = 1
        const val RIEGO_ASPERSION = 2
        const val RIEGO_INUNDACION = 3

        const val SISTEMA_INTERNACIONAL = "Internacional"
        const val SISTEMA_IMPERIAL = "Imperial"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerSistema = findViewById<Spinner>(R.id.s_sistema_unidades)

        val opcionesSistema = arrayOf(SISTEMA_INTERNACIONAL, SISTEMA_IMPERIAL)
        spinnerSistema.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            opcionesSistema
        )

        // Cargar selección anterior
        val prefs = getSharedPreferences(PREFERENCES_CONTAINER, MODE_PRIVATE)
        val posicionGuardada = prefs.getInt(PREF_POSICION_SISTEMA, 0)
        spinnerSistema.setSelection(posicionGuardada)

        var sistemaSeleccionado = opcionesSistema[posicionGuardada]

        spinnerSistema.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sistemaSeleccionado = opcionesSistema[position]
                prefs.edit {
                    putInt(PREF_POSICION_SISTEMA, position)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val sonidoPasar = MediaPlayer.create(this, R.raw.kara)

        findViewById<ImageButton>(R.id.ib_goteo).apply {
            setImageResource(R.drawable.riego_por_goteo)
            setOnClickListener {
                lanzarActividadEspecificaciones(RIEGO_GOTEO, sistemaSeleccionado)
                sonidoPasar.start()
            }
        }

        findViewById<ImageButton>(R.id.ib_aspersion).apply {
            setImageResource(R.drawable.riego_por_aspersion)
            setOnClickListener {
                lanzarActividadEspecificaciones(RIEGO_ASPERSION, sistemaSeleccionado)
                sonidoPasar.start()
            }
        }

        findViewById<ImageButton>(R.id.ib_inundacion).apply {
            setImageResource(R.drawable.riego_por_inundacion)
            setOnClickListener {
                lanzarActividadEspecificaciones(RIEGO_INUNDACION, sistemaSeleccionado)
                sonidoPasar.start()
            }
        }
    }

    private fun lanzarActividadEspecificaciones(tipoRiego: Int, sistemaUnidades: String) {
        val intent = Intent(this, EspecificacionesHidraulicasActivity::class.java).apply {
            putExtra(EXTRA_SISTEMA_UNIDADES, sistemaUnidades)
            putExtra(EXTRA_SISTEMA_RIEGO, tipoRiego)
        }
        startActivity(intent)
    }
}
