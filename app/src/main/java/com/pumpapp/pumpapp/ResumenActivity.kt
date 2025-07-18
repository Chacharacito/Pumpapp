package com.pumpapp.pumpapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadPrincipal
import com.pumpapp.pumpapp.enums.SistemaRiego
import com.pumpapp.pumpapp.enums.SistemaUnidades
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity
import com.pumpapp.pumpapp.especificaciones.EspecificacionesHidraulicasActivity
import com.pumpapp.pumpapp.riegos.RiegoGoteoActivity
import kotlin.math.pow

class ResumenActivity : AppCompatActivity() {

    companion object {
        const val GRAVEDAD = 9.81
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_resumen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val potenciaBomba = findViewById<TextView>(R.id.tv_potencia)
        val caudalDeSalida = findViewById<TextView>(R.id.tv_caudal)
        val presion = findViewById<TextView>(R.id.tv_presion)
        val velocidadFluida = findViewById<TextView>(R.id.tv_velocidad)
        val diametroDeCintilla = findViewById<TextView>(R.id.tv_diametro)
        val perdidasTotales = findViewById<TextView>(R.id.tv_perdidas)

        val perdidasAcesorios = EspecificacionesAccesoriosActivity.obtenerPerdidaAccesorios(this@ResumenActivity)
        val sistemaRiego = MainActivity.obtenerSistemaRiegoDesdePrefs(this@ResumenActivity)
        val velocidadTuberia = EspecificacionesHidraulicasActivity.obtenerVelocidadFluido(this@ResumenActivity)
        val presionBomba = EspecificacionesHidraulicasActivity.obtenerPresion(this@ResumenActivity)
        val alturaBomba = EspecificacionesHidraulicasActivity.obtenerAltura(this@ResumenActivity)
        val caudal = EspecificacionesHidraulicasActivity.obtenerCaudal(this@ResumenActivity)
        val diametroCintilla = RiegoGoteoActivity.obtenerDiemtroCintilla(this@ResumenActivity)

        var velocidadCintilla = 0.0
        var perdidadCintilla = 0.0

        val cargaBomba = (presionBomba / GRAVEDAD) + alturaBomba + ((velocidadTuberia + velocidadCintilla).pow(2.0) / (2 * GRAVEDAD)) + perdidasTotal
        val potenciaDeBomba = cargaBomba * GRAVEDAD * caudal
        potenciaBomba.setText(potenciaDeBomba.toString())

        caudalDeSalida.setText(caudal.toString())

        if (sistemaRiego == SistemaRiego.RIEGO_GOTEO) {
            perdidadCintilla = RiegoGoteoActivity.obtenerPerdidasCintilla(this@ResumenActivity)
            velocidadCintilla = RiegoGoteoActivity.obtenerVelocidad(this@ResumenActivity)
            velocidadFluida.setText(velocidadCintilla.toString())
            diametroDeCintilla.setText(diametroCintilla.toString())
        }

        val perdidasTotal = (velocidadTuberia.pow(2.0) / (2 * GRAVEDAD)) * perdidasAcesorios + ((velocidadCintilla.pow(2.0)) / (2 * GRAVEDAD)) * perdidadCintilla
        perdidasTotales.setText(perdidasTotal.toString())

        val presionSalidad = presionBomba - (GRAVEDAD * alturaBomba) + perdidasTotal
        presion.setText(presionSalidad.toString())

        findViewById<Button>(R.id.btn_inicio).setOnClickListener {
            lanzarActividadPrincipal(this@ResumenActivity)
        }
    }
}