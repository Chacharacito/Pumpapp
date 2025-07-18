package com.pumpapp.pumpapp.riegos

import android.content.Context
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
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecAccesorios
import com.pumpapp.pumpapp.MainActivity.Companion.obtenerSistemaUnidadesDesdePrefs
import com.pumpapp.pumpapp.R
import com.pumpapp.pumpapp.ResumenActivity
import com.pumpapp.pumpapp.calculos.CalculoPorGoteo
import com.pumpapp.pumpapp.calculos.CalculosGenerales
import com.pumpapp.pumpapp.enums.SistemaUnidades
import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch
import kotlin.math.PI
import kotlin.math.pow

class RiegoGoteoActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "riego_goteo"

        private const val MATERIAL_PLASTICO = "PLASTICO"
        private const val MATERIAL_PEAD = "PEAD"

        const val EXTRA_NUMERO_REYNOLDS = "nro_reynolds"
        const val EXTRA_FACTOR_FRICCION = "factor_friccion"
        const val EXTRA_PERDIDAS_CINTILLA = "perdidas_cintilla"
        const val EXTRA_VELOCIDAD = "velocidad"
        const val EXTRA_DIAMETRO_CINTILLA = "diametro_cintilla"

        const val RUGOSIDAD_PLASTICO = 3.0e-7
        const val RUGOSIDAD_PEAD = 1.5e-3

        private var rugosidad = 0.0

        fun limpiarPreferencias(context: Context) {
            context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit {
                clear()
            }
        }

        fun obtenerNumeroReynolds(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_NUMERO_REYNOLDS, 0f).toDouble()
        }

        fun obtenerFactorFriccion(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_FACTOR_FRICCION, 0f).toDouble()
        }

        fun obtenerDiemtroCintilla(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_DIAMETRO_CINTILLA, 0f).toDouble()
        }

        fun obtenerPerdidasCintilla(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_PERDIDAS_CINTILLA, 0f).toDouble()
        }

        fun obtenerVelocidad(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_VELOCIDAD, 0f).toDouble()
        }
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

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        val editTextDiametroCintilla = findViewById<EditText>(R.id.et_diametro_cintilla)
        val editTextLongitudCintilla = findViewById<EditText>(R.id.et_longitud_de_cintilla)
        val editTextCaudalRiego = findViewById<EditText>(R.id.et_caudal_de_riego)

        val stepperNumeroCanales = findViewById<StepperTouch>(R.id.st_numero_de_canales)
        stepperNumeroCanales.minValue = 0
        stepperNumeroCanales.maxValue = 10
        stepperNumeroCanales.sideTapEnabled = true
        stepperNumeroCanales.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
            }
        })

        val spinnerMaterialTuberia = findViewById<Spinner>(R.id.s_materiales_de_la_tuberia_2)
        val materiales = arrayOf(MATERIAL_PEAD, MATERIAL_PLASTICO)
        spinnerMaterialTuberia.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            materiales
        )

        spinnerMaterialTuberia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                rugosidad = when (materiales[position]) {
                    MATERIAL_PEAD -> RUGOSIDAD_PEAD
                    else -> RUGOSIDAD_PLASTICO
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                rugosidad = RUGOSIDAD_PEAD
            }
        }

        val sistemaUnidades = obtenerSistemaUnidadesDesdePrefs(this@RiegoGoteoActivity)

        if (sistemaUnidades == SistemaUnidades.INTERNACIONAL) {
            editTextDiametroCintilla.hint = "m"
            editTextLongitudCintilla.hint = "m"
            editTextCaudalRiego.hint = "m³/s"
        } else {
            editTextDiametroCintilla.hint = "ft"
            editTextLongitudCintilla.hint = "ft"
            editTextCaudalRiego.hint = "ft³/s"
        }

        findViewById<Button>(R.id.btn_atras7).setOnClickListener {
            lanzarActividadEspecAccesorios(this@RiegoGoteoActivity)
        }

        findViewById<Button>(R.id.btn_siguiente7).setOnClickListener {
            val diametroCintillaTxt = editTextDiametroCintilla.text.toString()
            val longitudCintillaTxt = editTextLongitudCintilla.text.toString()
            val caudalRiegoTxt = editTextCaudalRiego.text.toString()
            val numeroCanales = stepperNumeroCanales.count

            prefs.edit().apply {
                val diametroCintilla = diametroCintillaTxt.toDoubleOrNull()
                val longitudCintilla = longitudCintillaTxt.toDoubleOrNull()
                val caudalRiego = caudalRiegoTxt.toDoubleOrNull()

                if (diametroCintilla == null || longitudCintilla == null || caudalRiego == null) {
                    Toast.makeText(applicationContext, "Todos los campos deben contener números válidos", Toast.LENGTH_SHORT).show()

                    return@setOnClickListener
                }

                val areaTuberia = (PI * diametroCintilla.pow(2)) / 4
                val velocidad = caudalRiego / areaTuberia

                putFloat(EXTRA_VELOCIDAD, velocidad.toFloat())

                val numeroReynolds = CalculosGenerales.calcularNumeroReynolds(velocidad, diametroCintilla, sistemaUnidades)

                if (numeroReynolds < 0) {
                    Toast.makeText(this@RiegoGoteoActivity, "Número de Reynolds erróneo", Toast.LENGTH_SHORT).show()

                    return@setOnClickListener
                }

                putFloat(EXTRA_NUMERO_REYNOLDS, numeroReynolds.toFloat())

                val factorFriccion = CalculosGenerales.calcularFactorFriccion(
                    this@RiegoGoteoActivity,
                    diametroCintilla,
                    rugosidad,
                    numeroReynolds
                )

                putFloat(EXTRA_FACTOR_FRICCION, factorFriccion.toFloat())

                val perdidasCintilla = CalculoPorGoteo.perdidasCintilla(numeroCanales, longitudCintilla, diametroCintilla, factorFriccion)

                putFloat(EXTRA_PERDIDAS_CINTILLA, perdidasCintilla.toFloat())

                putFloat(EXTRA_DIAMETRO_CINTILLA, diametroCintilla.toFloat())

                apply()
            }
            val intent = Intent(this, ResumenActivity::class.java)
            startActivity(intent)
            MediaPlayer.create(this, R.raw.kara).start()
        }
    }
}