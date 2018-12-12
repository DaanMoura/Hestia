package com.ufscar.mobile.hestiaapp.cenarios.main

import android.content.Context
import android.content.Intent
import com.ufscar.mobile.hestiaapp.model.Imovel

interface MainContract {
    interface View {
        fun updateDrawerSuccess(nome: String, email: String, picturePath: String?)
        fun showLoading()
        fun showList(list: ArrayList<Imovel>)
        fun entrarSucces(intent: Intent)
        fun perfilSuccess()
        fun imoveisSuccess()
        fun firstTimeSuccess()
        fun hideEntrar()
        fun showEntrar()
        fun hidePerfil()
        fun showPerfil()
        fun showMeusImoveis()
        fun hideMeusImoveis()
        fun hideFavoritos()
        fun showFavoritos()
        fun showMessage(message: String)
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