package com.pumpapp.pumpapp.calculos

import android.content.Context
import com.pumpapp.pumpapp.enums.SistemaUnidades

class CalculoPorGoteo {


    companion object {

        const val RUGOSIDADPLASTICO = 3.0e-7
        const val RUGOSIDADPEAD = 1.5e-3
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

        private fun perdidasCintilla (
            cantidad: Int,
            longitudCintilla: Double,

            context: Context,
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