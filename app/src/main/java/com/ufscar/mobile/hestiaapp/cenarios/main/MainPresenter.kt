package com.ufscar.mobile.hestiaapp.cenarios.main

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.R
import com.ufscar.mobile.hestiaapp.model.User
import com.ufscar.mobile.hestiaapp.util.FirestoreImovelUtil
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil


class MainPresenter(val view: MainContract.View): MainContract.Presenter {
    private val signInProviders =
            listOf(AuthUI.IdpConfig.EmailBuilder()
                    .setAllowNewAccounts(true)
                    .setRequireName(true)
                    .build())

    override fun onUpdateDrawer(context: Context) {
        if (FirebaseAuth.getInstance().currentUser == null) {
            view.hideMeusImoveis()
            view.hidePerfil()
            view.hideFavoritos()
            view.showEntrar()
        } else {
            FirestoreUserUtil.getCurrentUser({ user: User ->
                view.updateDrawerSuccess(user.nome, user.email, user.picturePath)
            }, context)
            view.hideEntrar()
            view.showPerfil()
            view.showMeusImoveis()
            view.showFavoritos()
        }
    }

    override fun onUpdateList() {
        FirestoreImovelUtil.getAll {
            val list = it
            view.showLoading()
            view.showList(list)
        }
    }

    override fun onEntrar() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            val intent = AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(signInProviders)
                    .setLogo(R.drawable.ic_home)
                    .build()
            view.entrarSucces(intent)
        } else {
            view.showMessage("Não foi possível realizar essa operação")
        }
    }

    override fun onPerfil() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            view.showMessage("Não foi possível realizar essa operação")
        } else {
            view.perfilSuccess()
        }
    }

    override fun onImoveis() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            view.showMessage("Não foi possível realizar essa operação")
        } else {
            view.imoveisSuccess()
        }
    }

    override fun onFirstTime(context: Context) {
        FirestoreUserUtil.initCurrentUserIfFirstTime {
            view.firstTimeSuccess()
        }
    }
}