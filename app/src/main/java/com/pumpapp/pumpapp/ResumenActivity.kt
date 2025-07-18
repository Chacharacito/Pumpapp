package com.pumpapp.pumpapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadPrincipal
import com.pumpapp.pumpapp.MainActivity.Companion.obtenerSistemaUnidadesDesdePrefs
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



        val perdidasAcesorios = EspecificacionesAccesoriosActivity.obtenerPerdidaAccesorios(this)
        val sistemaRiego = MainActivity.obtenerSistemaRiegoDesdePrefs(this)
        val velocidadTuberia = EspecificacionesHidraulicasActivity.obtenerVelocidadFluido(this)
        val presionBomba = EspecificacionesHidraulicasActivity.obtenerPresion(this)
        val alturaBomba = EspecificacionesHidraulicasActivity.obtenerAltura(this)
        val caudal = EspecificacionesHidraulicasActivity.obtenerCaudal(this)
        val diametroCintilla = RiegoGoteoActivity.obtenerDiemtroCintilla(this)

        var velocidadCintilla = 0.0
        var perdidadCintilla = 0.0

        caudalDeSalida.text = "%.3f".format(caudal)

        val sistemaUnidades = obtenerSistemaUnidadesDesdePrefs(this)

        if (sistemaUnidades == SistemaUnidades.INTERNACIONAL) {
            findViewById<TextView>(R.id.tv_unidadcaudal).setText("m³/s")
            findViewById<TextView>(R.id.tv_unidaddiametro).setText("m")
            findViewById<TextView>(R.id.tv_unidadespresion).setText("kPa")
            findViewById<TextView>(R.id.tv_unidadperdidad).setText("m")
            findViewById<TextView>(R.id.tv_unidadpotencia).setText("kWa")
            findViewById<TextView>(R.id.tv_unidadvelocidad).setText("m/s")
        } else {
            findViewById<TextView>(R.id.tv_unidadcaudal).setText("ft³/s")
            findViewById<TextView>(R.id.tv_unidaddiametro).setText("ft")
            findViewById<TextView>(R.id.tv_unidadespresion).setText("psf")
            findViewById<TextView>(R.id.tv_unidadperdidad).setText("ft")
            findViewById<TextView>(R.id.tv_unidadpotencia).setText("hp")
            findViewById<TextView>(R.id.tv_unidadvelocidad).setText("ft/s")
        }
        if (sistemaRiego == SistemaRiego.RIEGO_GOTEO) {
            perdidadCintilla = RiegoGoteoActivity.obtenerPerdidasCintilla(this)
            velocidadCintilla = RiegoGoteoActivity.obtenerVelocidad(this)

            velocidadFluida.text = "%.2f".format(velocidadCintilla)
            diametroDeCintilla.text = "%.2f".format(diametroCintilla)
        }

        val perdidasTotal = (velocidadTuberia.pow(2.0) / (2 * GRAVEDAD)) * perdidasAcesorios +
                (velocidadCintilla.pow(2.0) / (2 * GRAVEDAD)) * perdidadCintilla
        perdidasTotales.text = "%.2f".format(perdidasTotal)

        val cargaBomba = (presionBomba / GRAVEDAD) + alturaBomba +
                ((velocidadTuberia + velocidadCintilla).pow(2.0) / (2 * GRAVEDAD)) +
                perdidasTotal
        val potenciaDeBomba = cargaBomba * GRAVEDAD * caudal
        potenciaBomba.text = "%.2f".format(potenciaDeBomba)

        val presionSalida = presionBomba - (GRAVEDAD * alturaBomba) + perdidasTotal
        presion.text = "%.2f".format(presionSalida)

        findViewById<Button>(R.id.btn_inicio).setOnClickListener {
            lanzarActividadPrincipal(this)
        }
    }
}
