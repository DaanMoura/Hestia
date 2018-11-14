package com.ufscar.mobile.hestiaapp.cenarios.cadastra_imovel

interface CadastraImovelContract {

    interface View {
        fun insertSuccess()
    }

    interface Presenter {
        fun onInsertImovel(tipo: String,moradores: Int,preco: Int,quartos: Int,
                           banheiros: Int,salas: Int,vagas: Int,desc: String,end:String)
    }
}