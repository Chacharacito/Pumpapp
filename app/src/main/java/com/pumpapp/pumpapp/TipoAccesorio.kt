package com.pumpapp.pumpapp

enum class TipoAccesorio (val clave: String) {
    VALVULA_DE_BOLA("valbulaDeBola"),
    VALVULA_DE_ANGULO("valbulaDeAngulo"),
    VALVULA_DE_GLOBO("valbulaDeGlobo"),
    CODO_NOVENTA("codosDeNoventa"),
    CODO_CUARENTA_Y_CINCO("codosDeCuarentaYCinco"),
    VUELTA_RETORNO("vueltaRetorno"),
    TE_FLUJO_NORMAL("teDeFlujoNormal"),
    TE_FLUJO_INVERTIDO("teDeFlujoInvertido"),
    CABEZAL_LLAVE_DE_PASO("cabezalLllaveDePaso"),
    CABEZAL_VALVULA_PRESION("cabezalValbulaDePresion");

    fun getClave(): String {
        return clave
    }
}