package com.pumpapp.pumpapp.calculos

import android.content.Context
import android.widget.Toast
import kotlin.math.pow

class CalculoInundacion {
    companion object {
        private const val PROFUNDIDAD = 0.4

        val rugosidadArcilloso = 0.026
        val rugosidadArenoso = 0.022
        val rugosidadLimoso = 0.021
        //TODO AJUSTAR ESTO CON LOS TIPOS DE SUELO Y LA FUNCION VELOCIDAD MANNING

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
            numeroSurcos: Double
        ): Double {
            val area = calcularArea(ancho) * numeroSurcos
            val velocidad = velocidadManning(ancho, pendiente, rugosidad)
            return velocidad * area * 1000.0 * 3600.0
        }

        fun evaluacion(
            context: Context,
            ancho: Double,
            pendiente: Double,
            rugosidad: Double,
            numeroSurcos: Double,
            infiltracion: Double,
            tiempoRiego: Double,
            longitudSurcos: Double
        ) {
            val caudalmmh = calcularCaudal(ancho, pendiente, rugosidad, numeroSurcos)
            return if (caudalmmh > infiltracion) {
                val longitudAvance = velocidadManning(ancho, pendiente, rugosidad) / (tiempoRiego * 60)
                if (longitudAvance >= longitudSurcos) {
                    Toast.makeText(context, "Datos guardados correctamente", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Disminuya longitud de riego", Toast.LENGTH_SHORT).show()
                    //TODO No dejar avanzar a la siguiente actividad
                }
            } else {
                Toast.makeText(context, "Aumente pendiente o caudal de la bomba", Toast.LENGTH_SHORT).show()
                //TODO No dejar avanzar a la siguiente actividad
            }
        }
    }
}