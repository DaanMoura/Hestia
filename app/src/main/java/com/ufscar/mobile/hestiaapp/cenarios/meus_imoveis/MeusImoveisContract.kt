package com.ufscar.mobile.hestiaapp.cenarios.meus_imoveis

import com.ufscar.mobile.hestiaapp.model.Imovel

interface MeusImoveisContract {
    interface View {
        fun showList(list: ArrayList<Imovel>)
        fun hideLoading()
    }

    interface Presenter {
        fun onListUpdate()
    }
}