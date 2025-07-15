package com.pumpapp.pumpapp

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
import com.pumpapp.pumpapp.MainActivity.Companion.EXTRA_SISTEMA_RIEGO
import com.pumpapp.pumpapp.MainActivity.Companion.EXTRA_SISTEMA_UNIDADES
import com.pumpapp.pumpapp.MainActivity.Companion.RIEGO_GOTEO
import com.pumpapp.pumpapp.MainActivity.Companion.RIEGO_INUNDACION
import com.pumpapp.pumpapp.MainActivity.Companion.SISTEMA_INTERNACIONAL
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadPrincipal
import com.pumpapp.pumpapp.riegos.RiegoPorInundacionActivity
import kotlin.math.PI
import kotlin.math.pow

class EspecificacionesHidraulicasActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_AREA_TUBERIA = "areaTuberia"
        const val EXTRA_VELOCIDAD_FLUIDO = "velocidadFluido"
        const val EXTRA_RUGOSIDAD = "rugosidad"

        private const val MATERIAL_ACERO = "Acero"
        private const val MATERIAL_PLASTICO = "Plástico"
        private const val MATERIAL_HIERRO = "Hierro"
        private const val MATERIAL_PVC = "PVC"

        private const val RUGOSIDAD_ACERO = 4.6e-5
        private const val RUGOSIDAD_PLASTICO = 3e-7
        private const val RUGOSIDAD_HIERRO = 1.5e-4
        private const val RUGOSIDAD_PVC = 2.3e-6
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
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
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

        val sistemaSeleccion = intent.getStringExtra(EXTRA_SISTEMA_UNIDADES) ?: SISTEMA_INTERNACIONAL

        if (sistemaSeleccion == SISTEMA_INTERNACIONAL) {
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

        val sonidoPasar = MediaPlayer.create(this, R.raw.kara)

        findViewById<Button>(R.id.btn_siguiente).setOnClickListener {
            val alturaTxt = editTextAltura.text.toString()
            val caudalTxt = editTextCaudal.text.toString()
            val diametroTxt = editTextDiametro.text.toString()
            val presionTxt = editTextPresion.text.toString()

            if (alturaTxt.isEmpty() || caudalTxt.isEmpty() || diametroTxt.isEmpty() || presionTxt.isEmpty()) {
                Toast.makeText(this, "No deben haber campos vacíos", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            val diametro = diametroTxt.toDouble()
            val caudal = caudalTxt.toDouble()
            val areaTuberia = (PI * diametro.pow(2)) / 4
            val velocidadFluido = caudal / areaTuberia

            val sistemaRiego = intent.getIntExtra(EXTRA_SISTEMA_RIEGO, RIEGO_GOTEO)
            val intent = when (sistemaRiego) {
                RIEGO_INUNDACION -> Intent(this, RiegoPorInundacionActivity::class.java)
                //TODO: añadir las demas actividades cuando juanpa las cree
                else -> Intent(this, MainActivity::class.java)
            }

            intent.putExtra(EXTRA_AREA_TUBERIA, areaTuberia)
            intent.putExtra(EXTRA_VELOCIDAD_FLUIDO, velocidadFluido)
            intent.putExtra(EXTRA_RUGOSIDAD, rugosidad)
            intent.putExtra(EXTRA_SISTEMA_UNIDADES, sistemaSeleccion)

            startActivity(intent)

            sonidoPasar.start()
        }

        findViewById<Button>(R.id.btn_atras).setOnClickListener {
            lanzarActividadPrincipal(this@EspecificacionesHidraulicasActivity)
            sonidoPasar.start()
        }
    }
}
