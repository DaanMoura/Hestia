package com.ufscar.mobile.hestiaapp.cenarios.favoritos

import android.content.Context
import com.ufscar.mobile.hestiaapp.model.Imovel

interface FavoritosContract {
    interface View {
        fun showMessage(m: String)
        fun showList(list: ArrayList<Imovel>)
        fun hideLoading()
    }

    interface Presenter {
        fun onListUpdate(context: Context)
    }
}