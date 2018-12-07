package com.ufscar.mobile.hestiaapp.cenarios.info_imovel

import android.content.Context
import com.ufscar.mobile.hestiaapp.model.Imovel

interface InfoImovelContract {
    interface View {
        fun showFabEdit()
        fun showFabFavorite()
        fun fillFavorite()
        fun unfillFavorite()
    }

    interface Presenter {
        fun onLoadFab(context: Context, showEdit: Boolean, imovel: Imovel?)
        fun onFavorite(imovel: Imovel?, context: Context)
    }
}