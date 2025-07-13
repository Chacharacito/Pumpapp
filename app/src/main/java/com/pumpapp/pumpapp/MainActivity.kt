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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.content.edit

class MainActivity : AppCompatActivity() {

    companion object {
        const val SELECCION_SISTEMA = "selecion_sistema"
        const val POSICION_ELIGIDA = "posicion_elegidad"

        const val DATO_SISTEMA_DE_UNIDADES = "sistema_de_unidades"
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

        val sSistemaUnidades = findViewById<Spinner>(R.id.s_sistema_unidades)

        val sistemas = arrayOf("Internacional", "Imperial")
        sSistemaUnidades.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            sistemas
        )

        //Obtengo el dato seleccionado
        var sistemaSelecion: String? = null
        val prefrerence = getSharedPreferences(SELECCION_SISTEMA, MODE_PRIVATE)
        val posicionEligida = prefrerence.getInt(POSICION_ELIGIDA, 0)
        sSistemaUnidades.setSelection(posicionEligida)

        sSistemaUnidades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                prefrerence.edit() {
                    putInt(POSICION_ELIGIDA, position)
                }
                sistemaSelecion = sistemas[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        val ibRiegoPorGoteo = findViewById<ImageButton>(R.id.ib_goteo)
        ibRiegoPorGoteo.setImageResource(R.drawable.riego_por_goteo)

        ibRiegoPorGoteo.setOnClickListener {
            val intent = Intent(this, EspecificacionesHidraulicasActivity::class.java)
            intent.putExtra(DATO_SISTEMA_DE_UNIDADES, sistemaSelecion)
            startActivity(intent)

            val sonidoPasar = MediaPlayer.create(this, R.raw.kara)
            sonidoPasar.start()
        }

        val ibRiegoPorAspersion = findViewById<ImageButton>(R.id.ib_aspersion)
        ibRiegoPorAspersion.setImageResource(R.drawable.riego_por_aspersion)

        ibRiegoPorAspersion.setOnClickListener {
            val intent = Intent(this, EspecificacionesHidraulicasActivity::class.java)
            intent.putExtra(DATO_SISTEMA_DE_UNIDADES, sistemaSelecion)
            startActivity(intent)

            val sonidoPasar = MediaPlayer.create(this, R.raw.kara)
            sonidoPasar.start()
        }

        val ibRiegoPorInundacion = findViewById<ImageButton>(R.id.ib_inundacion)
        ibRiegoPorInundacion.setImageResource(R.drawable.riego_por_inundacion)

        ibRiegoPorInundacion.setOnClickListener {
            val intent = Intent(this, EspecificacionesHidraulicasActivity::class.java)
            intent.putExtra(DATO_SISTEMA_DE_UNIDADES, sistemaSelecion)
            startActivity(intent)

            val sonidoPasar = MediaPlayer.create(this, R.raw.kara)
            sonidoPasar.start()
        }
    }
}