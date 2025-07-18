package com.pumpapp.pumpapp.especificaciones

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.MainActivity.Companion.lanzarActividadEspecHidraulicas
import com.pumpapp.pumpapp.MainActivity.Companion.obtenerSistemaRiegoDesdePrefs
import com.pumpapp.pumpapp.R
import com.pumpapp.pumpapp.calculos.CalculosGenerales
import com.pumpapp.pumpapp.enums.SistemaRiego
import com.pumpapp.pumpapp.enums.TipoAccesorio
import com.pumpapp.pumpapp.especificaciones.EspecificacionesHidraulicasActivity.Companion.obtenerFactorFriccion
import com.pumpapp.pumpapp.riegos.RiegoGoteoActivity
import com.pumpapp.pumpapp.riegos.RiegoInundacionActivity
import nl.dionsegijn.steppertouch.OnStepCallback
import nl.dionsegijn.steppertouch.StepperTouch

class EspecificacionesAccesoriosActivity : AppCompatActivity() {

    private val accesorios = mutableMapOf<TipoAccesorio, Int>()

    fun setAccesorio(accesorio: TipoAccesorio, valor: Int) {
        accesorios[accesorio] = valor
    }

    companion object {
        const val PREFS_NAME = "especificaciones_accesorios"

        const val EXTRA_PERDIDA_ACCESORIOS = "perdida_accesorios"

        fun limpiarPreferencias(context: Context) {
            context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit {
                clear()
            }
        }

        fun obtenerPerdidaAccesorios(context: Context): Double {
            val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
            return prefs.getFloat(EXTRA_PERDIDA_ACCESORIOS, 0f).toDouble()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_especificaciones_accesorios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val prefs = getSharedPreferences(EspecificacionesHidraulicasActivity.Companion.PREFS_NAME, MODE_PRIVATE)

        val stepperBola = findViewById<StepperTouch>(R.id.st_bola)
        stepperBola.minValue = 0
        stepperBola.maxValue = 10
        stepperBola.sideTapEnabled = true
        stepperBola.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.VALVULA_DE_BOLA, value)
            }
        })

        val stepperAngulo = findViewById<StepperTouch>(R.id.st_angulo)
        stepperAngulo.minValue = 0
        stepperAngulo.maxValue = 10
        stepperAngulo.sideTapEnabled = true
        stepperAngulo.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.VALVULA_DE_ANGULO, value)
            }
        })

        val stepperGlobo = findViewById<StepperTouch>(R.id.st_globo)
        stepperGlobo.minValue = 0
        stepperGlobo.maxValue = 10
        stepperGlobo.sideTapEnabled = true
        stepperGlobo.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.VALVULA_DE_GLOBO, value)
            }
        })

        val stepperNoventa = findViewById<StepperTouch>(R.id.st_noventa)
        stepperNoventa.minValue = 0
        stepperNoventa.maxValue = 10
        stepperNoventa.sideTapEnabled = true
        stepperNoventa.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.CODO_NOVENTA, value)
            }
        })

        val stepperCuarentaYCinco = findViewById<StepperTouch>(R.id.st_cuarenta_cinco)
        stepperCuarentaYCinco.minValue = 0
        stepperCuarentaYCinco.maxValue = 10
        stepperCuarentaYCinco.sideTapEnabled = true
        stepperCuarentaYCinco.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.CODO_CUARENTA_Y_CINCO, value)
            }
        })

        val stepperVueltaRetorno = findViewById<StepperTouch>(R.id.st_vuelva_retorno)
        stepperVueltaRetorno.minValue = 0
        stepperVueltaRetorno.maxValue = 10
        stepperVueltaRetorno.sideTapEnabled = true
        stepperVueltaRetorno.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.VUELTA_RETORNO, value)
            }
        })

        val stepperFlujoNormal = findViewById<StepperTouch>(R.id.st_flujo_normal)
        stepperFlujoNormal.minValue = 0
        stepperFlujoNormal.maxValue = 10
        stepperFlujoNormal.sideTapEnabled = true
        stepperFlujoNormal.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.TE_FLUJO_NORMAL, value)
            }
        })

        val stepperFlujoInvertido = findViewById<StepperTouch>(R.id.st_flujo_invertido)
        stepperFlujoInvertido.minValue = 0
        stepperFlujoInvertido.minValue = 10
        stepperFlujoInvertido.sideTapEnabled = true
        stepperFlujoInvertido.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.TE_FLUJO_INVERTIDO, value)
            }
        })

        val stepperLlavePaso = findViewById<StepperTouch>(R.id.st_llave_paso)
        stepperLlavePaso.minValue = 0
        stepperLlavePaso.maxValue = 10
        stepperLlavePaso.sideTapEnabled = true
        stepperLlavePaso.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.CABEZAL_LLAVE_DE_PASO, value)
            }
        })

        val stepperValvulaPresion = findViewById<StepperTouch>(R.id.st_valvula_presion)
        stepperValvulaPresion.minValue = 0
        stepperValvulaPresion.maxValue = 10
        stepperValvulaPresion.sideTapEnabled = true
        stepperValvulaPresion.addStepCallback(object: OnStepCallback {
            override fun onStep(value: Int, positive: Boolean) {
                setAccesorio(TipoAccesorio.CABEZAL_VALVULA_PRESION, value)
            }
        })

        findViewById<Button>(R.id.btn_atras3).setOnClickListener {
            lanzarActividadEspecHidraulicas(this@EspecificacionesAccesoriosActivity)
        }

        findViewById<Button>(R.id.btn_siguiente3).setOnClickListener {
            prefs.edit().apply {
                putFloat(EXTRA_PERDIDA_ACCESORIOS,
                    CalculosGenerales.calcularPerdidaDeAccesorios(
                        accesorios,
                        obtenerFactorFriccion(this@EspecificacionesAccesoriosActivity)).toFloat()
                )

                apply()
            }

            val sistemaRiego =
                obtenerSistemaRiegoDesdePrefs(this@EspecificacionesAccesoriosActivity)

            val intent = when (sistemaRiego) {
                SistemaRiego.RIEGO_GOTEO -> Intent(this, RiegoGoteoActivity::class.java)
                SistemaRiego.RIEGO_INUNDACION -> Intent(this, RiegoInundacionActivity::class.java)
            }

            startActivity(intent)
            MediaPlayer.create(this, R.raw.kara).start()
        }
    }
}