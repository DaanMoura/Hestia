package com.ufscar.mobile.hestiaapp.cenarios.perfil

import android.content.Context
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.ufscar.mobile.hestiaapp.util.FirestoreUserUtil
import com.ufscar.mobile.hestiaapp.util.StorageUtil


class PerfilPresenter(val view: PerfilContract.View): PerfilContract.Presenter {

    override fun onChangePhoto(nome: String, bio: String) {
        FirestoreUserUtil.updateCurrentUser(nome,
                FirebaseAuth.getInstance().currentUser?.email ?: "",
                bio, null)
        view.changePhotoSuccess()
    }

    override fun onSaveWithPhoto(context: Context, imageBytes: ByteArray, nome: String, bio: String) {
        view.showProgressBar()
        StorageUtil.uploadProfilePhoto(imageBytes) { imagePath ->
            FirestoreUserUtil.updateCurrentUser(nome,
                    FirebaseAuth.getInstance().currentUser?.email ?: "",
                    bio, imagePath)
            view.saveSucces()
        }
    }

    override fun onSaveWithoutPhoto(nome: String, bio: String) {
        FirestoreUserUtil.updateCurrentUser(nome,
                FirebaseAuth.getInstance().currentUser?.email ?: "",
                bio, null)
        view.saveSucces()
    }

    override fun onLogout(context: Context) {
        AuthUI.getInstance().signOut(context)
                .addOnCompleteListener {
                    view.logoutSucces()
                }
    }

    override fun onSetFields(context: Context) {
        FirestoreUserUtil.getCurrentUser({ user ->
            view.setFieldsSucces(user.nome, user.bio, user.picturePath)
        }, context)
    }
}