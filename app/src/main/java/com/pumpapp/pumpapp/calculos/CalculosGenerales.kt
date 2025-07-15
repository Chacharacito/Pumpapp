package com.pumpapp.pumpapp.calculos

import android.content.Context
import android.widget.Toast
import com.pumpapp.pumpapp.SistemaUnidades
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.CAB_LLAVE_DE_PASO
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.CAB_VAL_PRESION
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.COD_CUARENTACINCO
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.COD_NOVENTA
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.TE_FLU_INVERTIDO
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.TE_FLU_NORMAL
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.VAL_DE_ANGULO
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.VAL_DE_BOLA
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.VAL_DE_GOBO
import com.pumpapp.pumpapp.especificaciones.EspecificacionesAccesoriosActivity.Companion.VUL_RETORNO
import com.pumpapp.pumpapp.especificaciones.EspecificacionesHidraulicasActivity.Companion.EXTRA_FACTOR_FRICCION
import com.pumpapp.pumpapp.especificaciones.EspecificacionesHidraulicasActivity.Companion.EXTRA_NUMERO_REYNOLDS
import kotlin.contracts.contract

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
            sistemaDeUnidades: SistemaUnidades = SistemaUnidades.INTERNACIONAL
        ): Double {
            return when (sistemaDeUnidades) {
                SistemaUnidades.INTERNACIONAL -> (velocidad * diametro * DENSIDAD_SI) / VISCOSIDAD_SI
                SistemaUnidades.IMPERIAL -> (velocidad * diametro * DENSIDAD_IMPERIAL) / VISCOSIDAD_IMPERIAL
            }
        }
        fun calcularFactorFriccion(
            context: Context,
            diametro: Double,
            rugosidad: Double
        ): Double {
            val numeroDeReynolds = EXTRA_NUMERO_REYNOLDS.toDouble()
            return if (numeroDeReynolds <= LIMITE_FLUJO_LAMINAR) {
                64.0 / numeroDeReynolds
            } else if (numeroDeReynolds >= LIMITE_FLUJO_TURBULENTO){
                0.3009/(Math.pow(Math.log10((Math.pow(rugosidad/(3.7315*diametro), 1.0954))+(Math.pow(5.9802/numeroDeReynolds, 0.9695))), 2.0));
            } else {
                Toast.makeText(context, "Flujo transitorio: resultado incierto", Toast.LENGTH_SHORT).show()
                return -1.0  // Valor especial para indicar “no definido”

            }
        }
        fun calcularAccesorios(
            acesorios: Double,
            cantidad: Double
        ): Double {
            return when (acesorios) {
                VAL_DE_BOLA.toDouble() -> { 150 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                VAL_DE_ANGULO.toDouble() -> { 150 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                VAL_DE_GOBO.toDouble() -> { 340 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                COD_NOVENTA.toDouble() -> { 30 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                COD_CUARENTACINCO.toDouble() -> { 16 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                VUL_RETORNO.toDouble() -> { 50 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                TE_FLU_NORMAL.toDouble() -> { 20 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                TE_FLU_INVERTIDO.toDouble() -> { 60 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                CAB_LLAVE_DE_PASO.toDouble() -> { 150 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                CAB_VAL_PRESION.toDouble() -> { 150 * EXTRA_FACTOR_FRICCION.toDouble() * cantidad
                }
                else -> {
                    -1.0
                }
            }
        }
    }
}