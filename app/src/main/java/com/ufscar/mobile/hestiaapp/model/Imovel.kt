package com.ufscar.mobile.hestiaapp.model

import java.io.Serializable

data class Imovel(val title: String?,
                  val tipo: String,
                  val cidade: String,
                  val rua: String,
                  val numero: Int,
                  val complemento: String?,
                  val referencia: String?,
                  val max: Int,
                  val min: Int,
                  val interessados: Int,
                  val preco: Int,
                  val quartos: Int,
                  val banheiros: Int,
                  val salas: Int,
                  val cozinhas: Int,
                  val vaga: Int,
                  val descricao: String,
                  val uidDono: String,
                  val emailDono: String,
                  val picturePath: String?,
                  val id: String) : Serializable {
    constructor(): this("","","","", 0,"","",
            0,0,0,0,0,0,0,0,0,
            "","","",null,"")
}



