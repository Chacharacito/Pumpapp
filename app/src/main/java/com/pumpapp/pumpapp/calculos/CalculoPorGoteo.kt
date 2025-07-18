package com.pumpapp.pumpapp.calculos

class CalculoPorGoteo {
    companion object {
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