package com.pumpapp.pumpapp.riegos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecAccesorios
import com.pumpapp.pumpapp.R
import com.pumpapp.pumpapp.enums.TipoAccesorio
import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch

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

        val editTextPendienteTerreno = findViewById<EditText>(R.id.et_pendiente_del_terreno)
        val editTextLongitudSurco = findViewById<EditText>(R.id.et_longitud_del_surco)
        val editTextAnchoSurco = findViewById<EditText>(R.id.et_ancho_del_surco)
        val editTextTiempoRiego = findViewById<EditText>(R.id.et_tiempo_de_riego)
        val editTextInfiltracionSuelo = findViewById<EditText>(R.id.et_infiltracion_del_suelo)

        val stepperNumeroSurcos = findViewById<StepperTouch>(R.id.st_numero_de_surcos)
        stepperNumeroSurcos.minValue = 0
        stepperNumeroSurcos.sideTapEnabled = true
        stepperNumeroSurcos.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {

            }
        })

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

        findViewById<Button>(R.id.btn_siguiente2).setOnClickListener {
            val pendienteTerrenoTxt = editTextPendienteTerreno.text.toString()
            val longitudSurcoTxt = editTextLongitudSurco.text.toString()
            val anchoSurcoTxt = editTextAnchoSurco.text.toString()
            val tiempoRiegoTxt = editTextTiempoRiego.text.toString()
            val infiltracionSuelo = editTextInfiltracionSuelo.text.toString()


        }
    }
}
