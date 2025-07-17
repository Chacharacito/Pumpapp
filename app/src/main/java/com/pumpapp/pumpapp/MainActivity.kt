package com.pumpapp.pumpapp

import android.content.Context
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
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.calculos.CalculosGenerales
import com.pumpapp.pumpapp.enums.SistemaRiego
import com.pumpapp.pumpapp.enums.SistemaUnidades
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity
import com.pumpapp.pumpapp.especificaciones.EspecificacionesHidraulicasActivity

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFS_NAME = "main"

        private const val PREF_POSICION_SISTEMA_UNIDADES = "pref_posicion_sistema_unidades"
        const val PREF_SISTEMA_UNIDADES = "pref_sistema_unidades"
        const val PREF_SISTEMA_RIEGO = "pref_sistema_riego"

        fun lanzarActividadEspecHidraulicas(context: Context) {
            val intent = Intent(context, EspecificacionesHidraulicasActivity::class.java)
            context.startActivity(intent)

            MediaPlayer.create(context, R.raw.kara).start()
        }

        fun lanzarActividadEspecAccesorios(context: Context) {
            val intent = Intent(context, EspecificacionesAccesoriosActivity::class.java)
            context.startActivity(intent)

            MediaPlayer.create(context, R.raw.kara).start()
        }

        fun lanzarActividadPrincipal(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)

            MediaPlayer.create(context, R.raw.kara).start()
        }

        fun obtenerSistemaUnidadesDesdePrefs(context: Context): SistemaUnidades {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val nombre = prefs.getString(
                PREF_SISTEMA_UNIDADES,
                SistemaUnidades.INTERNACIONAL.obtenerNombre()
            )
            return SistemaUnidades.entries.firstOrNull { it.obtenerNombre() == nombre }
                ?: SistemaUnidades.INTERNACIONAL
        }

        fun obtenerSistemaRiegoDesdePrefs(context: Context): SistemaRiego {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            val codigo = prefs.getInt(
                PREF_SISTEMA_RIEGO,
                SistemaRiego.RIEGO_GOTEO.obtenerCodigo()
            )
            return SistemaRiego.entries.firstOrNull { it.obtenerCodigo() == codigo }
                ?: SistemaRiego.RIEGO_GOTEO
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val friccion1 = CalculosGenerales.calcularFactorFriccion(this, 2.0, 2.0, 3.3)
        val friccionCintilla = CalculosGenerales.calcularFactorFriccion(this, 2.0, 2.0, 3.4)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Limpiamos preferencias
        EspecificacionesHidraulicasActivity.limpiarPreferencias(this@MainActivity)

        val spinnerSistema = findViewById<Spinner>(R.id.s_sistema_unidades)

        val sistemaUnidades = SistemaUnidades.entries.map { it.obtenerNombre() }
        spinnerSistema.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            sistemaUnidades
        )

        // Cargar selección anterior
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val posicionGuardada = prefs.getInt(PREF_POSICION_SISTEMA_UNIDADES, 0)
        spinnerSistema.setSelection(posicionGuardada)

        var sistemaUnidadesSeleccionado = sistemaUnidades[posicionGuardada]

        spinnerSistema.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                sistemaUnidadesSeleccionado = sistemaUnidades[position]
                prefs.edit {
                    putInt(PREF_POSICION_SISTEMA_UNIDADES, position)
                    putString(PREF_SISTEMA_UNIDADES, sistemaUnidadesSeleccionado)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        findViewById<ImageButton>(R.id.ib_goteo).apply {
            setImageResource(R.drawable.riego_por_goteo)
            setOnClickListener {
                prefs.edit {
                    putInt(PREF_SISTEMA_RIEGO, SistemaRiego.RIEGO_GOTEO.obtenerCodigo())
                }

                lanzarActividadEspecHidraulicas(this@MainActivity)
            }
        }

        findViewById<ImageButton>(R.id.ib_inundacion).apply {
            setImageResource(R.drawable.riego_por_inundacion)
            setOnClickListener {
                prefs.edit {
                    putInt(PREF_SISTEMA_RIEGO, SistemaRiego.RIEGO_INUNDACION.obtenerCodigo())
                }

                lanzarActividadEspecHidraulicas(this@MainActivity)
            }
        }
    }
}
