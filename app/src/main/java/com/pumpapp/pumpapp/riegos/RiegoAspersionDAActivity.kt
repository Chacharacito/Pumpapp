package com.pumpapp.pumpapp.riegos

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.EXTRA_SISTEMA_UNIDADES
import com.pumpapp.pumpapp.MainActivity.Companion.SISTEMA_INTERNACIONAL
import com.pumpapp.pumpapp.R

class RiegoAspersionDAActivity : AppCompatActivity() {

    companion object {
        const val ALGODON = "cultivoAlgodon"
        const val CAÑA = "cultivoCaña"
        const val PIÑA = "cultivoPiña"
        const val ARROZ = "cultivoArroz"

        const val CAPACIDAD_DE_CAMPO = 0
        const val PUNTO_MARCHITEZ_PERMA = 0
        const val DENSIDAD_APARENTE = 0
        const val FACTOR_AGOTAMIENTO = 0

        const val PROF_ALGODON = 0.3
        const val PROF_CAÑA = 0.5
        const val PROF_PIÑA = 0.2
        const val PROF_ARROZ = 0.1

        private var profundidaCultivo: Double = 0.0

        const val LARA = 0
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_riego_aspersion_da)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val editTextEvapotranspiracion = findViewById<EditText>(R.id.et_evapo)
        val editTextIpmax = findViewById<EditText>(R.id.et_ip)
        val editTextDensidadAparente = findViewById<EditText>(R.id.et_da)

        val spinnerCultivo = findViewById<Spinner>(R.id.s_tipo_cultivo)

        val cultivo = arrayOf(ALGODON, ARROZ, CAÑA, PIÑA)
        spinnerCultivo.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            cultivo
        )

        spinnerCultivo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                profundidaCultivo = when (cultivo[position]) {
                    ALGODON -> PROF_ALGODON
                    ARROZ -> PROF_ARROZ
                    CAÑA -> PROF_CAÑA
                    else -> PROF_PIÑA
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                profundidaCultivo = PROF_ALGODON
            }
        }

        val sistemaSeleccion = intent.getStringExtra(EXTRA_SISTEMA_UNIDADES) ?: SISTEMA_INTERNACIONAL

        if (sistemaSeleccion == SISTEMA_INTERNACIONAL) {
            editTextEvapotranspiracion.hint = "mm/dia"
            editTextIpmax.hint = "mm/hora"
            editTextDensidadAparente.hint = "gr/cm³"

        } else {
            editTextEvapotranspiracion.hint = "in/dia"
            editTextIpmax.hint = "in/hora"
            editTextDensidadAparente.hint = "in/cm³"

        }

        val sonidoPasar = MediaPlayer.create(this, R.raw.kara)

        findViewById<Button>(R.id.btn_siguiente5).setOnClickListener {
            val evapotranspiracionTxt = editTextEvapotranspiracion.text.toString()
            val ipTxt = editTextIpmax.text.toString()
            val densidadTxt = editTextDensidadAparente.text.toString()

            if (evapotranspiracionTxt.isEmpty() || ipTxt.isEmpty() || densidadTxt.isEmpty()) {
                Toast.makeText(this, "No deben haber campos vacios", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, RiegoAspersionAspersorActivity::class.java)

        }
    }
}