package com.ufscar.mobile.hestiaapp.cenarios.info_imovel

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.model.Imovel
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil

class InfoImovelPresenter(val view: InfoImovelContract.View): InfoImovelContract.Presenter {
    override fun onFavorite(imovel: Imovel?, context: Context) {
        if(imovel != null) {
            FirestoreUserUtil.getCurrentUser({user ->
                if(imovel.id in user.favoritos) {
                    FirestoreUserUtil.removeFavorite(imovel.id)
                    view.unfillFavorite()
                } else {
                    FirestoreUserUtil.addFavorite(imovel.id)
                    view.fillFavorite()
                }

            }, context)
        }
    }

    override fun onLoadFab(context: Context, showEdit: Boolean, imovel: Imovel?) {

        if(FirebaseAuth.getInstance().currentUser != null) {
            if(showEdit)
                view.showFabEdit()
            else {
                view.showFabFavorite()
                FirestoreUserUtil.getCurrentUser({user ->
                    if(imovel?.id in user.favoritos) {
                        view.fillFavorite()
                    } else {
                        view.unfillFavorite()
                    }
                }, context)
            }
        }
    }
}