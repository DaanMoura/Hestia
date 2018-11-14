package com.ufscar.mobile.hestiaapp.cenarios.info_imovel

import android.content.Context
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.R.id.fab_edit
import com.ufscar.mobile.hestiaapp.R.id.fab_favorite
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
import kotlinx.android.synthetic.main.activity_info_imovel.*

class InfoImovelPresenter(val view: InfoImovelContract.View): InfoImovelContract.Presenter {

    override fun onLoadFab(context: Context) {
        if(FirebaseAuth.getInstance().currentUser != null) {
            FirestoreUserUtil.getCurrentUser({ user ->
                if(user.dono) view.showFabEdit()
                else view.showFabFavorite()
            }, context)
        }
    }
}