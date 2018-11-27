package com.ufscar.mobile.hestiaapp.cenarios.info_imovel

import android.content.Context

interface InfoImovelContract {
    interface View {
        fun showFabEdit()
        fun showFabFavorite()
    }

    interface Presenter {
        fun onLoadFab(context: Context)
    }
}