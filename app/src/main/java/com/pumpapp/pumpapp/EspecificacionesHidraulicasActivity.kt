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
        val intent1 : Intent = intent
        val datoselct = intent1.getStringExtra("Sistema de unidades")

        if (datoselct == "Internacional") {
            altura.setHint("m")
            caudal.setHint("m³/s")
            diametro.setHint("m")
            presion.setHint("kPa")
        }else{
            altura.setHint("ft")
            caudal.setHint("ft³/s")
            diametro.setHint("ft")
            presion.setHint("psi")
        }


        val materiales = arrayOf("PVC", "Acero", "Plástico", "Hierro")
        sMaterialDeLaTuberia.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            materiales
        )

        //inicio de los calculos iniciales

        var materialselec : String? = null

        sMaterialDeLaTuberia.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                materialselec = materiales[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        var rugosidad : Double? = null

        if (materialselec == "Acero"){

            rugosidad = 4.6 * (Math.pow(10.0, (-5).toDouble()))

        }else if (materialselec == "Plastico") {

            rugosidad = 3 * (Math.pow(10.0, (-7).toDouble()))

        }else if (materialselec == "Hierro") {

            rugosidad = 1.5 * (Math.pow(10.0, (-4).toDouble()))

        }else{

            rugosidad = 2.3 * (Math.pow(10.0, (-6).toDouble()))

        }

        val btnAtras = findViewById<Button>(R.id.btn_atras)
        btnAtras.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            val pasar = MediaPlayer.create(this, R.raw.kara)
            pasar.start()
        }
    }
}