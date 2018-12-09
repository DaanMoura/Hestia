package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

interface CadastraImovelContract {

    interface View {
        fun insertFinished()
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
    }
}