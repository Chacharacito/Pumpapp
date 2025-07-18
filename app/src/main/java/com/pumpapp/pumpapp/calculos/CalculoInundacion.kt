package com.pumpapp.pumpapp.calculos

import kotlin.math.pow

class CalculoInundacion {
    companion object {
        private const val PROFUNDIDAD = 0.4

        private fun calcularArea(ancho: Double): Double =
            ancho * PROFUNDIDAD

        private fun calcularPerimetro(ancho: Double): Double =
            ancho + 2 * PROFUNDIDAD

        private fun calcularRadioHidraulico(ancho: Double): Double =
            calcularArea(ancho) / calcularPerimetro(ancho)

        fun velocidadManning(ancho: Double, pendiente: Double, rugosidad: Double): Double {
            val radioHidraulico = calcularRadioHidraulico(ancho)
            return (1 / rugosidad) * radioHidraulico.pow(2.0 / 3.0) * pendiente.pow(0.5)
        }

        fun calcularCaudal(
            ancho: Double,
            pendiente: Double,
            rugosidad: Double,
            numeroSurcos: Int
        ): Double {
            val area = calcularArea(ancho) * numeroSurcos
            val velocidad = velocidadManning(ancho, pendiente, rugosidad)
            return velocidad * area * 1000.0 * 3600.0
        }
    }
}