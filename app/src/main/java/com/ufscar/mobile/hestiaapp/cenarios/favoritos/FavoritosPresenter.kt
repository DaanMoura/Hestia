package com.ufscar.mobile.hestiaapp.cenarios.favoritos

import android.content.Context
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil

class FavoritosPresenter(val view: FavoritosContract.View) : FavoritosContract.Presenter {
    override fun onListUpdate(context: Context) {
       FirestoreImovelUtil.getAll {imoveis ->
            view.hideLoading()
            FirestoreUserUtil.getCurrentUser({
                view.showList(filterImoveis(imoveis, it.favoritos))
            }, context)
        }

    }

    fun filterImoveis(list: ArrayList<Imovel>, favoritos: ArrayList<String>) : ArrayList<Imovel> {
        val newList = ArrayList<Imovel>()
        for(imovel in list) {
            if(imovel.id in favoritos) {
                newList.add(imovel)
            }
        }
        return newList
    }

}