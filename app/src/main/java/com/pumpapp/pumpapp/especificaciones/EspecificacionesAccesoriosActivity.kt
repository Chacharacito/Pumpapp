package com.pumpapp.pumpapp.especificaciones

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.pumpapp.pumpapp.R

class EspecificacionesAccesoriosActivity : AppCompatActivity() {

    companion object{
        const val VAL_DE_BOLA = "valbulaDeBola"
        const val VAL_DE_ANGULO = "valbulaDeAngulo"
        const val VAL_DE_GOBO = "valbulaDeGlobo"
        const val COD_NOVENTA = "codosDeNoventa"
        const val COD_CUARENTACINCO = "codosDeCuarentaYCinco"
        const val VUL_RETORNO = "vueltaRetorno"
        const val TE_FLU_NORMAL = "teDeFlujoNormal"
        const val TE_FLU_INVERTIDO = "teDeFlujoInvertido"
        const val CAB_LLAVE_DE_PASO = "cabezalLllaveDePaso"
        const val CAB_VAL_PRESION = "cabezalValbulaDePresion"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_especificaciones_accesorios)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}