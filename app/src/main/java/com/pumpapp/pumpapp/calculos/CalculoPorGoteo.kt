package com.pumpapp.pumpapp.calculos

import android.content.Context
import com.pumpapp.pumpapp.enums.SistemaUnidades

class CalculoPorGoteo {
    companion object {

        //TODO Para el spinner de material de riego por goteo

        private fun calculosAnteriores (
            context: Context,
            diametroGoteo: Double,
            rugosidadCintilla: Double,
            velocidadGoteo: Double,
            sistemaUnidades: SistemaUnidades = SistemaUnidades.INTERNACIONAL,
        ): Double {
            val numeroDeReynoldsGoteo = CalculosGenerales.calcularNumeroReynolds(velocidadGoteo,diametroGoteo,sistemaUnidades)
            return CalculosGenerales.calcularFactorFriccion(
                context,
                diametroGoteo,
                rugosidadCintilla,
                numeroDeReynoldsGoteo
            )
        }

        fun perdidasCintilla (
            context: Context,
            cantidad: Int,
            longitudCintilla: Double,
            diametroGoteo: Double,
            rugosidadCintilla: Double,
            numeroDeReynoldsGoteo: Double
        ): Double {
            val factorFriccion = calculosAnteriores(
                context,
                diametroGoteo,
                rugosidadCintilla,
                numeroDeReynoldsGoteo
            )
            return cantidad * (longitudCintilla / diametroGoteo) * factorFriccion
        }
    }
}