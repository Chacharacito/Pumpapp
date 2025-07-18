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
import com.pumpapp.pumpapp.riegos.RiegoGoteoActivity

class ResumenActivity : AppCompatActivity() {

    companion object {

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
        val diametroCintilla = findViewById<TextView>(R.id.tv_diametro)
        val perdidasTotales = findViewById<TextView>(R.id.tv_perdidas)

        val perdidasAcesorios = EspecificacionesAccesoriosActivity.obtenerPerdidaAccesorios(this@ResumenActivity)
        val sistemaRiego = MainActivity.obtenerSistemaRiegoDesdePrefs(this@ResumenActivity)

        if (sistemaRiego == SistemaRiego.RIEGO_GOTEO) {
            val perdidadCintilla = RiegoGoteoActivity.obtenerPerdidasCintilla(this@ResumenActivity)
            val velocidadCintilla = RiegoGoteoActivity.o
        }


        perdidasTotales =

        findViewById<Button>(R.id.btn_inicio).setOnClickListener {
            lanzarActividadPrincipal(this@ResumenActivity)
        }
    }
}