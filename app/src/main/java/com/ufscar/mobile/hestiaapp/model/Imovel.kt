package com.ufscar.mobile.hestiaapp.model

import android.graphics.Bitmap
import java.io.Serializable

data class Imovel(val title: String,
                  val max: Int,
                  val min: Int,
                  val interessados: Int,
                  val preco: Int,
                  val quartos: Int,
                  val banheiros: Int,
                  val salas: Int,
                  val vaga: Int,
                  val descricao: String,
                  val endereco: String,
                  val uidDono: String,
                  val bmFoto: Bitmap?) : Serializable {
    constructor(): this("",0,0,0,0,
            0,0,0,0,"","","", null)
}



