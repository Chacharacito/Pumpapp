package com.pumpapp.pumpapp.calculos

import android.content.Context
import android.widget.Toast
import com.pumpapp.pumpapp.enums.SistemaUnidades
import com.pumpapp.pumpapp.enums.TipoAccesorio

class CalculosGenerales {

    companion object {
        // Sistema Internacional
        private const val VISCOSIDAD_SI = 0.000861 // Pa·s
        private const val DENSIDAD_SI = 997.0      // kg/m³

        // Sistema Imperial
        private const val VISCOSIDAD_IMPERIAL = 0.000018 // slug/(ft·s)
        private const val DENSIDAD_IMPERIAL = 1.94       // slug/ft³

        private const val LIMITE_FLUJO_LAMINAR = 2000
        private const val LIMITE_FLUJO_TURBULENTO = 4000

        fun calcularNumeroReynolds(
            velocidad: Double,
            diametro: Double,
            sistemaUnidades: SistemaUnidades = SistemaUnidades.INTERNACIONAL
        ): Double {
            return when (sistemaUnidades) {
                SistemaUnidades.INTERNACIONAL -> (velocidad * diametro * DENSIDAD_SI) / VISCOSIDAD_SI
                SistemaUnidades.IMPERIAL -> (velocidad * diametro * DENSIDAD_IMPERIAL) / VISCOSIDAD_IMPERIAL
            }
        }

        fun calcularFactorFriccion(
            context: Context,
            diametro: Double,
            rugosidad: Double,
            numeroDeReynolds: Double
        ): Double {
            return if (numeroDeReynolds <= LIMITE_FLUJO_LAMINAR) {
                64.0 / numeroDeReynolds
            } else if (numeroDeReynolds >= LIMITE_FLUJO_TURBULENTO){
                0.3009 / (Math.pow(Math.log10((Math.pow(rugosidad / (3.7315 * diametro), 1.0954)) + (Math.pow(5.9802 / numeroDeReynolds, 0.9695))), 2.0))
            } else {
                Toast.makeText(context, "Flujo transitorio: resultado incierto", Toast.LENGTH_SHORT).show()
                return -1.0  // Valor especial para indicar “no definido”
            }
        }

        fun calcularPerdidaDeAccesorios(
            accesorios: Map<TipoAccesorio, Int>,
            factorFriccion: Double
        ): Double {
            return accesorios.entries.sumOf { (accesorio, cantidad) ->
                calcularPerdidaDeAccesorio(accesorio, cantidad, factorFriccion)
            }
        }

        fun calcularPerdidaDeAccesorio(
            accesorio: TipoAccesorio,
            cantidad: Int,
            factorFriccion: Double
        ): Double {
            val factor = when (accesorio) {
                TipoAccesorio.VALVULA_DE_BOLA -> 150
                TipoAccesorio.VALVULA_DE_ANGULO -> 150
                TipoAccesorio.VALVULA_DE_GLOBO -> 340
                TipoAccesorio.CODO_NOVENTA -> 30
                TipoAccesorio.CODO_CUARENTA_Y_CINCO -> 16
                TipoAccesorio.VUELTA_RETORNO -> 50
                TipoAccesorio.TE_FLUJO_NORMAL -> 20
                TipoAccesorio.TE_FLUJO_INVERTIDO -> 60
                TipoAccesorio.CABEZAL_LLAVE_DE_PASO -> 150
                TipoAccesorio.CABEZAL_VALVULA_PRESION -> 150
            }
            return factor * factorFriccion * cantidad
        }
    }
}