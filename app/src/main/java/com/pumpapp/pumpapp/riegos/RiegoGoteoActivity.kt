package com.pumpapp.pumpapp.riegos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecAccesorios
import com.pumpapp.pumpapp.R
import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch

class RiegoGoteoActivity : AppCompatActivity() {

    companion object {
        private const val MATERIAL_PVC = "PVC"
        private const val MATERIAL_ACERO = "Acero"
        private const val MATERIAL_HIERRO = "Hierro"
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

        val editTextPendienteTerreno = findViewById<EditText>(R.id.et_pendiente_del_terreno_2)
        val editTextLongitudCampo = findViewById<EditText>(R.id.et_longitud_del_campo)

        val stepperNumeroCanales = findViewById<StepperTouch>(R.id.st_numero_de_canales)
        stepperNumeroCanales.minValue = 0
        stepperNumeroCanales.sideTapEnabled = true

        val editTextCaudalRiego = findViewById<EditText>(R.id.et_caudal_de_riego)

        val spinnerMaterialTuberia = findViewById<Spinner>(R.id.s_materiales_de_la_tuberia_2)
        val materiales = arrayOf(MATERIAL_PVC, MATERIAL_ACERO, MATERIAL_HIERRO)
        spinnerMaterialTuberia.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            materiales
        )

        findViewById<Button>(R.id.btn_atras7).setOnClickListener {
            lanzarActividadEspecAccesorios(this@RiegoGoteoActivity)
        }
    }
}