package com.pumpapp.pumpapp.calculos

import android.widget.Toast
import com.pumpapp.pumpapp.calculos.CalculosGenerales.Companion.LIMITE_FLUJO_LAMINAR
import com.pumpapp.pumpapp.calculos.CalculosGenerales.Companion.LIMITE_FLUJO_TURBULENTO

class CalculoPorGoteo {
    companion object {
        fun factorFriccion(): Double {
            context: Context,
            diametro: Double,
            rugosidad: Double,
            numeroReynoldsCintilla: Double
            ): Double {
                val numeroDeReynolds = numeroReynoldsCintilla.toDouble()
                return if (numeroDeReynolds <= LIMITE_FLUJO_LAMINAR) {
                    64.0 / numeroDeReynolds
                } else if (numeroDeReynolds >= LIMITE_FLUJO_TURBULENTO){
                    0.3009 / (Math.pow(Math.log10((Math.pow(rugosidad / (3.7315 * diametro), 1.0954)) + (Math.pow(5.9802 / numeroDeReynolds, 0.9695))), 2.0));
                } else {
                    Toast.makeText(context, "Flujo transitorio: resultado incierto", Toast.LENGTH_SHORT).show()
                    return -1.0  // Valor especial para indicar “no definido”
                }
            }
        }
    }
}