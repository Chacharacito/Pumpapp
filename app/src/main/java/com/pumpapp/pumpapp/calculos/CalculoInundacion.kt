package com.pumpapp.pumpapp.calculos

class CalculoInundacion {
    companion object {
        const val VISCOSIDAD_SI = 0.000861
        const val PESO_ESPECIFICO = 997
        fun calculoNumeroReynolds (velocidad: Double, diametro: Double): Double {

            return (velocidad*diametro * PESO_ESPECIFICO) / VISCOSIDAD_SI
        }
    }
}