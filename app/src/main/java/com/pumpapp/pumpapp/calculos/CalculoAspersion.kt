package com.pumpapp.pumpapp.calculos

import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.ALGODON
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.ARROZ
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.CAPACIDAD_DE_CAMPO
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.CAÑA
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.DENSIDAD_APARENTE
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.FACTOR_AGOTAMIENTO
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.PIÑA
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.PROF_ALGODON
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.PROF_ARROZ
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.PROF_CAÑA
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.PROF_PIÑA
import com.pumpapp.pumpapp.riegos.RiegoAspersionDAActivity.Companion.PUNTO_MARCHITEZ_PERMA

class CalculoAspersion {

    companion object {



        fun LaminasDeAgua(cultivo: String
        ): Double{
            when (cultivo) {
                ALGODON -> (CAPACIDAD_DE_CAMPO - PUNTO_MARCHITEZ_PERMA) * DENSIDAD_APARENTE * PROF_ALGODON * FACTOR_AGOTAMIENTO
                CAÑA -> ((CAPACIDAD_DE_CAMPO - PUNTO_MARCHITEZ_PERMA) * DENSIDAD_APARENTE * PROF_CAÑA * FACTOR_AGOTAMIENTO)
                PIÑA -> (CAPACIDAD_DE_CAMPO - PUNTO_MARCHITEZ_PERMA) * DENSIDAD_APARENTE * PROF_PIÑA * FACTOR_AGOTAMIENTO
                ARROZ -> (CAPACIDAD_DE_CAMPO - PUNTO_MARCHITEZ_PERMA) * DENSIDAD_APARENTE * PROF_ARROZ * FACTOR_AGOTAMIENTO
            }
            when (cultivo) {
                ALGODON ->
                CAÑA ->
                PIÑA ->
                ARROZ
            }
        }
    }
}