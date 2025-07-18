package com.pumpapp.pumpapp.calculos

class CalculoPorGoteo {
    companion object {
        const val RUGOSIDAD_PLASTICO = 3.0e-7
        const val RUGOSIDAD_PEAD = 1.5e-3

        //TODO Para el spinner de material de riego por goteo

        fun perdidasCintilla (
            cantidad: Int,
            longitud: Double,
            diametro: Double,
            factorFriccion: Double
        ): Double {
            return cantidad * (longitud / diametro) * factorFriccion
        }
    }
}