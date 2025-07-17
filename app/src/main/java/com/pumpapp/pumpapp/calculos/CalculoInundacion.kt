package com.pumpapp.pumpapp.calculos

import kotlin.math.pow

class CalculoInundacion {
    companion object {
        private const val PROFUNDIDAD = 0.4

        const val RUGOSIDAD_ARCILLOSO = 0.026
        const val RUGOSIDAD_ARENOSO = 0.022
        const val RUGOSIDAD_LIMOSO = 0.021
        //TODO AJUSTAR ESTO CON LOS TIPOS DE SUELO Y LA FUNCION VELOCIDAD MANNING

        private fun calcularArea(ancho: Double): Double =
            ancho * PROFUNDIDAD

        private fun calcularPerimetro(ancho: Double): Double =
            ancho + 2 * PROFUNDIDAD

        private fun calcularRadioHidraulico(ancho: Double): Double =
            calcularArea(ancho) / calcularPerimetro(ancho)

        fun velocidadManning(ancho: Double, pendiente: Double, RUGOSIDAD_: Double): Double {
            val radioHidraulico = calcularRadioHidraulico(ancho)
            return (1 / RUGOSIDAD_) * radioHidraulico.pow(2.0 / 3.0) * pendiente.pow(0.5)
        }

        fun calcularCaudal(
            ancho: Double,
            pendiente: Double,
            RUGOSIDAD_: Double,
            numeroSurcos: Double
        ): Double {
            val area = calcularArea(ancho) * numeroSurcos
            val velocidad = velocidadManning(ancho, pendiente, RUGOSIDAD_)
            return velocidad * area * 1000.0 * 3600.0
        }
    }
}