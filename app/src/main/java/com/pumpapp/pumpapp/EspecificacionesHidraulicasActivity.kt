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
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.DATO_SISTEMA_DE_UNIDADES
import kotlin.math.pow

class EspecificacionesHidraulicasActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_especificaciones_hidraulicas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sMaterialDeLaTuberia = findViewById<Spinner>(R.id.s_material_de_la_tuberia)

        val altura = findViewById<EditText>(R.id.et_altura)
        val caudal = findViewById<EditText>(R.id.et_caudal)
        val diametro = findViewById<EditText>(R.id.et_diametro)
        val presion = findViewById<EditText>(R.id.et_presion)

        //no borrar esta parte me permite cambiar los hints de caudal altura y diametro de acuerdo a las unidades seleccionadas
        //en la pantalla principal
        val intentoActual: Intent = intent
        val sistemaSeleccion = intentoActual.getStringExtra(DATO_SISTEMA_DE_UNIDADES)

        val materiales = arrayOf("PVC", "Acero", "Plástico", "Hierro")
        sMaterialDeLaTuberia.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            materiales
        )

        //inicio de los calculos iniciales

        var materialSeleccionado: String? = null

        sMaterialDeLaTuberia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                materialSeleccionado = materiales[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        var rugosidad: Double? = if (materialSeleccionado == "Acero") {
            4.6 * (10.0.pow((-5).toDouble()))
        } else if (materialSeleccionado == "Plastico") {
            3 * (10.0.pow((-7).toDouble()))
        } else if (materialSeleccionado == "Hierro") {
            1.5 * (10.0.pow((-4).toDouble()))
        } else {
            2.3 * (10.0.pow((-6).toDouble()))
        }



        if (sistemaSeleccion == "Internacional") {
            altura.setHint("m")
            caudal.setHint("m³/s")
            diametro.setHint("m")
            presion.setHint("kPa")
        } else {
            altura.setHint("ft")
            caudal.setHint("ft³/s")
            diametro.setHint("ft")
            presion.setHint("psi")
        }

        val btnAtras = findViewById<Button>(R.id.btn_atras)
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            val sonidoPasar = MediaPlayer.create(this, R.raw.kara)
            sonidoPasar.start()
        }
    }
}