package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

import android.content.Context

interface CadastraImovelContract {

    interface View {
        fun insertFinished()
        fun saveSuccess()
        fun hideProgressBar()
        fun showProgressBar()
    }

    interface Presenter {
        fun onInsertImovel(title: String, tipo: String,
                           cidade: String, rua: String,
                           numero: Int, complemento: String,
                           referencia: String, max: Int,
                           min: Int, preco: Int, quartos: Int,
                           banheiros: Int, salas: Int,
                           cozinhas: Int, vaga: Int,
                           descricao: String)

        fun onInsertImovelWithPhoto(title: String, tipo: String,
                                    cidade: String, rua: String,
                                    numero: Int, complemento: String,
                                    referencia: String, max: Int,
                                    min: Int, preco: Int, quartos: Int,
                                    banheiros: Int, salas: Int,
                                    cozinhas: Int, vaga: Int,
                                    descricao: String,
                                    imageByte: ByteArray)
    }
}