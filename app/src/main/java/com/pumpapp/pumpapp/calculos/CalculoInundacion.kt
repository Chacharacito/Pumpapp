package com.pumpapp.pumpapp.calculos

import com.pumpapp.pumpapp.MainActivity.Sistema

class CalculoInundacion {
    companion object {
        // Sistema Internacional
        private const val VISCOSIDAD_SI = 0.000861 // Pa·s
        private const val DENSIDAD_SI = 997.0      // kg/m³

        // Sistema Imperial
        private const val VISCOSIDAD_IMPERIAL = 0.000018 // slug/(ft·s)
        private const val DENSIDAD_IMPERIAL = 1.94       // slug/ft³

        fun calcularNumeroReynolds(
            velocidad: Double,
            diametro: Double,
            sistema: Sistema = Sistema.SI
        ): Double {
            return when (sistema) {
                Sistema.SI -> (velocidad * diametro * DENSIDAD_SI) / VISCOSIDAD_SI
                Sistema.IMPERIAL -> (velocidad * diametro * DENSIDAD_IMPERIAL) / VISCOSIDAD_IMPERIAL
            }
        }
    }
}