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

class MainActivity : AppCompatActivity() {
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

        //obtengo el dato seleccionado
        var sistemaselecion: String? = null

        sSistemaUnidades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                sistemaselecion = sistemas[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }



        val ibRiegoPorGoteo = findViewById<ImageButton>(R.id.ib_goteo)
        ibRiegoPorGoteo.setImageResource(R.drawable.riego_por_goteo)

        ibRiegoPorGoteo.setOnClickListener {
            val intent1 = Intent(this, EspecificacionesHidraulicasActivity::class.java)
            intent1.putExtra("Sistema de unidades" , sistemaselecion )
            startActivity(intent1)

            val pasar = MediaPlayer.create(this, R.raw.kara)
            pasar.start()
        }

        val ibRiegoPorAspersion = findViewById<ImageButton>(R.id.ib_aspersion)
        ibRiegoPorAspersion.setImageResource(R.drawable.riego_por_aspersion)

        ibRiegoPorAspersion.setOnClickListener {
            val intent2 = Intent(this, EspecificacionesHidraulicasActivity::class.java)
            intent2.putExtra("Sistema de unidades" , sistemaselecion )
            startActivity(intent2)

            val pasar = MediaPlayer.create(this, R.raw.kara)
            pasar.start()
        }

        val ibRiegoPorInundacion = findViewById<ImageButton>(R.id.ib_inundacion)
        ibRiegoPorInundacion.setImageResource(R.drawable.riego_por_inundacion)

        ibRiegoPorInundacion.setOnClickListener {
            val intent3 = Intent(this, EspecificacionesHidraulicasActivity::class.java)
            intent3.putExtra("Sistema de unidades" , sistemaselecion )
            startActivity(intent3)

            val pasar = MediaPlayer.create(this, R.raw.kara)
            pasar.start()
        }
    }
}