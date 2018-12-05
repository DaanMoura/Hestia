package com.ufscar.mobile.hestiaapp.cenarios.main

import android.content.Context
import android.content.Intent
import com.ufscar.mobile.hestiaapp.model.Imovel

interface MainContract {
    interface View {
        fun updateDrawerSuccess(nome: String, email: String, picturePath: String?)
        fun showLoading()
        fun showList(list: ArrayList<Imovel>)
        fun showMeusImoveisItem()
        fun entrarSucces(intent: Intent)
        fun entrarFailed()
        fun perfilSuccess()
        fun perfilFailed()
        fun imoveisSuccess()
        fun imoveisFailed()
        fun firstTimeSuccess()
    }

    interface Presenter {
        fun onUpdateDrawer(context: Context)
        fun onUpdateList()
        fun onEntrar()
        fun onPerfil()
        fun onImoveis()
        fun onFirstTime(context: Context)
    }
}