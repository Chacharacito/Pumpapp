package com.pumpapp.pumpapp.enums

enum class SistemaRiego(val codigo: Int) {
    RIEGO_GOTEO(1),
    RIEGO_INUNDACION(2);

    fun obtenerCodigo(): Int {
        return codigo
    }
}
