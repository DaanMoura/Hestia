package com.ufscar.mobile.hestiaapp.cenarios.meus_imoveis

import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil

class MeusImoveisPresenter(val view: MeusImoveisContract.View): MeusImoveisContract.Presenter {
    override fun onListUpdate() {
        FirestoreImovelUtil.getAll {
            val list = filterImoveis(it)
            view.hideLoading()
            view.showList(list)
        }
    }

    fun filterImoveis(list: ArrayList<Imovel>) : ArrayList<Imovel> {
        val newList = ArrayList<Imovel>()
        val uid = FirebaseAuth.getInstance().uid
        for(imovel in list) {
            if(imovel.uidDono == uid)
                newList.add(imovel)
        }
        return newList
    }
}