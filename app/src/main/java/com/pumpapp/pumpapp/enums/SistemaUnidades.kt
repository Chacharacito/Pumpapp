package com.pumpapp.pumpapp.enums

enum class SistemaUnidades (val nombre: String) {
    INTERNACIONAL("Internacional"),
    IMPERIAL("Imperial");

    fun obtenerNombre(): String {
        return nombre
    }
}