package com.pumpapp.pumpapp.especificaciones

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
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadPrincipal
import com.pumpapp.pumpapp.MainActivity.Companion.obtenerSistemaUnidadesDesdePrefs
import com.pumpapp.pumpapp.R
import com.pumpapp.pumpapp.enums.SistemaUnidades
import com.pumpapp.pumpapp.calculos.CalculosGenerales
import kotlin.math.PI
import kotlin.math.pow

class EspecificacionesHidraulicasActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "especificaciones_hidraulicas"

        private const val PREF_ALTURA = "pref_altura"
        private const val PREF_CAUDAL = "pref_caudal"
        private const val PREF_DIAMETRO = "pref_diametro"
        private const val PREF_PRESION = "pref_presion"
        private const val PREF_MATERIAL_POS = "pref_material_pos"

        const val EXTRA_AREA_TUBERIA = "area_tuberia"
        const val EXTRA_VELOCIDAD_FLUIDO = "velocidad_fluido"
        const val EXTRA_RUGOSIDAD = "rugosidad"
        const val EXTRA_NUMERO_REYNOLDS = "nro_reynolds"
        const val EXTRA_FACTOR_FRICCION = "factor_friccion"

        private const val MATERIAL_ACERO = "Acero"
        private const val MATERIAL_PLASTICO = "Plástico"
        private const val MATERIAL_HIERRO = "Hierro"
        private const val MATERIAL_PVC = "PVC"

        private const val RUGOSIDAD_ACERO = 4.6e-5
        private const val RUGOSIDAD_PLASTICO = 3e-7
        private const val RUGOSIDAD_HIERRO = 1.5e-4
        private const val RUGOSIDAD_PVC = 2.3e-6

        fun limpiarPreferencias(context: Context) {
            context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit {
                clear()
            }
        }

        fun obtenerAreaTuberia(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_AREA_TUBERIA, 0f).toDouble()
        }

        fun obtenerVelocidadFluido(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_VELOCIDAD_FLUIDO, 0f).toDouble()
        }

        fun obtenerRugosidad(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_RUGOSIDAD, 0f).toDouble()
        }

        fun obtenerNumeroReynolds(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_NUMERO_REYNOLDS, 0f).toDouble()
        }

        fun obtenerFactorFriccion(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_FACTOR_FRICCION, 0f).toDouble()
        }
    }

    private var rugosidad: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_especificaciones_hidraulicas)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val spinnerMaterial = findViewById<Spinner>(R.id.s_material_de_la_tuberia)
        val editTextAltura = findViewById<EditText>(R.id.et_altura)
        val editTextCaudal = findViewById<EditText>(R.id.et_caudal)
        val editTextDiametro = findViewById<EditText>(R.id.et_diametro)
        val editTextPresion = findViewById<EditText>(R.id.et_presion)

        val materiales = arrayOf(MATERIAL_PVC, MATERIAL_ACERO, MATERIAL_PLASTICO, MATERIAL_HIERRO)
        spinnerMaterial.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            materiales
        )

        spinnerMaterial.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                rugosidad = when (materiales[position]) {
                    MATERIAL_ACERO -> RUGOSIDAD_ACERO
                    MATERIAL_PLASTICO -> RUGOSIDAD_PLASTICO
                    MATERIAL_HIERRO -> RUGOSIDAD_HIERRO
                    else -> RUGOSIDAD_PVC
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                rugosidad = RUGOSIDAD_PVC
            }
        }

        val sistemaUnidades = obtenerSistemaUnidadesDesdePrefs(this@EspecificacionesHidraulicasActivity)

        if (sistemaUnidades == SistemaUnidades.INTERNACIONAL) {
            editTextAltura.hint = "m"
            editTextCaudal.hint = "m³/s"
            editTextDiametro.hint = "m"
            editTextPresion.hint = "kPa"
        } else {
            editTextAltura.hint = "ft"
            editTextCaudal.hint = "ft³/s"
            editTextDiametro.hint = "ft"
            editTextPresion.hint = "psi"
        }

        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)

        editTextAltura.setText(prefs.getString(PREF_ALTURA, ""))
        editTextCaudal.setText(prefs.getString(PREF_CAUDAL, ""))
        editTextDiametro.setText(prefs.getString(PREF_DIAMETRO, ""))
        editTextPresion.setText(prefs.getString(PREF_PRESION, ""))
        spinnerMaterial.setSelection(prefs.getInt(PREF_MATERIAL_POS, 0))

        findViewById<Button>(R.id.btn_siguiente).setOnClickListener {
            val alturaTxt = editTextAltura.text.toString()
            val caudalTxt = editTextCaudal.text.toString()
            val diametroTxt = editTextDiametro.text.toString()
            val presionTxt = editTextPresion.text.toString()

            val altura = alturaTxt.toDoubleOrNull()
            val caudal = caudalTxt.toDoubleOrNull()
            val diametro = diametroTxt.toDoubleOrNull()
            val presion = presionTxt.toDoubleOrNull()

            if (altura == null || caudal == null || diametro == null || presion == null) {
                Toast.makeText(this, "Todos los campos deben contener números válidos", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            val areaTuberia = (PI * diametro.pow(2)) / 4
            val velocidadFluido = caudal / areaTuberia

            prefs.edit().apply {
                putString(PREF_ALTURA, alturaTxt)
                putString(PREF_CAUDAL, caudalTxt)
                putString(PREF_DIAMETRO, diametroTxt)
                putString(PREF_PRESION, presionTxt)
                putInt(PREF_MATERIAL_POS, spinnerMaterial.selectedItemPosition)

                putFloat(EXTRA_AREA_TUBERIA, areaTuberia.toFloat())
                putFloat(EXTRA_VELOCIDAD_FLUIDO, velocidadFluido.toFloat())
                putFloat(EXTRA_RUGOSIDAD, rugosidad.toFloat())

                val numeroReynolds = CalculosGenerales.calcularNumeroReynolds(
                    velocidadFluido,
                    diametro,
                    sistemaUnidades
                )

                if (numeroReynolds < 0) {
                    Toast.makeText(this@EspecificacionesHidraulicasActivity, "Número de Reynolds erróneo", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                putFloat(EXTRA_NUMERO_REYNOLDS, numeroReynolds.toFloat())

                val factorFriccion = CalculosGenerales.calcularFactorFriccion(
                    this@EspecificacionesHidraulicasActivity,
                    diametro,
                    rugosidad,
                    numeroReynolds
                )

                putFloat(EXTRA_FACTOR_FRICCION, factorFriccion.toFloat())

                apply()
            }

            lanzarActividadEspecAccesorios(this@EspecificacionesHidraulicasActivity)
        }

        findViewById<Button>(R.id.btn_atras).setOnClickListener {
            lanzarActividadPrincipal(this@EspecificacionesHidraulicasActivity)
        }
    }
}
