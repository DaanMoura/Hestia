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
        if (FirebaseAuth.getInstance().currentUser != null) {
            FirestoreUserUtil.getCurrentUser({ user: User ->
                view.updateDrawerSuccess(user.nome, user.email, user.picturePath)
                if(user.dono)
                    view.showMeusImoveisItem()
            }, context)

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
                    .setLogo(R.drawable.ic_home) // TODO: Change this later
                    .build()
            view.entrarSucces(intent)
        } else {
            view.entrarFailed()
        }
    }

    override fun onPerfil() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            view.perfilFailed()
        } else {
            view.perfilSuccess()
        }
    }

    override fun onImoveis() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            view.imoveisFailed()
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